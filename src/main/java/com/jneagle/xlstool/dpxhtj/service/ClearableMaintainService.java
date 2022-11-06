package com.jneagle.xlstool.dpxhtj.service;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 可被清除的维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ClearableMaintainService extends Service {

    /**
     * 清除所有数据。
     *
     * @throws ServiceException 服务异常。
     */
    void clear() throws ServiceException;
}
