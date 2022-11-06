package com.jneagle.xlstool.djxhtj.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

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
}
