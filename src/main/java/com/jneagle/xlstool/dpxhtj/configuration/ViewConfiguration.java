package com.jneagle.xlstool.dpxhtj.configuration;

import com.dwarfeng.dutil.basic.cna.model.DefaultReferenceModel;
import com.dwarfeng.dutil.basic.cna.model.ReferenceModel;
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
    public ReferenceModel<String> importPasswordModel() {
        return new DefaultReferenceModel<>();
    }
}
