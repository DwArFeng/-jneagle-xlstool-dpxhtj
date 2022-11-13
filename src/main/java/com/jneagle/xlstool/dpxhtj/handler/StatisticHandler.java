package com.jneagle.xlstool.dpxhtj.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.jneagle.xlstool.dpxhtj.bean.dto.StatisticResult;

/**
 * 统计处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface StatisticHandler extends ProgressHandler {

    /**
     * 执行统计动作。
     *
     * @return 统计结果。
     * @throws HandlerException 处理器异常。
     */
    StatisticResult execStatistic() throws HandlerException;
}
