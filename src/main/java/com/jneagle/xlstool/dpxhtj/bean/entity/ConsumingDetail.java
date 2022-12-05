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

    private static final long serialVersionUID = 8556768262688051982L;

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

    /**
     * 刀片代码。
     *
     * @since 1.1.0
     */
    private String toolCutterCode;

    /**
     * 退回数量。
     *
     * @since 1.1.0
     */
    private Integer returningQuantity;

    /**
     * 退回使用信息。
     *
     * @since 1.1.1
     */
    private String returningUsageInfo;

    public ConsumingDetail() {
    }

    public ConsumingDetail(
            UuidKey key, String toolCutterType, String device, Integer consumingQuantity, BigDecimal worth,
            String consumingPerson, Date consumingDate, String remark, String toolCutterCode, Integer returningQuantity,
            String returningUsageInfo
    ) {
        this.key = key;
        this.toolCutterType = toolCutterType;
        this.device = device;
        this.consumingQuantity = consumingQuantity;
        this.worth = worth;
        this.consumingPerson = consumingPerson;
        this.consumingDate = consumingDate;
        this.remark = remark;
        this.toolCutterCode = toolCutterCode;
        this.returningQuantity = returningQuantity;
        this.returningUsageInfo = returningUsageInfo;
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

    public String getToolCutterCode() {
        return toolCutterCode;
    }

    public void setToolCutterCode(String toolCutterCode) {
        this.toolCutterCode = toolCutterCode;
    }

    public Integer getReturningQuantity() {
        return returningQuantity;
    }

    public void setReturningQuantity(Integer returningQuantity) {
        this.returningQuantity = returningQuantity;
    }

    public String getReturningUsageInfo() {
        return returningUsageInfo;
    }

    public void setReturningUsageInfo(String returningUsageInfo) {
        this.returningUsageInfo = returningUsageInfo;
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
                ", toolCutterCode='" + toolCutterCode + '\'' +
                ", returningQuantity=" + returningQuantity +
                ", returningUsageInfo='" + returningUsageInfo + '\'' +
                '}';
    }
}
