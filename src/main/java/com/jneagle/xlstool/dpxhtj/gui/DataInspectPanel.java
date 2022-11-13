package com.jneagle.xlstool.dpxhtj.gui;

import com.dwarfeng.dutil.basic.gui.swing.MappingTableModel;
import com.jneagle.xlstool.dpxhtj.bean.entity.ConsumingDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Objects;

/**
 * 数据查看面板。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
@DependsOn("viewConfiguration")
public class DataInspectPanel extends JPanel {

    private static final long serialVersionUID = 2616571753913963447L;

    private final JTable consumingDetailTable = new JTable();

    private final MappingTableModel<ConsumingDetail> consumingDetailTableModel;

    public DataInspectPanel(
            @Qualifier("consumingDetailTableModel") MappingTableModel<ConsumingDetail> consumingDetailTableModel
    ) {
        this.consumingDetailTableModel = consumingDetailTableModel;
    }

    @SuppressWarnings("DuplicatedCode")
    @PostConstruct
    public void postConstruct() {
        // 初始化界面。
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0};
        gridBagLayout.rowHeights = new int[]{0};
        gridBagLayout.columnWeights = new double[]{1.0};
        gridBagLayout.rowWeights = new double[]{1.0};
        setLayout(gridBagLayout);

        // 领用详细信息表-滚动条。
        JScrollPane consumingDetailTableScrollPane = new JScrollPane();
        consumingDetailTableScrollPane.setViewportView(consumingDetailTable);

        // 领用详细信息表。
        consumingDetailTable.setModel(consumingDetailTableModel);
        Enumeration<TableColumn> columns = consumingDetailTable.getColumnModel().getColumns();
        while (columns.hasMoreElements()) {
            TableColumn tableColumn = columns.nextElement();
            tableColumn.setCellRenderer(new DefaultTableCellRenderer() {

                private static final long serialVersionUID = -6703493216611898047L;
                private static final String DATE_FORMAT = "yyyy-MM-dd";

                @Override
                public java.awt.Component getTableCellRendererComponent(
                        JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column
                ) {
                    JLabel label = (JLabel) super.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column
                    );
                    if (Objects.isNull(value)) {
                        label.setText("（空）");
                    } else if (value instanceof Date) {
                        label.setText(new SimpleDateFormat(DATE_FORMAT).format(value));
                    }
                    return label;
                }
            });
        }
        GridBagConstraints gbcConsumingDetailTable = new GridBagConstraints();
        gbcConsumingDetailTable.insets = new Insets(0, 0, 0, 0);
        gbcConsumingDetailTable.anchor = GridBagConstraints.CENTER;
        gbcConsumingDetailTable.fill = GridBagConstraints.BOTH;
        gbcConsumingDetailTable.gridx = 0;
        gbcConsumingDetailTable.gridy = 0;
        add(consumingDetailTableScrollPane, gbcConsumingDetailTable);
    }
}
