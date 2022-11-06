package com.jneagle.xlstool.dpxhtj.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.io.File;

/**
 * 数据导入处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface DataImportHandler extends ProgressHandler {

    /**
     * 执行导入动作。
     *
     * @param file     指定的文件。
     * @param password 指定的密码。
     * @throws HandlerException 处理器异常。
     */
    void execImport(File file, String password) throws HandlerException;
}
