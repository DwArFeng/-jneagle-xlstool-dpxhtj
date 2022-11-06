package com.jneagle.xlstool.dpxhtj.service;

import com.dwarfeng.subgrade.stack.exception.ServiceException;

import java.io.File;

/**
 * 数据导入服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface DataImportService extends ProgressService {

    /**
     * 执行导入动作。
     *
     * @param file     指定的文件。
     * @param password 指定的密码。
     * @throws ServiceException 服务异常。
     */
    void execImport(File file, String password) throws ServiceException;
}
