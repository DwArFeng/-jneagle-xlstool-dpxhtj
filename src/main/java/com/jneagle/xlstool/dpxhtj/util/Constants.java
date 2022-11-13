package com.jneagle.xlstool.dpxhtj.util;

/**
 * 常量类。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class Constants {

    /**
     * 导出文件类型: XLS。
     */
    public static final int EXPORT_FILE_TYPE_XLS = 0;
    /**
     * 导出文件类型: XLSX。
     */
    public static final int EXPORT_FILE_TYPE_XLSX = 1;

    private Constants() {
        throw new IllegalStateException("禁止实例化");
    }
}
