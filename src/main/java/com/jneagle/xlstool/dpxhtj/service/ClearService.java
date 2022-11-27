package com.jneagle.xlstool.dpxhtj.service;

import com.dwarfeng.subgrade.stack.exception.ServiceException;

/**
 * 清除服务。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface ClearService extends ProgressService {

    /**
     * 清除数据。
     *
     * @throws ServiceException 服务异常。
     */
    void clear() throws ServiceException;
}
