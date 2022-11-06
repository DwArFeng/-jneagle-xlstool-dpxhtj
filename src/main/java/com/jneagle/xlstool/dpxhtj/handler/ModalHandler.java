package com.jneagle.xlstool.dpxhtj.handler;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 模态处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ModalHandler extends Handler {

    /**
     * 获取模态处理器中的值。
     *
     * @param key  值的主键。
     * @param clas 值的类型。
     * @param <T>  类的泛型。
     * @return 指定键对应的值。
     */
    <T> T getValue(Name key, Class<T> clas);

    /**
     * 设置模态处理器中的值。
     *
     * @param key 值的主键。
     * @param obj 值。
     */
    void setValue(Name key, Object obj);
}
