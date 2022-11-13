package com.jneagle.xlstool.dpxhtj.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.jneagle.xlstool.dpxhtj.bean.dto.StatisticResult;

import java.io.File;

/**
 * 数据导出处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface DataExportHandler extends ProgressHandler {

    /**
     * 执行导出动作。
     *
     * @param statisticResult 统计结果。
     * @param file            指定的文件。
     * @param fileType        文件的类型。
     * @param password        文件的密码。
     * @throws HandlerException 处理器异常。
     */
    void execExport(StatisticResult statisticResult, File file, int fileType, String password) throws HandlerException;
}
