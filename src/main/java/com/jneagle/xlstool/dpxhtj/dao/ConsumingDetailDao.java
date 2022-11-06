package com.jneagle.xlstool.dpxhtj.dao;

import com.dwarfeng.subgrade.stack.bean.key.UuidKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.jneagle.xlstool.dpxhtj.bean.entity.ConsumingDetail;

/**
 * 消耗详细信息数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ConsumingDetailDao extends BatchBaseDao<UuidKey, ConsumingDetail>,
        EntireLookupDao<ConsumingDetail>, ClearableDao {
}
