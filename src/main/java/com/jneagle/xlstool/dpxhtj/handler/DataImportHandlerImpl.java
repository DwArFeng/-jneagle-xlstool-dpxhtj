package com.jneagle.xlstool.dpxhtj.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.jneagle.xlstool.dpxhtj.bean.entity.ConsumingDetail;
import com.jneagle.xlstool.dpxhtj.bean.entity.ImportErrorInfo;
import com.jneagle.xlstool.dpxhtj.exception.WrongPasswordException;
import com.jneagle.xlstool.dpxhtj.service.ConsumingDetailMaintainService;
import com.jneagle.xlstool.dpxhtj.service.ExportErrorInfoMaintainService;
import com.jneagle.xlstool.dpxhtj.service.ImportErrorInfoMaintainService;
import com.jneagle.xlstool.dpxhtj.structure.ProgressStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.*;

@Component
public class DataImportHandlerImpl extends AbstractProgressHandler implements DataImportHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataImportHandlerImpl.class);

    private final ConsumingDetailMaintainService consumingDetailMaintainService;
    private final ImportErrorInfoMaintainService importErrorInfoMaintainService;
    private final ExportErrorInfoMaintainService exportErrorInfoMaintainService;

    @Value("${data_import.valid_sheet_name_regex}")
    private String validSheetNameRegex;
    @Value("${data_import.data_sheet.first_data_row}")
    private int firstDataRow;

    @Value("${data_import.data_sheet.column_index.tool_cutter_type}")
    private int toolCutterTypeColumnIndex;
    @Value("${data_import.data_sheet.column_index.device}")
    private int deviceColumnIndex;
    @Value("${data_import.data_sheet.column_index.consuming_quantity}")
    private int consumingQuantityColumnIndex;
    @Value("${data_import.data_sheet.column_index.worth}")
    private int worthColumnIndex;
    @Value("${data_import.data_sheet.column_index.consuming_person}")
    private int consumingPersonColumnIndex;
    @Value("${data_import.data_sheet.column_index.consuming_date}")
    private int consumingDateColumnIndex;
    @Value("${data_import.data_sheet.column_index.remark}")
    private int remarkColumnIndex;

    public DataImportHandlerImpl(
            ConsumingDetailMaintainService consumingDetailMaintainService,
            ImportErrorInfoMaintainService importErrorInfoMaintainService,
            ExportErrorInfoMaintainService exportErrorInfoMaintainService
    ) {
        this.consumingDetailMaintainService = consumingDetailMaintainService;
        this.importErrorInfoMaintainService = importErrorInfoMaintainService;
        this.exportErrorInfoMaintainService = exportErrorInfoMaintainService;
    }

    @Override
    public void execImport(File file, String password) throws HandlerException {
        try {
            // 广播进度变更事件。
            fireProgressChanged(ProgressStatus.UNCERTAIN);

            // 清空相关实体的维护服务。
            consumingDetailMaintainService.clear();
            importErrorInfoMaintainService.clear();
            exportErrorInfoMaintainService.clear();

            // 执行加载动作。
            Workbook workbook = parseWorkbook(file, password);

            // 遍历表格的所有工作簿，获取名称有效的工作簿。
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            List<Sheet> sheets = new ArrayList<>();
            while (sheetIterator.hasNext()) {
                Sheet sheet = sheetIterator.next();
                if (sheet.getSheetName().matches(validSheetNameRegex)) {
                    sheets.add(sheet);
                }
            }

            // 获取工作表的公式计算器。
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            // 定义数据列表。
            List<ConsumingDetail> consumingDetails = new ArrayList<>();
            List<ImportErrorInfo> importErrorInfos = new ArrayList<>();

            // 设置总体进度。
            int progress = 0;
            fireProgressChanged(progress, sheets.size());

            // 遍历所有有效工作簿，导入单个工作簿。
            for (Sheet sheet : sheets) {
                execImportSingleSheet(evaluator, sheet, consumingDetails, importErrorInfos);
                fireProgressChanged(++progress, sheets.size());
            }

            // 将结果实体批量添加到维护服务中。
            consumingDetailMaintainService.batchInsert(consumingDetails);
            importErrorInfoMaintainService.batchInsert(importErrorInfos);
        } catch (org.apache.poi.EncryptedDocumentException e) {
            throw new WrongPasswordException(e);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            // 广播进度变更事件。
            fireProgressChanged(ProgressStatus.IDLE);
        }
    }

    private Workbook parseWorkbook(File file, String password) throws Exception {
        try (InputStream in = Files.newInputStream(file.toPath())) {
            if (StringUtils.isNotEmpty(password)) {
                POIFSFileSystem pfs = new POIFSFileSystem(in);
                EncryptionInfo encInfo = new EncryptionInfo(pfs);
                Decryptor decryptor = Decryptor.getInstance(encInfo);
                decryptor.verifyPassword(password);
                return WorkbookFactory.create(decryptor.getDataStream(pfs));
            } else {
                return WorkbookFactory.create(in);
            }
        }
    }

    private void execImportSingleSheet(
            FormulaEvaluator evaluator, Sheet sheet, List<ConsumingDetail> consumingDetails,
            List<ImportErrorInfo> importErrorInfos
    ) {
        // 获取工作簿名称。
        String sheetName = sheet.getSheetName();

        // 解析行数，并确定循环的总次数。
        int currentRowIndex = firstDataRow;
        int totalRowIndex = sheet.getLastRowNum();

        // 对工作簿的每一行进行遍历，读取数据。
        for (; currentRowIndex <= totalRowIndex; currentRowIndex++) {
            loadRow(
                    evaluator, sheetName, currentRowIndex, sheet.getRow(currentRowIndex), consumingDetails,
                    importErrorInfos
            );
        }
    }

    private void loadRow(
            FormulaEvaluator evaluator, String sheetName, int rowIndex, Row row, List<ConsumingDetail> consumingDetails,
            List<ImportErrorInfo> importErrorInfos
    ) {
        try {
            // 共用变量。
            CellValue cellValue;

            // 刀片型号。
            cellValue = evaluator.evaluate(CellUtil.getCell(row, toolCutterTypeColumnIndex));
            String toolCutterType = Optional.ofNullable(cellValue).map(CellValue::getStringValue).orElse(null);

            // 设备。
            cellValue = evaluator.evaluate(CellUtil.getCell(row, deviceColumnIndex));
            String device = Optional.ofNullable(cellValue).map(CellValue::getStringValue).orElse(null);

            // 消耗数量。
            cellValue = evaluator.evaluate(CellUtil.getCell(row, consumingQuantityColumnIndex));
            Integer consumingQuantity = Optional.ofNullable(cellValue).map(v -> (int) v.getNumberValue()).orElse(null);

            // 价值。
            cellValue = evaluator.evaluate(CellUtil.getCell(row, worthColumnIndex));
            BigDecimal worth = Optional.ofNullable(cellValue).map(v -> BigDecimal.valueOf(v.getNumberValue()))
                    .orElse(null);

            // 领用人。
            cellValue = evaluator.evaluate(CellUtil.getCell(row, consumingPersonColumnIndex));
            String consumingPerson = Optional.ofNullable(cellValue).map(CellValue::getStringValue).orElse(null);

            // 领用日期。
            cellValue = evaluator.evaluate(CellUtil.getCell(row, consumingDateColumnIndex));
            Date consumingDate = Optional.ofNullable(cellValue).map(v -> DateUtil.getJavaDate(v.getNumberValue()))
                    .orElse(null);

            // 备注。
            cellValue = evaluator.evaluate(CellUtil.getCell(row, remarkColumnIndex));
            String remark = Optional.ofNullable(cellValue).map(CellValue::getStringValue).orElse(null);

            consumingDetails.add(new ConsumingDetail(
                    null, toolCutterType, device, consumingQuantity, worth, consumingPerson, consumingDate, remark
            ));
        } catch (Exception e) {
            String warnMessage = "读取数据表的第 " + rowIndex + " 行(对应数据表是第 " +
                    (rowIndex + 1) + " 行)数据时出现异常，异常信息为: ";
            LOGGER.warn(warnMessage, e);
            ImportErrorInfo importErrorInfo = new ImportErrorInfo(
                    null, sheetName, rowIndex, "数据源错误，详见控制台日志"
            );
            importErrorInfos.add(importErrorInfo);
        }
    }


}
