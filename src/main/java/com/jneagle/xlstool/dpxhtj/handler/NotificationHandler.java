package com.jneagle.xlstool.dpxhtj.handler;

import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 通知处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface NotificationHandler extends Handler {

    /**
     * 在界面上显示警告通知对话框。
     *
     * @param parentComponent 对话框的父组件。
     * @param message         通知内容。
     */
    void warn(java.awt.Component parentComponent, String message);

    /**
     * 在界面上显示错误通知对话框。
     *
     * @param parentComponent 对话框的父组件。
     * @param message         通知内容。
     */
    void error(java.awt.Component parentComponent, String message);
}
