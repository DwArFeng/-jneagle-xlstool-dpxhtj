package com.jneagle.xlstool.dpxhtj.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.jneagle.xlstool.dpxhtj.bean.dto.StatisticResult;
import com.jneagle.xlstool.dpxhtj.bean.dto.StatisticResult.DevicePerspective;
import com.jneagle.xlstool.dpxhtj.bean.dto.StatisticResult.PersonPerspective;
import com.jneagle.xlstool.dpxhtj.bean.dto.StatisticResult.ToolCutterPerspective;
import com.jneagle.xlstool.dpxhtj.bean.entity.ConsumingDetail;
import com.jneagle.xlstool.dpxhtj.service.ConsumingDetailMaintainService;
import com.jneagle.xlstool.dpxhtj.structure.ProgressStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class StatisticHandlerImpl extends AbstractProgressHandler implements StatisticHandler {

    private final ConsumingDetailMaintainService consumingDetailMaintainService;

    public StatisticHandlerImpl(ConsumingDetailMaintainService consumingDetailMaintainService) {
        this.consumingDetailMaintainService = consumingDetailMaintainService;
    }

    @Override
    public StatisticResult execStatistic() throws HandlerException {
        try {
            // 广播进度变更事件。
            fireProgressChanged(ProgressStatus.UNCERTAIN);

            // 获取所有的消耗明细数据。
            List<ConsumingDetail> consumingDetails = consumingDetailMaintainService.lookupAsList();

            // 定义统计结果中间变量。
            Map<PersonPerspectiveKey, PersonPerspective> personPerspectiveMap = new LinkedHashMap<>();
            Map<DevicePerspectiveKey, DevicePerspective> devicePerspectiveMap = new LinkedHashMap<>();
            Map<ToolCutterPerspectiveKey, ToolCutterPerspective> toolCutterPerspectiveMap = new LinkedHashMap<>();

            // 设置总体进度。
            int progress = 0;
            fireProgressChanged(progress, consumingDetails.size());

            // 遍历所有消耗明细，处理单条数据。
            for (ConsumingDetail consumingDetail : consumingDetails) {
                execStatisticSingleData(consumingDetail, personPerspectiveMap, devicePerspectiveMap, toolCutterPerspectiveMap);
                fireProgressChanged(++progress, consumingDetails.size());
            }

            // 返回结果。
            return new StatisticResult(
                    new ArrayList<>(personPerspectiveMap.values()),
                    new ArrayList<>(devicePerspectiveMap.values()),
                    new ArrayList<>(toolCutterPerspectiveMap.values())
            );
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            // 广播进度变更事件。
            fireProgressChanged(ProgressStatus.IDLE);
        }
    }

    private void execStatisticSingleData(
            ConsumingDetail consumingDetail,
            Map<PersonPerspectiveKey, PersonPerspective> personPerspectiveMap,
            Map<DevicePerspectiveKey, DevicePerspective> devicePerspectiveMap,
            Map<ToolCutterPerspectiveKey, ToolCutterPerspective> toolCutterPerspectiveMap
    ) {
        // 获取关键参数，构造不同 Perspective 的主键。
        Date consumingDate = consumingDetail.getConsumingDate();
        Calendar consumingCalendar = Optional.ofNullable(consumingDate).map((date) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        }).orElse(null);
        Integer year = Optional.ofNullable(consumingCalendar).map(calendar -> calendar.get(Calendar.YEAR))
                .orElse(null);
        Integer month = Optional.ofNullable(consumingCalendar).map(calendar -> calendar.get(Calendar.MONTH))
                .orElse(null);
        String name = consumingDetail.getConsumingPerson();
        String toolCutterType = consumingDetail.getToolCutterType();
        String device = consumingDetail.getDevice();
        Integer consumingQuantity = Optional.ofNullable(consumingDetail.getConsumingQuantity()).orElse(0);
        BigDecimal worth = Optional.ofNullable(consumingDetail.getWorth()).orElse(BigDecimal.ZERO);
        PersonPerspectiveKey personPerspectiveKey = new PersonPerspectiveKey(year, month, name, toolCutterType, device);
        DevicePerspectiveKey devicePerspectiveKey = new DevicePerspectiveKey(year, month, toolCutterType, device);
        ToolCutterPerspectiveKey toolCutterPerspectiveKey = new ToolCutterPerspectiveKey(year, month, toolCutterType);

        // 处理 PersonPerspective。
        PersonPerspective oldPersonPerspective = personPerspectiveMap.getOrDefault(
                personPerspectiveKey, initPersonPerspective(personPerspectiveKey)
        );
        PersonPerspective neoPersonPerspective = new PersonPerspective(
                oldPersonPerspective.getMonth(), oldPersonPerspective.getName(),
                oldPersonPerspective.getToolCutterType(),
                oldPersonPerspective.getConsumingQuantity() + consumingQuantity,
                oldPersonPerspective.getWorth().add(worth),
                oldPersonPerspective.getDevice(), oldPersonPerspective.getYear()
        );
        personPerspectiveMap.put(personPerspectiveKey, neoPersonPerspective);

        // 处理 DevicePerspective。
        DevicePerspective oldDevicePerspective = devicePerspectiveMap.getOrDefault(
                devicePerspectiveKey, initDevicePerspective(devicePerspectiveKey)
        );
        DevicePerspective neoDevicePerspective = new DevicePerspective(
                oldDevicePerspective.getMonth(), oldDevicePerspective.getDevice(),
                oldDevicePerspective.getToolCutterType(),
                oldDevicePerspective.getConsumingQuantity() + consumingQuantity,
                oldDevicePerspective.getWorth().add(worth),
                oldDevicePerspective.getYear()
        );
        devicePerspectiveMap.put(devicePerspectiveKey, neoDevicePerspective);

        // 处理 ToolCutterPerspective。
        ToolCutterPerspective oldToolCutterPerspective = toolCutterPerspectiveMap.getOrDefault(
                toolCutterPerspectiveKey, initToolCutterPerspective(toolCutterPerspectiveKey)
        );
        ToolCutterPerspective neoToolCutterPerspective = new ToolCutterPerspective(
                oldToolCutterPerspective.getMonth(),
                oldToolCutterPerspective.getToolCutterType(),
                oldToolCutterPerspective.getConsumingQuantity() + consumingQuantity,
                oldToolCutterPerspective.getWorth().add(worth),
                oldToolCutterPerspective.getYear()
        );
        toolCutterPerspectiveMap.put(toolCutterPerspectiveKey, neoToolCutterPerspective);
    }

    private PersonPerspective initPersonPerspective(PersonPerspectiveKey key) {
        return new PersonPerspective(
                key.getMonth(), key.getName(), key.getToolCutterType(), 0, BigDecimal.ZERO, key.getDevice(),
                key.getYear()
        );
    }

    private DevicePerspective initDevicePerspective(DevicePerspectiveKey key) {
        return new DevicePerspective(
                key.getMonth(), key.getDevice(), key.getToolCutterType(), 0, BigDecimal.ZERO, key.getYear()
        );
    }

    private ToolCutterPerspective initToolCutterPerspective(ToolCutterPerspectiveKey key) {
        return new ToolCutterPerspective(
                key.getMonth(), key.getToolCutterType(), 0, BigDecimal.ZERO, key.getYear()
        );
    }

    private static class PersonPerspectiveKey {

        private final Integer year;
        private final Integer month;
        private final String name;
        private final String toolCutterType;
        private final String device;

        public PersonPerspectiveKey(Integer year, Integer month, String name, String toolCutterType, String device) {
            this.year = year;
            this.month = month;
            this.name = name;
            this.toolCutterType = toolCutterType;
            this.device = device;
        }

        public Integer getYear() {
            return year;
        }

        public Integer getMonth() {
            return month;
        }

        public String getName() {
            return name;
        }

        public String getToolCutterType() {
            return toolCutterType;
        }

        public String getDevice() {
            return device;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PersonPerspectiveKey that = (PersonPerspectiveKey) o;

            if (!Objects.equals(year, that.year)) return false;
            if (!Objects.equals(month, that.month)) return false;
            if (!Objects.equals(name, that.name)) return false;
            if (!Objects.equals(toolCutterType, that.toolCutterType))
                return false;
            return Objects.equals(device, that.device);
        }

        @Override
        public int hashCode() {
            int result = year != null ? year.hashCode() : 0;
            result = 31 * result + (month != null ? month.hashCode() : 0);
            result = 31 * result + (name != null ? name.hashCode() : 0);
            result = 31 * result + (toolCutterType != null ? toolCutterType.hashCode() : 0);
            result = 31 * result + (device != null ? device.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "PersonPerspectiveKey{" +
                    "year=" + year +
                    ", month=" + month +
                    ", name='" + name + '\'' +
                    ", toolCutterType='" + toolCutterType + '\'' +
                    ", device='" + device + '\'' +
                    '}';
        }
    }

    private static class DevicePerspectiveKey {

        private final Integer year;
        private final Integer month;
        private final String toolCutterType;
        private final String device;

        public DevicePerspectiveKey(Integer year, Integer month, String toolCutterType, String device) {
            this.year = year;
            this.month = month;
            this.toolCutterType = toolCutterType;
            this.device = device;
        }

        public Integer getYear() {
            return year;
        }

        public Integer getMonth() {
            return month;
        }

        public String getToolCutterType() {
            return toolCutterType;
        }

        public String getDevice() {
            return device;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DevicePerspectiveKey that = (DevicePerspectiveKey) o;

            if (!Objects.equals(year, that.year)) return false;
            if (!Objects.equals(month, that.month)) return false;
            if (!Objects.equals(toolCutterType, that.toolCutterType))
                return false;
            return Objects.equals(device, that.device);
        }

        @Override
        public int hashCode() {
            int result = year != null ? year.hashCode() : 0;
            result = 31 * result + (month != null ? month.hashCode() : 0);
            result = 31 * result + (toolCutterType != null ? toolCutterType.hashCode() : 0);
            result = 31 * result + (device != null ? device.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "DevicePerspectiveKey{" +
                    "year=" + year +
                    ", month=" + month +
                    ", toolCutterType='" + toolCutterType + '\'' +
                    ", device='" + device + '\'' +
                    '}';
        }
    }

    private static class ToolCutterPerspectiveKey {

        private final Integer year;
        private final Integer month;
        private final String toolCutterType;

        public ToolCutterPerspectiveKey(Integer year, Integer month, String toolCutterType) {
            this.year = year;
            this.month = month;
            this.toolCutterType = toolCutterType;
        }

        public Integer getYear() {
            return year;
        }

        public Integer getMonth() {
            return month;
        }

        public String getToolCutterType() {
            return toolCutterType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ToolCutterPerspectiveKey that = (ToolCutterPerspectiveKey) o;

            if (!Objects.equals(year, that.year)) return false;
            if (!Objects.equals(month, that.month)) return false;
            return Objects.equals(toolCutterType, that.toolCutterType);
        }

        @Override
        public int hashCode() {
            int result = year != null ? year.hashCode() : 0;
            result = 31 * result + (month != null ? month.hashCode() : 0);
            result = 31 * result + (toolCutterType != null ? toolCutterType.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "ToolCutterPerspectiveKey{" +
                    "year=" + year +
                    ", month=" + month +
                    ", toolCutterType='" + toolCutterType + '\'' +
                    '}';
        }
    }
}
