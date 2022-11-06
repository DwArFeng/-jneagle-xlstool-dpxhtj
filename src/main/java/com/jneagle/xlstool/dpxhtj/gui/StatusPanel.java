package com.jneagle.xlstool.dpxhtj.gui;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * 状态栏面板。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
@DependsOn("viewConfiguration")
public class StatusPanel extends JPanel {

    private static final long serialVersionUID = -2765376590181493203L;

    private final NotificationLabel notificationLabel;
    private final TaskProgressBar taskProgressBar;

    public StatusPanel(
            NotificationLabel notificationLabel,
            TaskProgressBar taskProgressBar
    ) {
        this.notificationLabel = notificationLabel;
        this.taskProgressBar = taskProgressBar;
    }

    @PostConstruct
    public void postConstruct() {
        // 初始化界面。
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(new Insets(5, 0, 0, 0)));
        add(notificationLabel, BorderLayout.CENTER);
        add(taskProgressBar, BorderLayout.EAST);
    }
}
