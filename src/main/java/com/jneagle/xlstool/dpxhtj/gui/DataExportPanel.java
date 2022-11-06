package com.jneagle.xlstool.dpxhtj.gui;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

/**
 * 数据导出面板。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
@DependsOn("viewConfiguration")
public class DataExportPanel extends JPanel {

    private static final long serialVersionUID = 2732460917838212901L;

    private final JTextField exportFileTextField = new JTextField();
    private final JButton selectFileButton = new JButton();
    private final JPasswordField passwordField = new JPasswordField();
    private final JButton statisticsAndSaveFileButton = new JButton();
    private final JTextField resultTextField = new JTextField();
    private final JButton showErrorButton = new JButton();

    public DataExportPanel() {
    }

    @SuppressWarnings("DuplicatedCode")
    @PostConstruct
    public void postConstruct() {
        // 初始化界面。
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0};
        setLayout(gridBagLayout);

        // 导出文件标签。
        JLabel loadFileLabel = new JLabel("导出文件");
        GridBagConstraints gbcLoadFileLabel = new GridBagConstraints();
        gbcLoadFileLabel.insets = new Insets(0, 0, 0, 5);
        gbcLoadFileLabel.anchor = GridBagConstraints.EAST;
        gbcLoadFileLabel.gridx = 0;
        gbcLoadFileLabel.gridy = 0;
        add(loadFileLabel, gbcLoadFileLabel);

        // 文件路径文本框。
        exportFileTextField.setEditable(false);
        GridBagConstraints gbcLoadFileTextField = new GridBagConstraints();
        gbcLoadFileTextField.insets = new Insets(0, 0, 0, 5);
        gbcLoadFileTextField.fill = GridBagConstraints.BOTH;
        gbcLoadFileTextField.gridx = 1;
        gbcLoadFileTextField.gridy = 0;
        add(exportFileTextField, gbcLoadFileTextField);

        // 选择文件按钮。
//        selectFileButton.setAction(new SelectFileAction()); TODO
        selectFileButton.setText("选择...");
        GridBagConstraints gbcSelectFileButton = new GridBagConstraints();
        gbcSelectFileButton.insets = new Insets(0, 0, 0, 0);
        gbcSelectFileButton.fill = GridBagConstraints.BOTH;
        gbcSelectFileButton.gridx = 2;
        gbcSelectFileButton.gridy = 0;
        add(selectFileButton, gbcSelectFileButton);

        // 密码标签。
        JLabel passwordLabel = new JLabel("密码");
        GridBagConstraints gbcPasswordLabel = new GridBagConstraints();
        gbcPasswordLabel.insets = new Insets(0, 0, 0, 5);
        gbcPasswordLabel.anchor = GridBagConstraints.EAST;
        gbcPasswordLabel.gridx = 0;
        gbcPasswordLabel.gridy = 1;
        add(passwordLabel, gbcPasswordLabel);

        // 密码文本框。
//        passwordField.getDocument().addDocumentListener(new PasswordDocumentListener()); TODO
        GridBagConstraints gbcPasswordField = new GridBagConstraints();
        gbcPasswordField.insets = new Insets(0, 0, 0, 5);
        gbcPasswordField.fill = GridBagConstraints.BOTH;
        gbcPasswordField.gridx = 1;
        gbcPasswordField.gridy = 1;
        add(passwordField, gbcPasswordField);

        // 统计并导出文件按钮。
//        statisticsAndSaveFileButton.setAction(new LoadFileAction()); TODO
        statisticsAndSaveFileButton.setText("统计并导出");
        GridBagConstraints gbcLoadFileButton = new GridBagConstraints();
        gbcLoadFileButton.insets = new Insets(0, 0, 0, 0);
        gbcLoadFileButton.fill = GridBagConstraints.BOTH;
        gbcLoadFileButton.gridx = 2;
        gbcLoadFileButton.gridy = 1;
        add(statisticsAndSaveFileButton, gbcLoadFileButton);

        // 结果标签。
        JLabel resultLabel = new JLabel("结果");
        GridBagConstraints gbcResultLabel = new GridBagConstraints();
        gbcResultLabel.insets = new Insets(0, 0, 0, 5);
        gbcResultLabel.anchor = GridBagConstraints.EAST;
        gbcResultLabel.gridx = 0;
        gbcResultLabel.gridy = 2;
        add(resultLabel, gbcResultLabel);

        // 结果文本框。
        resultTextField.setEditable(false);
        GridBagConstraints gbcResultTextField = new GridBagConstraints();
        gbcResultTextField.insets = new Insets(0, 0, 0, 5);
        gbcResultTextField.fill = GridBagConstraints.BOTH;
        gbcResultTextField.gridx = 1;
        gbcResultTextField.gridy = 2;
        add(resultTextField, gbcResultTextField);

        // 显示错误按钮。
//        showErrorButton.setAction(new ShowErrorDialogAction()); TODO
        showErrorButton.setText("显示错误...");
        GridBagConstraints gbcShowErrorButton = new GridBagConstraints();
        gbcShowErrorButton.insets = new Insets(0, 0, 0, 0);
        gbcShowErrorButton.fill = GridBagConstraints.BOTH;
        gbcShowErrorButton.gridx = 2;
        gbcShowErrorButton.gridy = 2;
        add(showErrorButton, gbcShowErrorButton);

        // 添加侦听器。 TODO
//        structuredFileModel.addObserver(loadFileObserver);
//        structuredPasswordModel.addObserver(passwordObserver);
//        structuredDataTableModel.addTableModelListener(structuredDataTableModelListener);
//        structuredErrorInfoTableModel.addTableModelListener(structuredErrorInfoTableModelListener);

        // 同步属性。 TODO
//        syncProperties();
    }
}
