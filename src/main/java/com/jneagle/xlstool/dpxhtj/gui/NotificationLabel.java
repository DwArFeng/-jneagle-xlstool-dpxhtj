package com.jneagle.xlstool.dpxhtj.gui;

import com.dwarfeng.dutil.basic.cna.model.ReferenceModel;
import com.dwarfeng.dutil.basic.cna.model.obs.ReferenceAdapter;
import com.dwarfeng.dutil.basic.gui.swing.SwingUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.swing.*;

/**
 * 通知面板。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
@DependsOn("viewConfiguration")
public class NotificationLabel extends JLabel {

    private static final long serialVersionUID = -5749169982482784359L;

    private final ReferenceModel<String> notificationModel;

    private final NotificationModelObserver observer = new NotificationModelObserver();

    public NotificationLabel(
            @Qualifier("notificationModel") ReferenceModel<String> notificationModel
    ) {
        this.notificationModel = notificationModel;
    }

    @PostConstruct
    public void postConstruct() {
        // 添加侦听器。
        notificationModel.addObserver(observer);
        // 同步属性。
        syncProperties();
    }

    private void syncProperties() {
        SwingUtil.invokeInEventQueue(() -> setText(notificationModel.get()));
    }

    @PreDestroy
    public void preDestroy() {
        // 移除侦听器。
        notificationModel.removeObserver(observer);
    }

    private class NotificationModelObserver extends ReferenceAdapter<String> {

        @Override
        public void fireSet(String oldValue, String newValue) {
            SwingUtil.invokeInEventQueue(() -> setText(newValue));
        }
    }
}
