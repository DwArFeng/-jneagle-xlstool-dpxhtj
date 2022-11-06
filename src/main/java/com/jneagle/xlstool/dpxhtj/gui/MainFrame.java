package com.jneagle.xlstool.dpxhtj.gui;

import com.dwarfeng.springterminator.stack.handler.Terminator;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 主界面。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
@DependsOn("viewConfiguration")
public class MainFrame extends JFrame {

    private final Terminator terminator;

    public MainFrame(Terminator terminator) {
        this.terminator = terminator;
    }

    @PostConstruct
    public void postConstruct() {
        // 初始化窗口。
        setTitle("刀片消耗统计");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 600, 750);
        addWindowListener(new WindowClosingListener());
        // 初始化 contentPanel。
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPanel);
        contentPanel.setLayout(new BorderLayout(0, 0));
        // 初始化 bodyPanel。
        // contentPanel.add(bodyTabbedPane, BorderLayout.CENTER);
        // 初始化 statusPanel。
        // contentPanel.add(statusPanel, BorderLayout.SOUTH);
        // 启动界面
        setVisible(true);
    }

    private class WindowClosingListener extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            // 因为 terminator 的退出方法存在一定的延时，为了视觉效果看起来不卡顿，首先使界面不可见。
            setVisible(false);
            terminator.exit(0);
        }
    }
}
