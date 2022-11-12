package com.jneagle.xlstool.dpxhtj.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.UuidKey;

/**
 * 导入错误信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ImportErrorInfo implements Entity<UuidKey> {

    private static final long serialVersionUID = -3798767439401160407L;

    private UuidKey key;
    private String sheetName;
    private Integer rowIndex;
    private String errorMessage;

    public ImportErrorInfo() {
    }

    public ImportErrorInfo(UuidKey key, String sheetName, Integer rowIndex, String errorMessage) {
        this.key = key;
        this.sheetName = sheetName;
        this.rowIndex = rowIndex;
        this.errorMessage = errorMessage;
    }

    @Override
    public UuidKey getKey() {
        return key;
    }

    @Override
    public void setKey(UuidKey key) {
        this.key = key;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Integer getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "ImportErrorInfo{" +
                "key=" + key +
                ", sheetName=" + sheetName +
                ", rowIndex=" + rowIndex +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
