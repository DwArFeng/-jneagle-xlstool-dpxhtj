package com.jneagle.xlstool.dpxhtj.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.UuidKey;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 消耗详细信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ConsumingDetail implements Entity<UuidKey> {

    private static final long serialVersionUID = 547780372375006329L;

    private UuidKey key;

    /**
     * 刀片型号。
     */
    private String toolCutterType;

    /**
     * 设备。
     */
    private String device;

    /**
     * 消耗数量。
     */
    private Integer consumingQuantity;

    /**
     * 价值。
     */
    private BigDecimal worth;

    /**
     * 领用人。
     */
    private String consumingPerson;

    /**
     * 领用日期。
     */
    private Date consumingDate;

    /**
     * 备注。
     */
    private String remark;

    public ConsumingDetail() {
    }

    public ConsumingDetail(
            UuidKey key, String toolCutterType, String device, Integer consumingQuantity, BigDecimal worth,
            String consumingPerson, Date consumingDate, String remark
    ) {
        this.key = key;
        this.toolCutterType = toolCutterType;
        this.device = device;
        this.consumingQuantity = consumingQuantity;
        this.worth = worth;
        this.consumingPerson = consumingPerson;
        this.consumingDate = consumingDate;
        this.remark = remark;
    }

    @Override
    public UuidKey getKey() {
        return key;
    }

    @Override
    public void setKey(UuidKey key) {
        this.key = key;
    }

    public String getToolCutterType() {
        return toolCutterType;
    }

    public void setToolCutterType(String toolCutterType) {
        this.toolCutterType = toolCutterType;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Integer getConsumingQuantity() {
        return consumingQuantity;
    }

    public void setConsumingQuantity(Integer consumingQuantity) {
        this.consumingQuantity = consumingQuantity;
    }

    public BigDecimal getWorth() {
        return worth;
    }

    public void setWorth(BigDecimal worth) {
        this.worth = worth;
    }

    public String getConsumingPerson() {
        return consumingPerson;
    }

    public void setConsumingPerson(String consumingPerson) {
        this.consumingPerson = consumingPerson;
    }

    public Date getConsumingDate() {
        return consumingDate;
    }

    public void setConsumingDate(Date consumingDate) {
        this.consumingDate = consumingDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ConsumingDetail{" +
                "key=" + key +
                ", toolCutterType='" + toolCutterType + '\'' +
                ", device='" + device + '\'' +
                ", consumingQuantity=" + consumingQuantity +
                ", worth=" + worth +
                ", consumingPerson='" + consumingPerson + '\'' +
                ", consumingDate=" + consumingDate +
                ", remark='" + remark + '\'' +
                '}';
    }
}
