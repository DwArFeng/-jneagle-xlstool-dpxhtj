package com.jneagle.xlstool.dpxhtj.handler;

import com.dwarfeng.dutil.basic.io.FileUtil;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.jneagle.xlstool.dpxhtj.bean.dto.StatisticResult;
import com.jneagle.xlstool.dpxhtj.bean.dto.StatisticResult.DevicePerspective;
import com.jneagle.xlstool.dpxhtj.bean.dto.StatisticResult.PersonPerspective;
import com.jneagle.xlstool.dpxhtj.bean.dto.StatisticResult.ToolCutterPerspective;
import com.jneagle.xlstool.dpxhtj.bean.entity.ExportErrorInfo;
import com.jneagle.xlstool.dpxhtj.exception.TemplateLoadFailedException;
import com.jneagle.xlstool.dpxhtj.service.ExportErrorInfoMaintainService;
import com.jneagle.xlstool.dpxhtj.structure.ProgressStatus;
import com.jneagle.xlstool.dpxhtj.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class DataExportHandlerImpl extends AbstractProgressHandler implements DataExportHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataExportHandlerImpl.class);
    private static final String TEMPLATE_XLS_RESOURCE = "file:conf/data-export/template.xls";
    private static final String TEMPLATE_XLSX_RESOURCE = "file:conf/data-export/template.xlsx";

    private final ApplicationContext ctx;

    private final ExportErrorInfoMaintainService exportErrorInfoMaintainService;

    @Value("${data_export.sheet_index.person_perspective}")
    private int personPerspectiveSheetIndex;
    @Value("${data_export.sheet_index.device_perspective}")
    private int devicePerspectiveSheetIndex;
    @Value("${data_export.sheet_index.tool_cutter_perspective}")
    private int toolCutterPerspectiveSheetIndex;

    @Value("${data_export.data_sheet.person_perspective.first_data_row}")
    private int personPerspectiveFirstDataRow;
    @Value("${data_export.data_sheet.person_perspective.column_index.month}")
    private int personPerspectiveMonthColumnIndex;
    @Value("${data_export.data_sheet.person_perspective.column_index.name}")
    private int personPerspectiveNameColumnIndex;
    @Value("${data_export.data_sheet.person_perspective.column_index.tool_cutter_type}")
    private int personPerspectiveToolCutterTypeColumnIndex;
    @Value("${data_export.data_sheet.person_perspective.column_index.consuming_quantity}")
    private int personPerspectiveConsumingQuantityColumnIndex;
    @Value("${data_export.data_sheet.person_perspective.column_index.worth}")
    private int personPerspectiveWorthColumnIndex;
    @Value("${data_export.data_sheet.person_perspective.column_index.returning_quantity}")
    private int personPerspectiveReturningQuantityColumnIndex;
    @Value("${data_export.data_sheet.person_perspective.column_index.device}")
    private int personPerspectiveDeviceColumnIndex;
    @Value("${data_export.data_sheet.person_perspective.column_index.year}")
    private int personPerspectiveYearColumnIndex;
    @Value("${data_export.data_sheet.person_perspective.column_index.statistics_date}")
    private int personPerspectiveStatisticsDateColumnIndex;
    @Value("${data_export.data_sheet.person_perspective.column_index.tool_cutter_code}")
    private int personPerspectiveToolCutterCodeColumnIndex;
    @Value("${data_export.data_sheet.person_perspective.column_index.returning_usage_g01_quantity}")
    private int returningUsageG01QuantityColumnIndex;
    @Value("${data_export.data_sheet.person_perspective.column_index.returning_usage_g02_quantity}")
    private int returningUsageG02QuantityColumnIndex;
    @Value("${data_export.data_sheet.person_perspective.column_index.returning_usage_g03_quantity}")
    private int returningUsageG03QuantityColumnIndex;

    @Value("${data_export.data_sheet.device_perspective.first_data_row}")
    private int devicePerspectiveFirstDataRow;
    @Value("${data_export.data_sheet.device_perspective.column_index.month}")
    private int devicePerspectiveMonthColumnIndex;
    @Value("${data_export.data_sheet.device_perspective.column_index.device}")
    private int devicePerspectiveDeviceColumnIndex;
    @Value("${data_export.data_sheet.device_perspective.column_index.tool_cutter_type}")
    private int devicePerspectiveToolCutterTypeColumnIndex;
    @Value("${data_export.data_sheet.device_perspective.column_index.consuming_quantity}")
    private int devicePerspectiveConsumingQuantityColumnIndex;
    @Value("${data_export.data_sheet.device_perspective.column_index.worth}")
    private int devicePerspectiveWorthColumnIndex;
    @Value("${data_export.data_sheet.device_perspective.column_index.year}")
    private int devicePerspectiveYearColumnIndex;
    @Value("${data_export.data_sheet.device_perspective.column_index.statistics_date}")
    private int devicePerspectiveStatisticsDateColumnIndex;
    @Value("${data_export.data_sheet.device_perspective.column_index.tool_cutter_code}")
    private int devicePerspectiveToolCutterCodeColumnIndex;

    @Value("${data_export.data_sheet.tool_cutter_perspective.first_data_row}")
    private int toolCutterPerspectiveFirstDataRow;
    @Value("${data_export.data_sheet.tool_cutter_perspective.column_index.month}")
    private int toolCutterPerspectiveMonthColumnIndex;
    @Value("${data_export.data_sheet.tool_cutter_perspective.column_index.tool_cutter_type}")
    private int toolCutterPerspectiveToolCutterTypeColumnIndex;
    @Value("${data_export.data_sheet.tool_cutter_perspective.column_index.consuming_quantity}")
    private int toolCutterPerspectiveConsumingQuantityColumnIndex;
    @Value("${data_export.data_sheet.tool_cutter_perspective.column_index.worth}")
    private int toolCutterPerspectiveWorthColumnIndex;
    @Value("${data_export.data_sheet.tool_cutter_perspective.column_index.year}")
    private int toolCutterPerspectiveYearColumnIndex;
    @Value("${data_export.data_sheet.tool_cutter_perspective.column_index.statistics_date}")
    private int toolCutterPerspectiveStatisticsDateColumnIndex;
    @Value("${data_export.data_sheet.tool_cutter_perspective.column_index.tool_cutter_code}")
    private int toolCutterPerspectiveToolCutterCodeColumnIndex;

    @Value("#{'${data_export.month_array}'.split(',')}")
    private String[] monthArray;

    public DataExportHandlerImpl(
            ApplicationContext ctx,
            ExportErrorInfoMaintainService exportErrorInfoMaintainService
    ) {
        this.ctx = ctx;
        this.exportErrorInfoMaintainService = exportErrorInfoMaintainService;
    }

    @Override
    public void execExport(
            StatisticResult statisticResult, File file, int fileType, String password
    ) throws HandlerException {
        try {
            // 广播进度变更事件。
            fireProgressChanged(ProgressStatus.UNCERTAIN);

            // 清空相关实体的维护服务。
            exportErrorInfoMaintainService.clear();

            // 判断文件类型，执行加载动作。
            Workbook workbook = loadTemplate(fileType);

            // 取出结果中的透视列表。
            List<PersonPerspective> personPerspectives = statisticResult.getPersonPerspectives();
            List<DevicePerspective> devicePerspectives = statisticResult.getDevicePerspectives();
            List<ToolCutterPerspective> toolCutterPerspectives = statisticResult.getToolCutterPerspectives();
            // 定义数据列表。
            List<ExportErrorInfo> exportErrorInfos = new ArrayList<>();

            // 设置总体进度。
            int progress = 0;
            int total = personPerspectives.size() + devicePerspectives.size() + toolCutterPerspectives.size();
            fireProgressChanged(progress, total);

            // 定义统计日期。
            String statisticsDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            // 遍历人员透视。
            Sheet personPerspectiveSheet = workbook.getSheetAt(personPerspectiveSheetIndex);
            Map<Integer, CellStyle> personPerspectiveStyleMap = new HashMap<>();
            for (int i = 0; i < personPerspectives.size(); i++) {
                exportSinglePersonPerspective(
                        personPerspectiveSheet, i, statisticsDate, personPerspectives.get(i), exportErrorInfos,
                        personPerspectiveStyleMap
                );
                fireProgressChanged(++progress, total);
            }
            // 遍历设备透视。
            Sheet devicePerspectiveSheet = workbook.getSheetAt(devicePerspectiveSheetIndex);
            Map<Integer, CellStyle> devicePerspectiveStyleMap = new HashMap<>();
            for (int i = 0; i < devicePerspectives.size(); i++) {
                exportSingleDevicePerspective(
                        devicePerspectiveSheet, i, statisticsDate, devicePerspectives.get(i), exportErrorInfos,
                        devicePerspectiveStyleMap
                );
                fireProgressChanged(++progress, total);
            }
            // 遍历刀片透视。
            Sheet toolCutterPerspectiveSheet = workbook.getSheetAt(toolCutterPerspectiveSheetIndex);
            Map<Integer, CellStyle> toolCutterPerspectiveStyleMap = new HashMap<>();
            for (int i = 0; i < toolCutterPerspectives.size(); i++) {
                exportSingleToolCutterPerspective(
                        toolCutterPerspectiveSheet, i, statisticsDate, toolCutterPerspectives.get(i), exportErrorInfos,
                        toolCutterPerspectiveStyleMap
                );
                fireProgressChanged(++progress, total);
            }

            // 保存工作表。
            saveWorkbook(workbook, file, fileType, password);

            // 将结果实体批量添加到维护服务中。
            exportErrorInfoMaintainService.batchInsert(exportErrorInfos);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            // 广播进度变更事件。
            fireProgressChanged(ProgressStatus.IDLE);
        }
    }

    private Workbook loadTemplate(int fileType) throws Exception {
        switch (fileType) {
            case Constants.EXPORT_FILE_TYPE_XLS:
                return loadXlsTemplate();
            case Constants.EXPORT_FILE_TYPE_XLSX:
                return loadXlsxTemplate();
            default:
                throw new IllegalStateException("不应该执行到此处，请联系开发人员");
        }
    }

    private Workbook loadXlsTemplate() throws Exception {
        try (InputStream in = ctx.getResource(TEMPLATE_XLS_RESOURCE).getInputStream()) {
            return WorkbookFactory.create(in);
        } catch (Exception e) {
            throw new TemplateLoadFailedException();
        }
    }

    private Workbook loadXlsxTemplate() throws Exception {
        try (InputStream in = ctx.getResource(TEMPLATE_XLSX_RESOURCE).getInputStream()) {
            return WorkbookFactory.create(in);
        } catch (Exception e) {
            throw new TemplateLoadFailedException();
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void exportSinglePersonPerspective(
            Sheet sheet, int index, String statisticsDate, PersonPerspective personPerspective,
            List<ExportErrorInfo> exportErrorInfos, Map<Integer, CellStyle> styleMap
    ) {
        // 定义通用变量。
        int rowIndex = personPerspectiveFirstDataRow + index;
        Row row = CellUtil.getRow(rowIndex, sheet);
        Cell cell;
        try {
            // 月份。
            cell = CellUtil.getCell(row, personPerspectiveMonthColumnIndex);
            cell.setCellValue(Optional.ofNullable(personPerspective.getMonth()).map(i -> monthArray[i]).orElse(""));
            cell.setCellStyle(cellStyle(
                    styleMap, personPerspectiveMonthColumnIndex, sheet, personPerspectiveFirstDataRow
            ));

            // 姓名。
            cell = CellUtil.getCell(row, personPerspectiveNameColumnIndex);
            cell.setCellValue(Optional.ofNullable(personPerspective.getName()).orElse(""));
            cell.setCellStyle(cellStyle(
                    styleMap, personPerspectiveNameColumnIndex, sheet, personPerspectiveFirstDataRow
            ));

            // 刀片型号。
            cell = CellUtil.getCell(row, personPerspectiveToolCutterTypeColumnIndex);
            cell.setCellValue(Optional.ofNullable(personPerspective.getToolCutterType()).orElse(""));
            cell.setCellStyle(cellStyle(
                    styleMap, personPerspectiveToolCutterTypeColumnIndex, sheet, personPerspectiveFirstDataRow
            ));

            // 消耗数量。
            cell = CellUtil.getCell(row, personPerspectiveConsumingQuantityColumnIndex);
            cell.setCellValue(Optional.ofNullable(personPerspective.getConsumingQuantity()).orElse(0));
            cell.setCellStyle(cellStyle(
                    styleMap, personPerspectiveConsumingQuantityColumnIndex, sheet, personPerspectiveFirstDataRow
            ));

            // 价值。
            cell = CellUtil.getCell(row, personPerspectiveWorthColumnIndex);
            cell.setCellValue(OptionalDouble.of(personPerspective.getWorth().doubleValue()).orElse(0));
            cell.setCellStyle(cellStyle(
                    styleMap, personPerspectiveWorthColumnIndex, sheet, personPerspectiveFirstDataRow
            ));

            // 返回数量。
            cell = CellUtil.getCell(row, personPerspectiveReturningQuantityColumnIndex);
            cell.setCellValue(Optional.ofNullable(personPerspective.getReturningQuantity()).orElse(0));
            cell.setCellStyle(cellStyle(
                    styleMap, personPerspectiveReturningQuantityColumnIndex, sheet, personPerspectiveFirstDataRow
            ));

            // 设备。
            cell = CellUtil.getCell(row, personPerspectiveDeviceColumnIndex);
            cell.setCellValue(Optional.ofNullable(personPerspective.getDevice()).orElse(""));
            cell.setCellStyle(cellStyle(
                    styleMap, personPerspectiveDeviceColumnIndex, sheet, personPerspectiveFirstDataRow
            ));

            // 年份。
            cell = CellUtil.getCell(row, personPerspectiveYearColumnIndex);
            cell.setCellValue(Optional.ofNullable(personPerspective.getYear()).orElse(0));
            cell.setCellStyle(cellStyle(
                    styleMap, personPerspectiveYearColumnIndex, sheet, personPerspectiveFirstDataRow
            ));

            // 统计日期。
            cell = CellUtil.getCell(row, personPerspectiveStatisticsDateColumnIndex);
            cell.setCellValue(statisticsDate);
            cell.setCellStyle(cellStyle(
                    styleMap, personPerspectiveStatisticsDateColumnIndex, sheet, personPerspectiveFirstDataRow
            ));

            // 刀片代号。
            cell = CellUtil.getCell(row, personPerspectiveToolCutterCodeColumnIndex);
            cell.setCellValue(Optional.ofNullable(personPerspective.getToolCutterCode()).orElse(""));
            cell.setCellStyle(cellStyle(
                    styleMap, personPerspectiveToolCutterCodeColumnIndex, sheet, personPerspectiveFirstDataRow
            ));

            // 退回使用等级01数量。
            cell = CellUtil.getCell(row, returningUsageG01QuantityColumnIndex);
            cell.setCellValue(Optional.ofNullable(personPerspective.getReturningUsageG01Quantity()).orElse(0));
            cell.setCellStyle(cellStyle(
                    styleMap, returningUsageG01QuantityColumnIndex, sheet, personPerspectiveFirstDataRow
            ));

            // 退回使用等级02数量。
            cell = CellUtil.getCell(row, returningUsageG02QuantityColumnIndex);
            cell.setCellValue(Optional.ofNullable(personPerspective.getReturningUsageG02Quantity()).orElse(0));
            cell.setCellStyle(cellStyle(
                    styleMap, returningUsageG02QuantityColumnIndex, sheet, personPerspectiveFirstDataRow
            ));

            // 退回使用等级03数量。
            cell = CellUtil.getCell(row, returningUsageG03QuantityColumnIndex);
            cell.setCellValue(Optional.ofNullable(personPerspective.getReturningUsageG03Quantity()).orElse(0));
            cell.setCellStyle(cellStyle(
                    styleMap, returningUsageG03QuantityColumnIndex, sheet, personPerspectiveFirstDataRow
            ));
        } catch (Exception e) {
            String warnMessage = "设置数据表的第 " + rowIndex + " 行(对应数据表是第 " +
                    (rowIndex + 1) + " 行)数据时出现异常，异常信息为: ";
            LOGGER.warn(warnMessage, e);
            ExportErrorInfo exportErrorInfo = new ExportErrorInfo(
                    null, sheet.getSheetName(), rowIndex, "数据源错误，详见控制台日志"
            );
            exportErrorInfos.add(exportErrorInfo);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void exportSingleDevicePerspective(
            Sheet sheet, int index, String statisticsDate, DevicePerspective devicePerspective,
            List<ExportErrorInfo> exportErrorInfos, Map<Integer, CellStyle> styleMap
    ) {
        // 定义通用变量。
        int rowIndex = devicePerspectiveFirstDataRow + index;
        Row row = CellUtil.getRow(rowIndex, sheet);
        Cell cell;
        try {
            // 月份。
            cell = CellUtil.getCell(row, devicePerspectiveMonthColumnIndex);
            cell.setCellValue(Optional.ofNullable(devicePerspective.getMonth()).map(i -> monthArray[i]).orElse(""));
            cell.setCellStyle(cellStyle(
                    styleMap, devicePerspectiveMonthColumnIndex, sheet, devicePerspectiveFirstDataRow
            ));

            // 设备。
            cell = CellUtil.getCell(row, devicePerspectiveDeviceColumnIndex);
            cell.setCellValue(Optional.ofNullable(devicePerspective.getDevice()).orElse(""));
            cell.setCellStyle(cellStyle(
                    styleMap, devicePerspectiveDeviceColumnIndex, sheet, devicePerspectiveFirstDataRow
            ));

            // 刀片型号。
            cell = CellUtil.getCell(row, devicePerspectiveToolCutterTypeColumnIndex);
            cell.setCellValue(Optional.ofNullable(devicePerspective.getToolCutterType()).orElse(""));
            cell.setCellStyle(cellStyle(
                    styleMap, devicePerspectiveToolCutterTypeColumnIndex, sheet, devicePerspectiveFirstDataRow
            ));

            // 消耗数量。
            cell = CellUtil.getCell(row, devicePerspectiveConsumingQuantityColumnIndex);
            cell.setCellValue(Optional.ofNullable(devicePerspective.getConsumingQuantity()).orElse(0));
            cell.setCellStyle(cellStyle(
                    styleMap, devicePerspectiveConsumingQuantityColumnIndex, sheet, devicePerspectiveFirstDataRow
            ));

            // 价值。
            cell = CellUtil.getCell(row, devicePerspectiveWorthColumnIndex);
            cell.setCellValue(OptionalDouble.of(devicePerspective.getWorth().doubleValue()).orElse(0));
            cell.setCellStyle(cellStyle(
                    styleMap, devicePerspectiveWorthColumnIndex, sheet, devicePerspectiveFirstDataRow
            ));

            // 年份。
            cell = CellUtil.getCell(row, devicePerspectiveYearColumnIndex);
            cell.setCellValue(Optional.ofNullable(devicePerspective.getYear()).orElse(0));
            cell.setCellStyle(cellStyle(
                    styleMap, devicePerspectiveYearColumnIndex, sheet, devicePerspectiveFirstDataRow
            ));

            // 统计日期。
            cell = CellUtil.getCell(row, devicePerspectiveStatisticsDateColumnIndex);
            cell.setCellValue(statisticsDate);
            cell.setCellStyle(cellStyle(
                    styleMap, devicePerspectiveStatisticsDateColumnIndex, sheet, devicePerspectiveFirstDataRow
            ));

            // 刀片代号。
            cell = CellUtil.getCell(row, devicePerspectiveToolCutterCodeColumnIndex);
            cell.setCellValue(Optional.ofNullable(devicePerspective.getToolCutterCode()).orElse(""));
            cell.setCellStyle(cellStyle(
                    styleMap, devicePerspectiveToolCutterCodeColumnIndex, sheet, devicePerspectiveFirstDataRow
            ));
        } catch (Exception e) {
            String warnMessage = "设置数据表的第 " + rowIndex + " 行(对应数据表是第 " +
                    (rowIndex + 1) + " 行)数据时出现异常，异常信息为: ";
            LOGGER.warn(warnMessage, e);
            ExportErrorInfo exportErrorInfo = new ExportErrorInfo(
                    null, sheet.getSheetName(), rowIndex, "数据源错误，详见控制台日志"
            );
            exportErrorInfos.add(exportErrorInfo);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void exportSingleToolCutterPerspective(
            Sheet sheet, int index, String statisticsDate, ToolCutterPerspective toolCutterPerspective,
            List<ExportErrorInfo> exportErrorInfos, Map<Integer, CellStyle> styleMap
    ) {
        // 定义通用变量。
        int rowIndex = toolCutterPerspectiveFirstDataRow + index;
        Row row = CellUtil.getRow(rowIndex, sheet);
        Cell cell;
        try {
            // 月份。
            cell = CellUtil.getCell(row, toolCutterPerspectiveMonthColumnIndex);
            cell.setCellValue(Optional.ofNullable(toolCutterPerspective.getMonth()).map(i -> monthArray[i]).orElse(""));
            cell.setCellStyle(cellStyle(
                    styleMap, toolCutterPerspectiveMonthColumnIndex, sheet, toolCutterPerspectiveFirstDataRow
            ));

            // 刀片型号。
            cell = CellUtil.getCell(row, toolCutterPerspectiveToolCutterTypeColumnIndex);
            cell.setCellValue(Optional.ofNullable(toolCutterPerspective.getToolCutterType()).orElse(""));
            cell.setCellStyle(cellStyle(
                    styleMap, toolCutterPerspectiveToolCutterTypeColumnIndex, sheet, toolCutterPerspectiveFirstDataRow
            ));

            // 消耗数量。
            cell = CellUtil.getCell(row, toolCutterPerspectiveConsumingQuantityColumnIndex);
            cell.setCellValue(Optional.ofNullable(toolCutterPerspective.getConsumingQuantity()).orElse(0));
            cell.setCellStyle(cellStyle(
                    styleMap, toolCutterPerspectiveConsumingQuantityColumnIndex, sheet,
                    toolCutterPerspectiveFirstDataRow
            ));

            // 价值。
            cell = CellUtil.getCell(row, toolCutterPerspectiveWorthColumnIndex);
            cell.setCellValue(OptionalDouble.of(toolCutterPerspective.getWorth().doubleValue()).orElse(0));
            cell.setCellStyle(cellStyle(
                    styleMap, toolCutterPerspectiveWorthColumnIndex, sheet, toolCutterPerspectiveFirstDataRow
            ));

            // 年份。
            cell = CellUtil.getCell(row, toolCutterPerspectiveYearColumnIndex);
            cell.setCellValue(Optional.ofNullable(toolCutterPerspective.getYear()).orElse(0));
            cell.setCellStyle(cellStyle(
                    styleMap, toolCutterPerspectiveYearColumnIndex, sheet, toolCutterPerspectiveFirstDataRow
            ));

            // 统计日期。
            cell = CellUtil.getCell(row, toolCutterPerspectiveStatisticsDateColumnIndex);
            cell.setCellValue(statisticsDate);
            cell.setCellStyle(cellStyle(
                    styleMap, toolCutterPerspectiveStatisticsDateColumnIndex, sheet, toolCutterPerspectiveFirstDataRow
            ));

            // 刀片代号。
            cell = CellUtil.getCell(row, toolCutterPerspectiveToolCutterCodeColumnIndex);
            cell.setCellValue(Optional.ofNullable(toolCutterPerspective.getToolCutterCode()).orElse(""));
            cell.setCellStyle(cellStyle(
                    styleMap, toolCutterPerspectiveToolCutterCodeColumnIndex, sheet, toolCutterPerspectiveFirstDataRow
            ));
        } catch (Exception e) {
            String warnMessage = "设置数据表的第 " + rowIndex + " 行(对应数据表是第 " +
                    (rowIndex + 1) + " 行)数据时出现异常，异常信息为: ";
            LOGGER.warn(warnMessage, e);
            ExportErrorInfo exportErrorInfo = new ExportErrorInfo(
                    null, sheet.getSheetName(), rowIndex, "数据源错误，详见控制台日志"
            );
            exportErrorInfos.add(exportErrorInfo);
        }
    }

    private CellStyle cellStyle(Map<Integer, CellStyle> styleMap, int columnIndex, Sheet sheet, int refRowIndex) {
        if (styleMap.containsKey(columnIndex)) {
            return styleMap.get(columnIndex);
        }
        Row row = CellUtil.getRow(refRowIndex, sheet);
        Cell cell = CellUtil.getCell(row, columnIndex);
        CellStyle cellStyle = cell.getCellStyle();
        styleMap.put(columnIndex, cellStyle);
        return cellStyle;
    }

    private void saveWorkbook(Workbook workbook, File file, int fileType, String password) throws Exception {
        switch (fileType) {
            case Constants.EXPORT_FILE_TYPE_XLS:
                saveXls(workbook, file, password);
                break;
            case Constants.EXPORT_FILE_TYPE_XLSX:
                saveXlsx(workbook, file, password);
                break;
            default:
                throw new IllegalStateException("不应该执行到此处，请联系开发人员");
        }
    }

    private void saveXls(Workbook workbook, File file, String password) throws Exception {
        // 如果文件不存在，则创建文件。
        FileUtil.createFileIfNotExists(file);
        // 写入文件。
        try (OutputStream out = Files.newOutputStream(file.toPath())) {
            // 设置密码。
            org.apache.poi.hssf.record.crypto.Biff8EncryptionKey.setCurrentUserPassword(
                    StringUtils.isEmpty(password) ? null : password
            );
            workbook.write(out);
        } finally {
            // 取消密码。
            org.apache.poi.hssf.record.crypto.Biff8EncryptionKey.setCurrentUserPassword(null);
        }
    }

    private void saveXlsx(Workbook workbook, File file, String password) throws Exception {
        // 如果文件不存在，则创建文件。
        FileUtil.createFileIfNotExists(file);
        // 写入文件。
        try (OutputStream out = Files.newOutputStream(file.toPath())) {
            workbook.write(out);
        }
        // 如果没有密码，则返回。
        if (StringUtils.isEmpty(password)) {
            return;
        }
        // 如果有密码，则重新读取，再加密，再覆盖原文件。
        try (POIFSFileSystem fs = new POIFSFileSystem()) {
            EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);
            Encryptor enc = info.getEncryptor();
            enc.confirmPassword(password);
            // Read in an existing OOXML file and write to encrypted output stream
            // don't forget to close the output stream otherwise the padding bytes aren't added
            try (OPCPackage opc = OPCPackage.open(file, PackageAccess.READ_WRITE);
                 OutputStream os = enc.getDataStream(fs)) {
                opc.save(os);
            }
            // Write out the encrypted version
            try (OutputStream out = Files.newOutputStream(file.toPath())) {
                fs.writeFilesystem(out);
            }
        }
    }
}
