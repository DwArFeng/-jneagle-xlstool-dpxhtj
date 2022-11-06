package com.jneagle.xlstool.dpxhtj.handler;

import com.dwarfeng.dutil.basic.gui.swing.SwingUtil;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class NotificationHandlerImpl implements NotificationHandler {

    @Override
    public void warn(java.awt.Component parentComponent, String message) {
        SwingUtil.invokeInEventQueue(
                () -> JOptionPane.showMessageDialog(parentComponent, message, "警告", JOptionPane.ERROR_MESSAGE)
        );
    }

    @Override
    public void error(java.awt.Component parentComponent, String message) {
        SwingUtil.invokeInEventQueue(
                () -> JOptionPane.showMessageDialog(parentComponent, message, "错误", JOptionPane.WARNING_MESSAGE)
        );
    }
}
