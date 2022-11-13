package com.jneagle.xlstool.dpxhtj.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * 统计结果。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class StatisticResult implements Dto {

    private static final long serialVersionUID = -7229682002810900060L;

    private List<PersonPerspective> personPerspectives;
    private List<DevicePerspective> devicePerspectives;
    private List<ToolCutterPerspective> toolCutterPerspectives;

    public StatisticResult() {
    }

    public StatisticResult(
            List<PersonPerspective> personPerspectives, List<DevicePerspective> devicePerspectives, List<ToolCutterPerspective> toolCutterPerspectives
    ) {
        this.personPerspectives = personPerspectives;
        this.devicePerspectives = devicePerspectives;
        this.toolCutterPerspectives = toolCutterPerspectives;
    }

    public List<PersonPerspective> getPersonPerspectives() {
        return personPerspectives;
    }

    public void setPersonPerspectives(List<PersonPerspective> personPerspectives) {
        this.personPerspectives = personPerspectives;
    }

    public List<DevicePerspective> getDevicePerspectives() {
        return devicePerspectives;
    }

    public void setDevicePerspectives(List<DevicePerspective> devicePerspectives) {
        this.devicePerspectives = devicePerspectives;
    }

    public List<ToolCutterPerspective> getToolCutterPerspectives() {
        return toolCutterPerspectives;
    }

    public void setToolCutterPerspectives(List<ToolCutterPerspective> toolCutterPerspectives) {
        this.toolCutterPerspectives = toolCutterPerspectives;
    }

    @Override
    public String toString() {
        return "StatisticResult{" +
                "personPerspectives=" + personPerspectives +
                ", devicePerspectives=" + devicePerspectives +
                ", toolCutterPerspectives=" + toolCutterPerspectives +
                '}';
    }

    public static class PersonPerspective implements Dto {

        private static final long serialVersionUID = -8460931232697750091L;

        /**
         * 月份（0-11）。
         */
        private Integer month;

        /**
         * 姓名。
         */
        private String name;

        /**
         * 刀片型号。
         */
        private String toolCutterType;

        /**
         * 消耗数量。
         */
        private Integer consumingQuantity;

        /**
         * 价值。
         */
        private BigDecimal worth;

        /**
         * 设备。
         */
        private String device;

        /**
         * 年份。
         */
        private Integer year;

        public PersonPerspective() {
        }

        public PersonPerspective(
                Integer month, String name, String toolCutterType, Integer consumingQuantity, BigDecimal worth,
                String device, Integer year
        ) {
            this.month = month;
            this.name = name;
            this.toolCutterType = toolCutterType;
            this.consumingQuantity = consumingQuantity;
            this.worth = worth;
            this.device = device;
            this.year = year;
        }

        public Integer getMonth() {
            return month;
        }

        public void setMonth(Integer month) {
            this.month = month;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getToolCutterType() {
            return toolCutterType;
        }

        public void setToolCutterType(String toolCutterType) {
            this.toolCutterType = toolCutterType;
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

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        @Override
        public String toString() {
            return "PersonPerspective{" +
                    "month=" + month +
                    ", name='" + name + '\'' +
                    ", toolCutterType='" + toolCutterType + '\'' +
                    ", consumingQuantity=" + consumingQuantity +
                    ", worth=" + worth +
                    ", device='" + device + '\'' +
                    ", year=" + year +
                    '}';
        }
    }

    public static class DevicePerspective implements Dto {

        private static final long serialVersionUID = -7339159446310129483L;

        /**
         * 月份（0-11）。
         */
        private Integer month;

        /**
         * 设备。
         */
        private String device;

        /**
         * 刀片型号。
         */
        private String toolCutterType;

        /**
         * 消耗数量。
         */
        private Integer consumingQuantity;

        /**
         * 价值。
         */
        private BigDecimal worth;

        /**
         * 年份。
         */
        private Integer year;

        public DevicePerspective() {
        }

        public DevicePerspective(
                Integer month, String device, String toolCutterType, Integer consumingQuantity, BigDecimal worth,
                Integer year
        ) {
            this.month = month;
            this.device = device;
            this.toolCutterType = toolCutterType;
            this.consumingQuantity = consumingQuantity;
            this.worth = worth;
            this.year = year;
        }

        public Integer getMonth() {
            return month;
        }

        public void setMonth(Integer month) {
            this.month = month;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public String getToolCutterType() {
            return toolCutterType;
        }

        public void setToolCutterType(String toolCutterType) {
            this.toolCutterType = toolCutterType;
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

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        @Override
        public String toString() {
            return "DevicePerspective{" +
                    "month=" + month +
                    ", device='" + device + '\'' +
                    ", toolCutterType='" + toolCutterType + '\'' +
                    ", consumingQuantity=" + consumingQuantity +
                    ", worth=" + worth +
                    ", year=" + year +
                    '}';
        }
    }

    public static class ToolCutterPerspective implements Dto {

        private static final long serialVersionUID = -6260888092555350713L;

        /**
         * 月份（0-11）。
         */
        private Integer month;

        /**
         * 刀片型号。
         */
        private String toolCutterType;

        /**
         * 消耗数量。
         */
        private Integer consumingQuantity;

        /**
         * 价值。
         */
        private BigDecimal worth;

        /**
         * 年份。
         */
        private Integer year;

        public ToolCutterPerspective() {
        }

        public ToolCutterPerspective(
                Integer month, String toolCutterType, Integer consumingQuantity, BigDecimal worth, Integer year
        ) {
            this.month = month;
            this.toolCutterType = toolCutterType;
            this.consumingQuantity = consumingQuantity;
            this.worth = worth;
            this.year = year;
        }

        public Integer getMonth() {
            return month;
        }

        public void setMonth(Integer month) {
            this.month = month;
        }

        public String getToolCutterType() {
            return toolCutterType;
        }

        public void setToolCutterType(String toolCutterType) {
            this.toolCutterType = toolCutterType;
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

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        @Override
        public String toString() {
            return "ToolCutterPerspective{" +
                    "month=" + month +
                    ", toolCutterType='" + toolCutterType + '\'' +
                    ", consumingQuantity=" + consumingQuantity +
                    ", worth=" + worth +
                    ", year=" + year +
                    '}';
        }
    }
}
