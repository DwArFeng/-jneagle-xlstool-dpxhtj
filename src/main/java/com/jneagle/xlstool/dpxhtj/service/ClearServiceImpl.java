package com.jneagle.xlstool.dpxhtj.service;

import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import com.jneagle.xlstool.dpxhtj.handler.ClearHandler;
import com.jneagle.xlstool.dpxhtj.structure.ProgressObserver;
import org.springframework.stereotype.Service;

@Service
public class ClearServiceImpl implements ClearService {

    private final ClearHandler handler;

    private final ServiceExceptionMapper sem;

    public ClearServiceImpl(ClearHandler handler, ServiceExceptionMapper sem) {
        this.handler = handler;
        this.sem = sem;
    }

    @Override
    public void addObserver(ProgressObserver observer) {
        handler.addObserver(observer);
    }

    @Override
    public void removeObserver(ProgressObserver observer) {
        handler.removeObserver(observer);
    }

    @Override
    public void clear() throws ServiceException {
        try {
            handler.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("发生异常", LogLevel.WARN, sem, e);
        }
    }
}
