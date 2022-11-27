package com.jneagle.xlstool.dpxhtj.configuration;

import com.dwarfeng.dutil.basic.cna.model.DefaultReferenceModel;
import com.dwarfeng.dutil.basic.cna.model.ReferenceModel;
import com.dwarfeng.dutil.basic.gui.swing.MappingTableModel;
import com.dwarfeng.dutil.basic.gui.swing.MappingTableModel.MappingInfos;
import com.jneagle.xlstool.dpxhtj.bean.entity.ConsumingDetail;
import com.jneagle.xlstool.dpxhtj.bean.entity.ExportErrorInfo;
import com.jneagle.xlstool.dpxhtj.bean.entity.ImportErrorInfo;
import com.jneagle.xlstool.dpxhtj.handler.ModalHandler;
import com.jneagle.xlstool.dpxhtj.structure.ModalItem;
import com.jneagle.xlstool.dpxhtj.structure.ProgressStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

@Configuration
public class ViewConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewConfiguration.class);

    @PostConstruct
    public void init() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            LOGGER.warn("系统不支持 NimbusLookAndFeel 主题，将使用默认主题", e);
        }
    }

    @Bean
    public ReferenceModel<String> notificationModel() {
        return new DefaultReferenceModel<>();
    }

    @Bean
    public ReferenceModel<ProgressStatus> progressStatusModel() {
        return new DefaultReferenceModel<>(ProgressStatus.IDLE);
    }

    @Bean
    public ReferenceModel<File> importFileModel(ModalHandler modalHandler) {
        boolean loadFlag = modalHandler.getValue(ModalItem.MODAL_DATA_IMPORT_LAST_IMPORT_FLAG, Boolean.class);
        File file = modalHandler.getValue(ModalItem.MODAL_DATA_IMPORT_LAST_IMPORT_FILE, File.class);
        if (!loadFlag) {
            file = null;
        }
        return new DefaultReferenceModel<>(file);
    }

    @Bean
    public ReferenceModel<File> exportFileModel(ModalHandler modalHandler) {
        boolean loadFlag = modalHandler.getValue(ModalItem.MODAL_DATA_EXPORT_LAST_EXPORT_FLAG, Boolean.class);
        File file = modalHandler.getValue(ModalItem.MODAL_DATA_EXPORT_LAST_EXPORT_FILE, File.class);
        if (!loadFlag) {
            file = null;
        }
        return new DefaultReferenceModel<>(file);
    }

    @Bean
    public ReferenceModel<Integer> exportFileTypeModel(ModalHandler modalHandler) {
        boolean loadFlag = modalHandler.getValue(ModalItem.MODAL_DATA_EXPORT_LAST_EXPORT_FLAG, Boolean.class);
        Integer type = modalHandler.getValue(ModalItem.MODAL_DATA_EXPORT_LAST_EXPORT_FILE_TYPE, Integer.class);
        if (!loadFlag) {
            type = null;
        }
        return new DefaultReferenceModel<>(type);
    }

    @Bean
    public ReferenceModel<String> importPasswordModel() {
        return new DefaultReferenceModel<>();
    }

    @Bean
    public ReferenceModel<String> exportPasswordModel() {
        return new DefaultReferenceModel<>();
    }

    @Bean
    public MappingTableModel<ConsumingDetail> consumingDetailTableModel() {
        return new MappingTableModel<>(ConsumingDetail.class, ConsumingDetailMappingInfo.class);
    }

    @MappingTableModel.TableColumn(
            columnName = "刀片代号", columnValueGetterName = "getToolCutterCode", columnClass = String.class
    )
    @MappingTableModel.TableColumn(
            columnName = "刀片型号", columnValueGetterName = "getToolCutterType", columnClass = String.class
    )
    @MappingTableModel.TableColumn(
            columnName = "设备", columnValueGetterName = "getDevice", columnClass = String.class
    )
    @MappingTableModel.TableColumn(
            columnName = "设备", columnValueGetterName = "getDevice", columnClass = String.class
    )
    @MappingTableModel.TableColumn(
            columnName = "消耗数量", columnValueGetterName = "getConsumingQuantity", columnClass = Integer.class
    )
    @MappingTableModel.TableColumn(
            columnName = "退回数量", columnValueGetterName = "getReturningQuantity", columnClass = Integer.class
    )
    @MappingTableModel.TableColumn(
            columnName = "价值", columnValueGetterName = "getWorth", columnClass = BigDecimal.class
    )
    @MappingTableModel.TableColumn(
            columnName = "领用人", columnValueGetterName = "getConsumingPerson", columnClass = String.class
    )
    @MappingTableModel.TableColumn(
            columnName = "领用日期", columnValueGetterName = "getConsumingDate", columnClass = Date.class
    )
    @MappingTableModel.TableColumn(
            columnName = "备注", columnValueGetterName = "getRemark", columnClass = String.class
    )
    private interface ConsumingDetailMappingInfo extends MappingInfos {
    }

    @Bean
    public MappingTableModel<ImportErrorInfo> importErrorInfoTableModel() {
        return new MappingTableModel<>(ImportErrorInfo.class, ImportErrorInfoMappingInfo.class);
    }

    @MappingTableModel.TableColumn(
            columnName = "工作簿名", columnValueGetterName = "getSheetName", columnClass = String.class
    )
    @MappingTableModel.TableColumn(
            columnName = "行索引", columnValueGetterName = "getRowIndex", columnClass = Integer.class
    )
    @MappingTableModel.TableColumn(
            columnName = "错误信息", columnValueGetterName = "getErrorMessage", columnClass = String.class
    )
    private interface ImportErrorInfoMappingInfo extends MappingInfos {
    }

    @Bean
    public MappingTableModel<ExportErrorInfo> exportErrorInfoTableModel() {
        return new MappingTableModel<>(ExportErrorInfo.class, ExportErrorInfoMappingInfo.class);
    }

    @MappingTableModel.TableColumn(
            columnName = "工作簿名", columnValueGetterName = "getSheetName", columnClass = String.class
    )
    @MappingTableModel.TableColumn(
            columnName = "行索引", columnValueGetterName = "getRowIndex", columnClass = Integer.class
    )
    @MappingTableModel.TableColumn(
            columnName = "错误信息", columnValueGetterName = "getErrorMessage", columnClass = String.class
    )
    private interface ExportErrorInfoMappingInfo extends MappingInfos {
    }
}
