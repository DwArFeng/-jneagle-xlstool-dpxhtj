package com.jneagle.xlstool.dpxhtj.service;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.jneagle.xlstool.dpxhtj.bean.dto.StatisticResult;

/**
 * 统计服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface StatisticService extends ProgressService {

    /**
     * 执行统计动作。
     *
     * @return 统计结果。
     * @throws ServiceException 服务异常。
     */
    StatisticResult execStatistic() throws ServiceException;
}
