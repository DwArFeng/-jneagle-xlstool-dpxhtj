package com.jneagle.xlstool.dpxhtj.service;

import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import com.jneagle.xlstool.dpxhtj.handler.DataImportHandler;
import com.jneagle.xlstool.dpxhtj.structure.ProgressObserver;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DataImportServiceImpl implements DataImportService {

    private final DataImportHandler handler;

    private final ServiceExceptionMapper sem;

    public DataImportServiceImpl(DataImportHandler handler, ServiceExceptionMapper sem) {
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
    public void execImport(File file, String password) throws ServiceException {
        try {
            handler.execImport(file, password);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("发生异常", LogLevel.WARN, sem, e);
        }
    }
}
