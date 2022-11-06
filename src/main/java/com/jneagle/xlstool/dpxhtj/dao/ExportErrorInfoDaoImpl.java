package com.jneagle.xlstool.dpxhtj.dao;

import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.UuidKey;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import com.jneagle.xlstool.dpxhtj.bean.entity.ExportErrorInfo;
import org.dozer.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Repository
public class ExportErrorInfoDaoImpl implements ExportErrorInfoDao {

    private final Mapper mapper;

    private final Map<UuidKey, ExportErrorInfo> memory = new LinkedHashMap<>();

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public ExportErrorInfoDaoImpl(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    @BehaviorAnalyse
    public void clear() {
        lock.writeLock().lock();
        try {
            memory.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public boolean exists(UuidKey key) {
        lock.readLock().lock();
        try {
            return memory.containsKey(key);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public ExportErrorInfo get(UuidKey key) throws DaoException {
        lock.readLock().lock();
        try {
            return cloneBean(memory.get(key));
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public UuidKey insert(ExportErrorInfo element) throws DaoException {
        lock.writeLock().lock();
        try {
            ExportErrorInfo neoElement = cloneBean(element);
            memory.put(neoElement.getKey(), neoElement);
            return neoElement.getKey();
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public void update(ExportErrorInfo element) throws DaoException {
        lock.writeLock().lock();
        try {
            ExportErrorInfo neoElement = cloneBean(element);
            memory.put(neoElement.getKey(), neoElement);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public void delete(UuidKey key) {
        lock.writeLock().lock();
        try {
            memory.keySet().remove(key);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public boolean allExists(@SkipRecord List<UuidKey> keys) {
        lock.readLock().lock();
        try {
            return memory.keySet().containsAll(keys);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public boolean nonExists(@SkipRecord List<UuidKey> keys) {
        lock.readLock().lock();
        try {
            for (UuidKey key : keys) {
                if (memory.containsKey(key)) {
                    return false;
                }
            }
            return true;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<ExportErrorInfo> batchGet(@SkipRecord List<UuidKey> keys) throws DaoException {
        lock.readLock().lock();
        try {
            List<ExportErrorInfo> result = new ArrayList<>();
            for (UuidKey key : keys) {
                ExportErrorInfo exportErrorInfo = cloneBean(memory.get(key));
                result.add(exportErrorInfo);
            }
            return result;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<UuidKey> batchInsert(@SkipRecord List<ExportErrorInfo> elements) throws DaoException {
        lock.writeLock().lock();
        try {
            List<UuidKey> result = new ArrayList<>();
            for (ExportErrorInfo element : elements) {
                ExportErrorInfo neoElement = cloneBean(element);
                memory.put(neoElement.getKey(), neoElement);
                result.add(neoElement.getKey());
            }
            return result;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public void batchUpdate(@SkipRecord List<ExportErrorInfo> elements) throws DaoException {
        lock.writeLock().lock();
        try {
            for (ExportErrorInfo element : elements) {
                ExportErrorInfo neoElement = cloneBean(element);
                memory.put(neoElement.getKey(), neoElement);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public void batchDelete(@SkipRecord List<UuidKey> keys) {
        lock.writeLock().lock();
        try {
            keys.forEach(memory.keySet()::remove);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<ExportErrorInfo> lookup() throws DaoException {
        lock.readLock().lock();
        try {
            List<ExportErrorInfo> result = new ArrayList<>();
            for (ExportErrorInfo exportErrorInfo : memory.values()) {
                result.add(cloneBean(exportErrorInfo));
            }
            return result;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<ExportErrorInfo> lookup(PagingInfo pagingInfo) throws DaoException {
        lock.readLock().lock();
        try {
            int beginIndex = pagingInfo.getPage() * pagingInfo.getRows();
            int endIndex = beginIndex + pagingInfo.getRows();
            List<ExportErrorInfo> subList = new ArrayList<>(memory.values()).subList(beginIndex, endIndex);
            List<ExportErrorInfo> result = new ArrayList<>();
            for (ExportErrorInfo exportErrorInfo : subList) {
                result.add(cloneBean(exportErrorInfo));
            }
            return result;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public int lookupCount() {
        lock.readLock().lock();
        try {
            return memory.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    private ExportErrorInfo cloneBean(ExportErrorInfo exportErrorInfo) throws DaoException {
        try {
            return mapper.map(exportErrorInfo, ExportErrorInfo.class);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
