package com.jneagle.xlstool.dpxhtj.gui;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

/**
 * 数据查看面板。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
@DependsOn("viewConfiguration")
public class DataInspectPanel extends JPanel {

    private static final long serialVersionUID = -7745789640635646017L;

    private final JTable consumingDetailTable = new JTable();

    public DataInspectPanel() {
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
        GridBagConstraints gbcConsumingDetailTable = new GridBagConstraints();
        gbcConsumingDetailTable.insets = new Insets(0, 0, 0, 0);
        gbcConsumingDetailTable.anchor = GridBagConstraints.CENTER;
        gbcConsumingDetailTable.fill = GridBagConstraints.BOTH;
        gbcConsumingDetailTable.gridx = 0;
        gbcConsumingDetailTable.gridy = 0;
        add(consumingDetailTableScrollPane, gbcConsumingDetailTable);
    }
}
