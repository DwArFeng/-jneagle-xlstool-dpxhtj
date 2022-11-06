package com.jneagle.xlstool.dpxhtj.gui;

import com.dwarfeng.dutil.basic.cna.model.ReferenceModel;
import com.dwarfeng.dutil.basic.gui.swing.SwingUtil;
import com.dwarfeng.springterminator.stack.handler.Terminator;
import org.springframework.beans.factory.annotation.Qualifier;
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

    private static final long serialVersionUID = 743769340495353537L;

    private final Terminator terminator;

    private final ReferenceModel<String> notificationModel;

    private final BodyGridBagPane bodyGridBagPane;
    private final StatusPanel statusPanel;


    public MainFrame(
            Terminator terminator,
            @Qualifier("notificationModel") ReferenceModel<String> notificationModel,
            BodyGridBagPane bodyGridBagPane,
            StatusPanel statusPanel
    ) {
        this.terminator = terminator;
        this.notificationModel = notificationModel;
        this.bodyGridBagPane = bodyGridBagPane;
        this.statusPanel = statusPanel;
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

        // 在 contentPanel 的中心位置添加 bodyGridBagPane。
        contentPanel.add(bodyGridBagPane, BorderLayout.CENTER);

        // 在 contentPanel 的南方位置添加 statusPanel。
        contentPanel.add(statusPanel, BorderLayout.SOUTH);

        // 显示就绪
        notificationModel.set("就绪...");

        // 启动界面
        SwingUtil.invokeInEventQueue(() -> setVisible(true));
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
