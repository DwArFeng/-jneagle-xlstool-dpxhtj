package com.jneagle.xlstool.dpxhtj.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 清除处理器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface ClearHandler extends ProgressHandler {

    /**
     * 清除数据。
     *
     * @throws HandlerException 处理器异常。
     */
    void clear() throws HandlerException;
}
