package com.jneagle.xlstool.dpxhtj.service;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.jneagle.xlstool.dpxhtj.bean.dto.StatisticResult;

import java.io.File;

/**
 * 数据导出服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface DataExportService extends ProgressService {

    /**
     * 执行导出动作。
     *
     * @param statisticResult 统计结果。
     * @param file            指定的文件。
     * @param fileType        文件的类型。
     * @param password        文件的密码。
     * @throws ServiceException 服务异常。
     */
    void execExport(StatisticResult statisticResult, File file, int fileType, String password) throws ServiceException;
}
