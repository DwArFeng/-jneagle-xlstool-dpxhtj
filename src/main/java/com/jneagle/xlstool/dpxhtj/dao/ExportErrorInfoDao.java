package com.jneagle.xlstool.dpxhtj.dao;

import com.dwarfeng.subgrade.stack.bean.key.UuidKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.jneagle.xlstool.dpxhtj.bean.entity.ExportErrorInfo;

/**
 * 导出错误信息数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExportErrorInfoDao extends BatchBaseDao<UuidKey, ExportErrorInfo>,
        EntireLookupDao<ExportErrorInfo>, ClearableDao {
}
