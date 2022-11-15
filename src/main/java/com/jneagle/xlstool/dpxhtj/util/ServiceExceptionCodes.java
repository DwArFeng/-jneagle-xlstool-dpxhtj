package com.jneagle.xlstool.dpxhtj.util;

import com.dwarfeng.subgrade.stack.exception.ServiceException;

/**
 * 服务异常代码。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class ServiceExceptionCodes {

    private static int EXCEPTION_CODE_OFFSET = 11000;

    public static final ServiceException.Code WRONG_PASSWORD =
            new ServiceException.Code(offset(0), "wrong password");
    public static final ServiceException.Code TEMPLATE_LOAD_FAILED =
            new ServiceException.Code(offset(10), "template load failed");

    @SuppressWarnings("SameParameterValue")
    private static int offset(int i) {
        return EXCEPTION_CODE_OFFSET + i;
    }

    /**
     * 获取异常代号的偏移量。
     *
     * @return 异常代号的偏移量。
     */
    public static int getExceptionCodeOffset() {
        return EXCEPTION_CODE_OFFSET;
    }

    /**
     * 设置异常代号的偏移量。
     *
     * @param exceptionCodeOffset 指定的异常代号的偏移量。
     */
    public static void setExceptionCodeOffset(int exceptionCodeOffset) {
        // 设置 EXCEPTION_CODE_OFFSET 的值。
        EXCEPTION_CODE_OFFSET = exceptionCodeOffset;

        // 以新的 EXCEPTION_CODE_OFFSET 为基准，更新异常代码的值。
        WRONG_PASSWORD.setCode(offset(0));
        TEMPLATE_LOAD_FAILED.setCode(offset(10));
    }

    private ServiceExceptionCodes() {
        throw new IllegalStateException("禁止实例化");
    }
}
