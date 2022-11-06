package com.jneagle.xlstool.dpxhtj.dao;

import com.dwarfeng.subgrade.stack.dao.Dao;
import com.dwarfeng.subgrade.stack.exception.DaoException;

/**
 * 可被清除的数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ClearableDao extends Dao {

    /**
     * 清除所有数据。
     *
     * @throws DaoException 数据访问层异常。
     */
    void clear() throws DaoException;
}
