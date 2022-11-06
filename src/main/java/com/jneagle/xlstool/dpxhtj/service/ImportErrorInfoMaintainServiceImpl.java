package com.jneagle.xlstool.dpxhtj.service;

import com.dwarfeng.subgrade.impl.service.DaoOnlyBatchCrudService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyEntireLookupService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.KeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.UuidKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import com.jneagle.xlstool.dpxhtj.bean.entity.ImportErrorInfo;
import com.jneagle.xlstool.dpxhtj.dao.ImportErrorInfoDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImportErrorInfoMaintainServiceImpl implements ImportErrorInfoMaintainService {

    private final ImportErrorInfoDao dao;
    private final ServiceExceptionMapper sem;
    private final DaoOnlyBatchCrudService<UuidKey, ImportErrorInfo> crudService;
    private final DaoOnlyEntireLookupService<ImportErrorInfo> entireLookupService;

    public ImportErrorInfoMaintainServiceImpl(
            ImportErrorInfoDao dao, KeyFetcher<UuidKey> keyFetcher, ServiceExceptionMapper sem
    ) {
        this.dao = dao;
        this.sem = sem;
        crudService = new DaoOnlyBatchCrudService<>(dao, keyFetcher, sem, LogLevel.WARN);
        entireLookupService = new DaoOnlyEntireLookupService<>(dao, sem, LogLevel.WARN);
    }

    @Override
    @BehaviorAnalyse
    public void clear() throws ServiceException {
        try {
            dao.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("清除所有数据时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    public boolean exists(UuidKey key) throws ServiceException {
        return crudService.exists(key);
    }

    @Override
    @BehaviorAnalyse
    public ImportErrorInfo get(UuidKey key) throws ServiceException {
        return crudService.get(key);
    }

    @Override
    @BehaviorAnalyse
    public UuidKey insert(ImportErrorInfo element) throws ServiceException {
        return crudService.insert(element);
    }

    @Override
    @BehaviorAnalyse
    public void update(ImportErrorInfo element) throws ServiceException {
        crudService.update(element);
    }

    @Override
    @BehaviorAnalyse
    public void delete(UuidKey key) throws ServiceException {
        crudService.delete(key);
    }

    @Override
    @BehaviorAnalyse
    public ImportErrorInfo getIfExists(UuidKey key) throws ServiceException {
        return crudService.getIfExists(key);
    }

    @Override
    @BehaviorAnalyse
    public UuidKey insertIfNotExists(ImportErrorInfo element) throws ServiceException {
        return crudService.insertIfNotExists(element);
    }

    @Override
    @BehaviorAnalyse
    public void updateIfExists(ImportErrorInfo element) throws ServiceException {
        crudService.updateIfExists(element);
    }

    @Override
    @BehaviorAnalyse
    public void deleteIfExists(UuidKey key) throws ServiceException {
        crudService.deleteIfExists(key);
    }

    @Override
    @BehaviorAnalyse
    public UuidKey insertOrUpdate(ImportErrorInfo element) throws ServiceException {
        return crudService.insertOrUpdate(element);
    }

    @Override
    @BehaviorAnalyse
    public boolean allExists(@SkipRecord List<UuidKey> keys) throws ServiceException {
        return crudService.allExists(keys);
    }

    @Override
    @BehaviorAnalyse
    public boolean nonExists(@SkipRecord List<UuidKey> keys) throws ServiceException {
        return crudService.nonExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<ImportErrorInfo> batchGet(@SkipRecord List<UuidKey> keys) throws ServiceException {
        return crudService.batchGet(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<UuidKey> batchInsert(@SkipRecord List<ImportErrorInfo> elements) throws ServiceException {
        return crudService.batchInsert(elements);
    }

    @Override
    @BehaviorAnalyse
    public void batchUpdate(@SkipRecord List<ImportErrorInfo> elements) throws ServiceException {
        crudService.batchUpdate(elements);
    }

    @Override
    @BehaviorAnalyse
    public void batchDelete(@SkipRecord List<UuidKey> keys) throws ServiceException {
        crudService.batchDelete(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<ImportErrorInfo> batchGetIfExists(@SkipRecord List<UuidKey> keys) throws ServiceException {
        return crudService.batchGetIfExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<UuidKey> batchInsertIfExists(@SkipRecord List<ImportErrorInfo> elements) throws ServiceException {
        return crudService.batchInsertIfExists(elements);
    }

    @Override
    @BehaviorAnalyse
    public void batchUpdateIfExists(@SkipRecord List<ImportErrorInfo> elements) throws ServiceException {
        crudService.batchUpdateIfExists(elements);
    }

    @Override
    @BehaviorAnalyse
    public void batchDeleteIfExists(@SkipRecord List<UuidKey> keys) throws ServiceException {
        crudService.batchDeleteIfExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<UuidKey> batchInsertOrUpdate(@SkipRecord List<ImportErrorInfo> elements) throws ServiceException {
        return crudService.batchInsertOrUpdate(elements);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public PagedData<ImportErrorInfo> lookup() throws ServiceException {
        return entireLookupService.lookup();
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public PagedData<ImportErrorInfo> lookup(PagingInfo pagingInfo) throws ServiceException {
        return entireLookupService.lookup(pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<ImportErrorInfo> lookupAsList() throws ServiceException {
        return entireLookupService.lookupAsList();
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<ImportErrorInfo> lookupAsList(PagingInfo pagingInfo) throws ServiceException {
        return entireLookupService.lookupAsList(pagingInfo);
    }
}
