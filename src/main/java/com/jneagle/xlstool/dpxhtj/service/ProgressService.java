package com.jneagle.xlstool.dpxhtj.service;

import com.dwarfeng.subgrade.stack.service.Service;
import com.jneagle.xlstool.dpxhtj.structure.ProgressObserver;

/**
 * 进度服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ProgressService extends Service {

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
