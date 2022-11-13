package com.jneagle.xlstool.dpxhtj.gui;

import com.dwarfeng.dutil.basic.cna.model.ReferenceModel;
import com.dwarfeng.dutil.basic.cna.model.obs.ReferenceAdapter;
import com.dwarfeng.dutil.basic.gui.swing.MappingTableModel;
import com.dwarfeng.dutil.basic.gui.swing.SwingUtil;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.jneagle.xlstool.dpxhtj.bean.dto.StatisticResult;
import com.jneagle.xlstool.dpxhtj.bean.entity.ConsumingDetail;
import com.jneagle.xlstool.dpxhtj.bean.entity.ExportErrorInfo;
import com.jneagle.xlstool.dpxhtj.handler.ModalHandler;
import com.jneagle.xlstool.dpxhtj.handler.NotificationHandler;
import com.jneagle.xlstool.dpxhtj.service.DataExportService;
import com.jneagle.xlstool.dpxhtj.service.ExportErrorInfoMaintainService;
import com.jneagle.xlstool.dpxhtj.service.StatisticService;
import com.jneagle.xlstool.dpxhtj.structure.ModalItem;
import com.jneagle.xlstool.dpxhtj.structure.ProgressObserver;
import com.jneagle.xlstool.dpxhtj.structure.ProgressStatus;
import com.jneagle.xlstool.dpxhtj.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Objects;
import java.util.Optional;

/**
 * 数据导出面板。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
@DependsOn("viewConfiguration")
public class DataExportPanel extends JPanel {

    private static final long serialVersionUID = 6210342693252875912L;
    private static final Logger LOGGER = LoggerFactory.getLogger(DataExportPanel.class);

    private final StatisticService statisticService;
    private final DataExportService dataExportService;
    private final ExportErrorInfoMaintainService exportErrorInfoMaintainService;

    private final NotificationHandler notificationHandler;
    private final ModalHandler modalHandler;

    private final ThreadPoolTaskExecutor executor;

    private final ReferenceModel<File> exportFileModel;
    private final ReferenceModel<String> exportPasswordModel;
    private final ReferenceModel<Integer> exportFileTypeModel;
    private final ReferenceModel<String> notificationModel;
    private final ReferenceModel<ProgressStatus> progressStatusModel;
    private final MappingTableModel<ConsumingDetail> consumingDetailTableModel;
    private final MappingTableModel<ExportErrorInfo> exportErrorInfoTableModel;

    private final DataExportErrorDialog dataExportErrorDialog;

    private final JTextField exportFileTextField = new JTextField();
    private final JButton selectFileButton = new JButton();
    private final JPasswordField passwordField = new JPasswordField();
    private final JButton statisticsAndExportFileButton = new JButton();
    private final JTextField resultTextField = new JTextField();
    private final JButton showErrorButton = new JButton();

    private final ExportFileObserver exportFileObserver = new ExportFileObserver();
    private final SelectFileAction selectFileAction = new SelectFileAction();
    private final ExportPasswordObserver exportPasswordObserver = new ExportPasswordObserver();
    private final ExportPasswordDocumentListener exportPasswordDocumentListener = new ExportPasswordDocumentListener();
    private final StatisticAndExportFileAction statisticAndExportFileAction = new StatisticAndExportFileAction();
    private final DataExportProgressObserver dataExportProgressObserver = new DataExportProgressObserver();
    private final DataExportTableModelListener dataExportTableModelListener = new DataExportTableModelListener();
    private final ShowErrorDialogAction showErrorDialogAction = new ShowErrorDialogAction();

    public DataExportPanel(
            StatisticService statisticService,
            DataExportService dataExportService,
            ExportErrorInfoMaintainService exportErrorInfoMaintainService,
            NotificationHandler notificationHandler,
            ModalHandler modalHandler,
            ThreadPoolTaskExecutor executor,
            @Qualifier("exportFileModel") ReferenceModel<File> exportFileModel,
            @Qualifier("exportPasswordModel") ReferenceModel<String> exportPasswordModel,
            @Qualifier("exportFileTypeModel") ReferenceModel<Integer> exportFileTypeModel,
            @Qualifier("notificationModel") ReferenceModel<String> notificationModel,
            @Qualifier("progressStatusModel") ReferenceModel<ProgressStatus> progressStatusModel,
            @Qualifier("consumingDetailTableModel") MappingTableModel<ConsumingDetail> consumingDetailTableModel,
            @Qualifier("exportErrorInfoTableModel") MappingTableModel<ExportErrorInfo> exportErrorInfoTableModel,
            DataExportErrorDialog dataExportErrorDialog
    ) {
        this.statisticService = statisticService;
        this.dataExportService = dataExportService;
        this.exportErrorInfoMaintainService = exportErrorInfoMaintainService;
        this.notificationHandler = notificationHandler;
        this.modalHandler = modalHandler;
        this.executor = executor;
        this.exportFileModel = exportFileModel;
        this.exportPasswordModel = exportPasswordModel;
        this.exportFileTypeModel = exportFileTypeModel;
        this.notificationModel = notificationModel;
        this.progressStatusModel = progressStatusModel;
        this.consumingDetailTableModel = consumingDetailTableModel;
        this.exportErrorInfoTableModel = exportErrorInfoTableModel;
        this.dataExportErrorDialog = dataExportErrorDialog;
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
        JLabel importFileLabel = new JLabel("导出文件");
        GridBagConstraints gbcImportFileLabel = new GridBagConstraints();
        gbcImportFileLabel.insets = new Insets(0, 0, 0, 5);
        gbcImportFileLabel.anchor = GridBagConstraints.EAST;
        gbcImportFileLabel.gridx = 0;
        gbcImportFileLabel.gridy = 0;
        add(importFileLabel, gbcImportFileLabel);

        // 文件路径文本框。
        exportFileTextField.setEditable(false);
        GridBagConstraints gbcImportFileTextField = new GridBagConstraints();
        gbcImportFileTextField.insets = new Insets(0, 0, 0, 5);
        gbcImportFileTextField.fill = GridBagConstraints.BOTH;
        gbcImportFileTextField.gridx = 1;
        gbcImportFileTextField.gridy = 0;
        add(exportFileTextField, gbcImportFileTextField);

        // 选择文件按钮。
        selectFileButton.setAction(selectFileAction);
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
        passwordField.getDocument().addDocumentListener(exportPasswordDocumentListener);
        GridBagConstraints gbcPasswordField = new GridBagConstraints();
        gbcPasswordField.insets = new Insets(0, 0, 0, 5);
        gbcPasswordField.fill = GridBagConstraints.BOTH;
        gbcPasswordField.gridx = 1;
        gbcPasswordField.gridy = 1;
        add(passwordField, gbcPasswordField);

        // 统计并导出文件按钮。
        statisticsAndExportFileButton.setAction(statisticAndExportFileAction);
        statisticsAndExportFileButton.setText("统计并导出");
        GridBagConstraints gbcImportFileButton = new GridBagConstraints();
        gbcImportFileButton.insets = new Insets(0, 0, 0, 0);
        gbcImportFileButton.fill = GridBagConstraints.BOTH;
        gbcImportFileButton.gridx = 2;
        gbcImportFileButton.gridy = 1;
        add(statisticsAndExportFileButton, gbcImportFileButton);

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
        showErrorButton.setAction(showErrorDialogAction);
        showErrorButton.setText("显示错误...");
        GridBagConstraints gbcShowErrorButton = new GridBagConstraints();
        gbcShowErrorButton.insets = new Insets(0, 0, 0, 0);
        gbcShowErrorButton.fill = GridBagConstraints.BOTH;
        gbcShowErrorButton.gridx = 2;
        gbcShowErrorButton.gridy = 2;
        add(showErrorButton, gbcShowErrorButton);

        // 添加侦听器。
        exportFileModel.addObserver(exportFileObserver);
        exportPasswordModel.addObserver(exportPasswordObserver);
        consumingDetailTableModel.addTableModelListener(dataExportTableModelListener);
        exportErrorInfoTableModel.addTableModelListener(dataExportTableModelListener);

        // 同步属性。
        syncProperties();
    }

    private void syncProperties() {
        SwingUtil.invokeInEventQueue(() -> {
            File file = exportFileModel.get();
            exportFileTextField.setText(
                    Optional.ofNullable(file).map(File::getAbsolutePath).orElse("")
            );
            if (Objects.isNull(file)) {
                statisticsAndExportFileButton.setEnabled(false);
            } else {
                statisticsAndExportFileButton.setEnabled(file.exists());
            }
            resultTextField.setText(String.format("%d 失败", exportErrorInfoTableModel.size()));
            showErrorButton.setEnabled(!exportErrorInfoTableModel.isEmpty());
        });
    }

    @PreDestroy
    public void preDestroy() {
        // 移除侦听器。
        exportFileModel.removeObserver(exportFileObserver);
        exportPasswordModel.removeObserver(exportPasswordObserver);
        consumingDetailTableModel.removeTableModelListener(dataExportTableModelListener);
        exportErrorInfoTableModel.removeTableModelListener(dataExportTableModelListener);
    }

    private class ExportFileObserver extends ReferenceAdapter<File> {

        @Override
        public void fireSet(File oldValue, File newValue) {
            SwingUtil.invokeInEventQueue(() -> {
                exportFileTextField.setText(
                        Optional.ofNullable(newValue).map(File::getAbsolutePath).orElse("")
                );
                if (Objects.isNull(newValue)) {
                    statisticsAndExportFileButton.setEnabled(false);
                } else {
                    statisticsAndExportFileButton.setEnabled(!consumingDetailTableModel.isEmpty());
                }
            });
        }
    }

    private class SelectFileAction extends AbstractAction {

        private static final long serialVersionUID = -2871657759087340579L;

        @SuppressWarnings("DuplicatedCode")
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // 定义文件筛选器。
                FileNameExtensionFilter xlsFileFilter = new FileNameExtensionFilter("Excel 97-03 文件(xls)", "xls");
                FileNameExtensionFilter xlsxFileFilter = new FileNameExtensionFilter("Excel 文件(xlsx)", "xlsx");

                // 创建文件选择器并进行设置。
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.setFileFilter(xlsFileFilter);
                jfc.addChoosableFileFilter(xlsxFileFilter);
                jfc.setAcceptAllFileFilterUsed(false);
                // 如果已经有文件了，将当前路径设置为指定文件的路径。
                File currentFile = exportFileModel.get();
                if (Objects.nonNull(currentFile)) {
                    if (currentFile.isDirectory()) {
                        jfc.setCurrentDirectory(currentFile);
                    } else {
                        jfc.setCurrentDirectory(currentFile.getParentFile());
                    }
                }

                // 显示界面，让用户选择文件。
                int result = jfc.showOpenDialog(DataExportPanel.this);
                // 如果用户按下了确定键，则将文件模型设置为用户选择的文件。
                if (Objects.equals(result, JFileChooser.APPROVE_OPTION)) {
                    passwordField.setText("");
                    File file = jfc.getSelectedFile();
                    int type;

                    String absolutePath = file.getAbsolutePath();
                    FileFilter selectedFileFilter = jfc.getFileFilter();

                    // 确定文件的类型，并自动适配文件名。
                    if (selectedFileFilter == xlsFileFilter) {
                        type = Constants.EXPORT_FILE_TYPE_XLS;
                        if (!absolutePath.endsWith(".xls")) {
                            file = new File(absolutePath + ".xls");
                        }
                    } else if ((selectedFileFilter == xlsxFileFilter)) {
                        type = Constants.EXPORT_FILE_TYPE_XLSX;
                        if (!absolutePath.endsWith(".xlsx")) {
                            file = new File(absolutePath + ".xlsx");
                        }
                    } else {
                        throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
                    }

                    LOGGER.info(type + "");
                    LOGGER.info(file.getAbsolutePath());

                    exportFileModel.set(file);
                    exportFileTypeModel.set(type);
                }
            } catch (Exception ex) {
                LOGGER.warn("选择文件时发生异常，异常信息如下: ", ex);
                notificationHandler.error(SwingUtilities.getRoot(DataExportPanel.this), "选择文件时发出异常，详见日志");
            }
        }
    }

    private class ExportPasswordObserver extends ReferenceAdapter<String> {

        private boolean enabled = true;

        @Override
        public void fireSet(String oldValue, String newValue) {
            if (!enabled) {
                return;
            }
            SwingUtil.invokeInEventQueue(() -> passwordField.setText(newValue));
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    private class ExportPasswordDocumentListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            changePassword();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            changePassword();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            // Do nothing
        }

        private void changePassword() {
            exportPasswordObserver.setEnabled(false);
            exportPasswordModel.set(new String(passwordField.getPassword()));
            exportPasswordObserver.setEnabled(true);
        }
    }

    private class StatisticAndExportFileAction extends AbstractAction implements Runnable {

        private static final long serialVersionUID = -8624960426477159846L;

        @Override
        public void actionPerformed(ActionEvent e) {
            // 在后台线程中执行方法，避免前端界面卡顿。
            executor.execute(this);
        }

        @Override
        public void run() {
            try {
                // 通过控制按钮的 enabled 属性，此处可以保证按钮按下时文件是有效的。
                File file = exportFileModel.get();
                String password = exportPasswordModel.get();
                Integer fileType = exportFileTypeModel.get();

                // 保存文件读取的模态信息。
                modalHandler.setValue(ModalItem.MODAL_DATA_EXPORT_LAST_EXPORT_FILE, file);
                modalHandler.setValue(ModalItem.MODAL_DATA_EXPORT_LAST_EXPORT_FLAG, true);
                modalHandler.setValue(ModalItem.MODAL_DATA_EXPORT_LAST_EXPORT_FILE_TYPE, fileType);

                // 更新通知面板标签文本。
                notificationModel.set("统计与导出数据...");

                // 定义统计结果。
                StatisticResult statisticResult;

                // 创建侦听
                statisticService.addObserver(dataExportProgressObserver);
                // 调用接口执行统计动作，并捕获异常。
                try {
                    statisticResult = statisticService.execStatistic();
                } catch (ServiceException ex) {
                    // 更新通知面板标签文本。
                    notificationHandler.warn(
                            SwingUtilities.getRoot(DataExportPanel.this), "程序内部错误，请联系开发人员"
                    );
                    notificationModel.set("数据统计失败");
                    return;
                } finally {
                    // 移除侦听。
                    statisticService.removeObserver(dataExportProgressObserver);
                }

                // 创建侦听
                dataExportService.addObserver(dataExportProgressObserver);
                // 调用接口导出统计结果到文件，并捕获异常。
                try {
                    dataExportService.execExport(statisticResult, file, fileType, password);
                } catch (ServiceException ex) {
                    // 更新通知面板标签文本。
                    notificationHandler.warn(
                            SwingUtilities.getRoot(DataExportPanel.this), "程序内部错误，请联系开发人员"
                    );
                    notificationModel.set("数据导出失败");
                    return;
                } finally {
                    // 移除侦听。
                    dataExportService.removeObserver(dataExportProgressObserver);
                }

                // 重新加载结构化数据。
                exportErrorInfoTableModel.clear();
                exportErrorInfoTableModel.addAll(exportErrorInfoMaintainService.lookupAsList());

                // 判断是否存在数据错误，如果有错误，则需要自动弹出错误对话框。
                if (exportErrorInfoTableModel.isEmpty()) {
                    notificationModel.set("统计结果成功");
                } else {
                    notificationModel.set("统计结果导出完成，但存在错误");
                }
            } catch (Exception ex) {
                LOGGER.warn("加载文件时发生异常，异常信息如下: ", ex);
                notificationHandler.error(SwingUtilities.getRoot(DataExportPanel.this), "ui.label.011");
            }
        }
    }

    private class DataExportProgressObserver implements ProgressObserver {

        @Override
        public void onProgressChanged(ProgressStatus progressStatus) {
            progressStatusModel.set(progressStatus);
        }
    }

    private class DataExportTableModelListener implements TableModelListener {

        @Override
        public void tableChanged(TableModelEvent e) {
            SwingUtil.invokeInEventQueue(() -> {
                if (Objects.nonNull(exportFileModel.get())) {
                    statisticsAndExportFileButton.setEnabled(!consumingDetailTableModel.isEmpty());
                }
                resultTextField.setText(String.format("%d 失败", exportErrorInfoTableModel.size()));
                showErrorButton.setEnabled(!exportErrorInfoTableModel.isEmpty());
            });
        }
    }

    private class ShowErrorDialogAction extends AbstractAction {

        private static final long serialVersionUID = 4664682158321026566L;

        @Override
        public void actionPerformed(ActionEvent e) {
            dataExportErrorDialog.setLocationRelativeTo(SwingUtilities.getRoot(DataExportPanel.this));
            dataExportErrorDialog.setVisible(true);
        }
    }
}
