package com.jneagle.xlstool.dpxhtj.handler;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.dutil.develop.setting.DefaultSettingHandler;
import com.dwarfeng.dutil.develop.setting.SettingHandler;
import com.dwarfeng.dutil.develop.setting.SettingUtil;
import com.dwarfeng.dutil.develop.setting.io.PropSettingValueLoader;
import com.dwarfeng.dutil.develop.setting.io.PropSettingValueSaver;
import com.jneagle.xlstool.dpxhtj.structure.ModalItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileUrlResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class ModalHandlerImpl implements ModalHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModalHandlerImpl.class);
    private static final String MODAL_CONFIG_RESOURCE = "file:conf/modal/config.properties";

    private final ApplicationContext ctx;

    private final SettingHandler settingHandler = new DefaultSettingHandler();

    public ModalHandlerImpl(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @PostConstruct
    public void postConstruct() throws Exception {
        LOGGER.info("加载模态信息...");
        SettingUtil.putEnumItems(ModalItem.class, settingHandler);
        try (
                PropSettingValueLoader loader = new PropSettingValueLoader(
                        ctx.getResource(MODAL_CONFIG_RESOURCE).getInputStream()
                )
        ) {
            loader.load(settingHandler);
        }
    }

    @PreDestroy
    public void preDestroy() throws Exception {
        LOGGER.info("保存模态信息...");
        try (
                PropSettingValueSaver saver = new PropSettingValueSaver(
                        ((FileUrlResource) ctx.getResource(MODAL_CONFIG_RESOURCE)).getOutputStream()
                )
        ) {
            saver.save(settingHandler);
        }
    }

    @Override
    public <T> T getValue(Name key, Class<T> clas) {
        return settingHandler.getParsedValidValue(key, clas);
    }

    @Override
    public void setValue(Name key, Object obj) {
        settingHandler.setParsedValue(key, obj);
    }
}
