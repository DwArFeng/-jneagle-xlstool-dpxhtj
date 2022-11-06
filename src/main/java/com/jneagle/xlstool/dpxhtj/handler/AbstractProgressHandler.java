package com.jneagle.xlstool.dpxhtj.handler;


import com.jneagle.xlstool.dpxhtj.structure.ProgressObserver;
import com.jneagle.xlstool.dpxhtj.structure.ProgressStatus;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * 进度处理器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractProgressHandler implements ProgressHandler {

    protected final Set<ProgressObserver> observers = Collections.newSetFromMap(new WeakHashMap<>());

    @Override
    public void addObserver(ProgressObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(ProgressObserver observer) {
        observers.remove(observer);
    }

    protected void fireProgressChanged(ProgressStatus progressStatus) {
        for (ProgressObserver observer : observers) {
            observer.onProgressChanged(progressStatus);
        }
    }

    protected void fireProgressChanged(int current, int total) {
        ProgressStatus progressStatus = new ProgressStatus(current, total);
        for (ProgressObserver observer : observers) {
            observer.onProgressChanged(progressStatus);
        }
    }
}
