package com.jneagle.xlstool.dpxhtj.gui;

import com.dwarfeng.dutil.basic.gui.swing.MappingTableModel;
import com.jneagle.xlstool.dpxhtj.bean.entity.ImportErrorInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * 数据导入错误对话框。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
@DependsOn("viewConfiguration")
public class DataImportErrorDialog extends JDialog {

    private static final long serialVersionUID = -10898046869658757L;
    
    private final MappingTableModel<ImportErrorInfo> importErrorInfoTableModel;

    private final JTable importErrorInfoTable = new JTable();

    public DataImportErrorDialog(
            @Qualifier("importErrorInfoTableModel") MappingTableModel<ImportErrorInfo> importErrorInfoTableModel
    ) {
        this.importErrorInfoTableModel = importErrorInfoTableModel;
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
        importErrorInfoTable.setModel(importErrorInfoTableModel);
        importErrorInfoTable.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {

            private static final long serialVersionUID = -8913227198742213606L;

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
        JScrollPane importErrorInfoScrollPane = new JScrollPane();
        importErrorInfoScrollPane.setViewportView(importErrorInfoTable);
        add(importErrorInfoScrollPane, BorderLayout.CENTER);
    }
}
