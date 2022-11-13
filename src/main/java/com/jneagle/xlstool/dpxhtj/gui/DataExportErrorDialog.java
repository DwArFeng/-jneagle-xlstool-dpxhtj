package com.jneagle.xlstool.dpxhtj.gui;

import com.dwarfeng.dutil.basic.gui.swing.MappingTableModel;
import com.jneagle.xlstool.dpxhtj.bean.entity.ExportErrorInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * 数据导出错误对话框。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
@DependsOn("viewConfiguration")
public class DataExportErrorDialog extends JDialog {

    private static final long serialVersionUID = 4356639123805200140L;

    private final MappingTableModel<ExportErrorInfo> exportErrorInfoTableModel;

    private final JTable exportErrorInfoTable = new JTable();

    public DataExportErrorDialog(
            @Qualifier("exportErrorInfoTableModel") MappingTableModel<ExportErrorInfo> exportErrorInfoTableModel
    ) {
        this.exportErrorInfoTableModel = exportErrorInfoTableModel;
    }

    @SuppressWarnings("DuplicatedCode")
    @PostConstruct
    public void postConstruct() {
        // 初始化窗口。
        setTitle("错误查看");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 450);
        setModal(true);

        // 初始化 contentPanel。
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPanel);
        contentPanel.setLayout(new BorderLayout(0, 0));

        // 中央表格。
        exportErrorInfoTable.setModel(exportErrorInfoTableModel);
        exportErrorInfoTable.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {

            private static final long serialVersionUID = 2617996406254727139L;

            @Override
            public java.awt.Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column
            ) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column
                );
                label.setText(((int) value + 1) + "");
                return label;
            }
        });
        JScrollPane exportErrorInfoScrollPane = new JScrollPane();
        exportErrorInfoScrollPane.setViewportView(exportErrorInfoTable);
        add(exportErrorInfoScrollPane, BorderLayout.CENTER);
    }
}
