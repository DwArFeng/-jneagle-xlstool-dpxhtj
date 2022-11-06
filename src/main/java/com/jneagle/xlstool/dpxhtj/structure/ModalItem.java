package com.jneagle.xlstool.dpxhtj.structure;

import com.dwarfeng.dutil.develop.setting.SettingEnumItem;
import com.dwarfeng.dutil.develop.setting.SettingInfo;
import com.dwarfeng.dutil.develop.setting.info.BooleanSettingInfo;
import com.dwarfeng.dutil.develop.setting.info.FileSettingInfo;

/**
 * 模态条目
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public enum ModalItem implements SettingEnumItem {

    /**
     * 数据导入 - 上一次读取的文件。
     */
    MODAL_DATA_IMPORT_LAST_IMPORT_FILE("modal.structured_data.last_import_file", new FileSettingInfo("attributes")),
    /**
     * 数据导入 - 上一次是否读取。
     */
    MODAL_DATA_IMPORT_LAST_IMPORT_FLAG("modal.structured_data.last-import_flag", new BooleanSettingInfo("false"));

    private final String name;
    private final SettingInfo settingInfo;

    ModalItem(String name, SettingInfo settingInfo) {
        this.name = name;
        this.settingInfo = settingInfo;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public SettingInfo getSettingInfo() {
        return settingInfo;
    }
}
