package com.jneagle.xlstool.dpxhtj.dao;

import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.UuidKey;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import com.jneagle.xlstool.dpxhtj.bean.entity.ConsumingDetail;
import org.dozer.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Repository
public class ConsumingDetailDaoImpl implements ConsumingDetailDao {

    private final Mapper mapper;

    private final Map<UuidKey, ConsumingDetail> memory = new LinkedHashMap<>();

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public ConsumingDetailDaoImpl(Mapper mapper) {
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
    public ConsumingDetail get(UuidKey key) throws DaoException {
        lock.readLock().lock();
        try {
            return cloneBean(memory.get(key));
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public UuidKey insert(ConsumingDetail element) throws DaoException {
        lock.writeLock().lock();
        try {
            ConsumingDetail neoElement = cloneBean(element);
            memory.put(neoElement.getKey(), neoElement);
            return neoElement.getKey();
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public void update(ConsumingDetail element) throws DaoException {
        lock.writeLock().lock();
        try {
            ConsumingDetail neoElement = cloneBean(element);
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
    public List<ConsumingDetail> batchGet(@SkipRecord List<UuidKey> keys) throws DaoException {
        lock.readLock().lock();
        try {
            List<ConsumingDetail> result = new ArrayList<>();
            for (UuidKey key : keys) {
                ConsumingDetail consumingDetail = cloneBean(memory.get(key));
                result.add(consumingDetail);
            }
            return result;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<UuidKey> batchInsert(@SkipRecord List<ConsumingDetail> elements) throws DaoException {
        lock.writeLock().lock();
        try {
            List<UuidKey> result = new ArrayList<>();
            for (ConsumingDetail element : elements) {
                ConsumingDetail neoElement = cloneBean(element);
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
    public void batchUpdate(@SkipRecord List<ConsumingDetail> elements) throws DaoException {
        lock.writeLock().lock();
        try {
            for (ConsumingDetail element : elements) {
                ConsumingDetail neoElement = cloneBean(element);
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
    public List<ConsumingDetail> lookup() throws DaoException {
        lock.readLock().lock();
        try {
            List<ConsumingDetail> result = new ArrayList<>();
            for (ConsumingDetail consumingDetail : memory.values()) {
                result.add(cloneBean(consumingDetail));
            }
            return result;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<ConsumingDetail> lookup(PagingInfo pagingInfo) throws DaoException {
        lock.readLock().lock();
        try {
            int beginIndex = pagingInfo.getPage() * pagingInfo.getRows();
            int endIndex = beginIndex + pagingInfo.getRows();
            List<ConsumingDetail> subList = new ArrayList<>(memory.values()).subList(beginIndex, endIndex);
            List<ConsumingDetail> result = new ArrayList<>();
            for (ConsumingDetail consumingDetail : subList) {
                result.add(cloneBean(consumingDetail));
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

    private ConsumingDetail cloneBean(ConsumingDetail consumingDetail) throws DaoException {
        try {
            return mapper.map(consumingDetail, ConsumingDetail.class);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
