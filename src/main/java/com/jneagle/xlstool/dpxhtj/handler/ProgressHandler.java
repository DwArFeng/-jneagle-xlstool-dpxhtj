package com.jneagle.xlstool.dpxhtj.handler;

import com.dwarfeng.subgrade.stack.handler.Handler;
import com.jneagle.xlstool.dpxhtj.structure.ProgressObserver;

/**
 * 进度处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ProgressHandler extends Handler {

    /**
     * 添加进度观察器。
     *
     * @param observer 指定的进度观察器
     */
    void addObserver(ProgressObserver observer);

    /**
     * 移除进度观察器。
     *
     * @param observer 指定的进度观察器
     */
    void removeObserver(ProgressObserver observer);
}
