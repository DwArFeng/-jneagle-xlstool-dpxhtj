package com.jneagle.xlstool.dpxhtj.gui;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

/**
 * Body 位置的 GridBag 布局的面板。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
@DependsOn("viewConfiguration")
public class BodyGridBagPane extends JPanel {

    private static final long serialVersionUID = -2848809397004124852L;

    private final DataImportPanel dataImportPanel;
    private final DataInspectPanel dataInspectPanel;
    private final DataExportPanel dataExportPanel;

    public BodyGridBagPane(
            DataImportPanel dataImportPanel,
            DataInspectPanel dataInspectPanel,
            DataExportPanel dataExportPanel
    ) {
        this.dataImportPanel = dataImportPanel;
        this.dataInspectPanel = dataInspectPanel;
        this.dataExportPanel = dataExportPanel;
    }

    @SuppressWarnings("DuplicatedCode")
    @PostConstruct
    public void postConstruct() {
        // 初始化界面。
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0,};
        gridBagLayout.rowHeights = new int[]{0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0};
        gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0};
        setLayout(gridBagLayout);

        // 数据导入面板。
        GridBagConstraints gbcDataImportPanel = new GridBagConstraints();
        gbcDataImportPanel.insets = new Insets(0, 0, 5, 0);
        gbcDataImportPanel.anchor = GridBagConstraints.CENTER;
        gbcDataImportPanel.fill = GridBagConstraints.BOTH;
        gbcDataImportPanel.gridx = 0;
        gbcDataImportPanel.gridy = 0;
        add(dataImportPanel, gbcDataImportPanel);

        // 数据查看面板。
        GridBagConstraints gbcDataInspectPanel = new GridBagConstraints();
        gbcDataInspectPanel.insets = new Insets(0, 0, 5, 0);
        gbcDataInspectPanel.anchor = GridBagConstraints.CENTER;
        gbcDataInspectPanel.fill = GridBagConstraints.BOTH;
        gbcDataInspectPanel.gridx = 0;
        gbcDataInspectPanel.gridy = 1;
        add(dataInspectPanel, gbcDataInspectPanel);

        // 数据导出面板。
        GridBagConstraints gbcDataExportPanel = new GridBagConstraints();
        gbcDataExportPanel.insets = new Insets(0, 0, 0, 0);
        gbcDataExportPanel.anchor = GridBagConstraints.CENTER;
        gbcDataExportPanel.fill = GridBagConstraints.BOTH;
        gbcDataExportPanel.gridx = 0;
        gbcDataExportPanel.gridy = 2;
        add(dataExportPanel, gbcDataExportPanel);
    }
}
