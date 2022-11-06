package com.jneagle.xlstool.dpxhtj.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DataImportHandlerImpl extends AbstractProgressHandler implements DataImportHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataImportHandlerImpl.class);

    @Override
    public void execImport(File file, String password) {
        LOGGER.info("执行导入: 文件: {}, 密码: {}", file, password);
    }
}
