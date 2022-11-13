package com.jneagle.xlstool.dpxhtj.gui;

import com.dwarfeng.dutil.basic.cna.model.ReferenceModel;
import com.dwarfeng.dutil.basic.cna.model.obs.ReferenceAdapter;
import com.dwarfeng.dutil.basic.gui.swing.MappingTableModel;
import com.dwarfeng.dutil.basic.gui.swing.SwingUtil;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.jneagle.xlstool.dpxhtj.bean.entity.ConsumingDetail;
import com.jneagle.xlstool.dpxhtj.bean.entity.ImportErrorInfo;
import com.jneagle.xlstool.dpxhtj.handler.ModalHandler;
import com.jneagle.xlstool.dpxhtj.handler.NotificationHandler;
import com.jneagle.xlstool.dpxhtj.service.ConsumingDetailMaintainService;
import com.jneagle.xlstool.dpxhtj.service.DataImportService;
import com.jneagle.xlstool.dpxhtj.service.ImportErrorInfoMaintainService;
import com.jneagle.xlstool.dpxhtj.structure.ModalItem;
import com.jneagle.xlstool.dpxhtj.structure.ProgressObserver;
import com.jneagle.xlstool.dpxhtj.structure.ProgressStatus;
import com.jneagle.xlstool.dpxhtj.util.ServiceExceptionCodes;
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
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Objects;
import java.util.Optional;

/**
 * 数据导入面板。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
@DependsOn("viewConfiguration")
public class DataImportPanel extends JPanel {

    private static final long serialVersionUID = -1521715549303446921L;
    private static final Logger LOGGER = LoggerFactory.getLogger(DataImportPanel.class);

    private final DataImportService dataImportService;
    private final ConsumingDetailMaintainService consumingDetailMaintainService;
    private final ImportErrorInfoMaintainService importErrorInfoMaintainService;

    private final NotificationHandler notificationHandler;
    private final ModalHandler modalHandler;

    private final ThreadPoolTaskExecutor executor;

    private final ReferenceModel<File> importFileModel;
    private final ReferenceModel<String> importPasswordModel;
    private final ReferenceModel<String> notificationModel;
    private final ReferenceModel<ProgressStatus> progressStatusModel;
    private final MappingTableModel<ConsumingDetail> consumingDetailTableModel;
    private final MappingTableModel<ImportErrorInfo> importErrorInfoTableModel;

    private final DataImportErrorDialog dataImportErrorDialog;

    private final JTextField importFileTextField = new JTextField();
    private final JButton selectFileButton = new JButton();
    private final JPasswordField passwordField = new JPasswordField();
    private final JButton importFileButton = new JButton();
    private final JTextField resultTextField = new JTextField();
    private final JButton showErrorButton = new JButton();

    private final ImportFileObserver importFileObserver = new ImportFileObserver();
    private final SelectFileAction selectFileAction = new SelectFileAction();
    private final ImportPasswordObserver importPasswordObserver = new ImportPasswordObserver();
    private final ImportPasswordDocumentListener importPasswordDocumentListener = new ImportPasswordDocumentListener();
    private final ImportFileAction importFileAction = new ImportFileAction();
    private final DataImportProgressObserver dataImportProgressObserver = new DataImportProgressObserver();
    private final DataImportTableModelListener dataImportTableModelListener = new DataImportTableModelListener();
    private final ShowErrorDialogAction showErrorDialogAction = new ShowErrorDialogAction();

    public DataImportPanel(
            DataImportService dataImportService,
            ConsumingDetailMaintainService consumingDetailMaintainService,
            ImportErrorInfoMaintainService importErrorInfoMaintainService,
            NotificationHandler notificationHandler,
            ModalHandler modalHandler,
            ThreadPoolTaskExecutor executor,
            @Qualifier("importFileModel") ReferenceModel<File> importFileModel,
            @Qualifier("importPasswordModel") ReferenceModel<String> importPasswordModel,
            @Qualifier("notificationModel") ReferenceModel<String> notificationModel,
            @Qualifier("progressStatusModel") ReferenceModel<ProgressStatus> progressStatusModel,
            @Qualifier("consumingDetailTableModel") MappingTableModel<ConsumingDetail> consumingDetailTableModel,
            @Qualifier("importErrorInfoTableModel") MappingTableModel<ImportErrorInfo> importErrorInfoTableModel,
            DataImportErrorDialog dataImportErrorDialog
    ) {
        this.dataImportService = dataImportService;
        this.consumingDetailMaintainService = consumingDetailMaintainService;
        this.importErrorInfoMaintainService = importErrorInfoMaintainService;
        this.notificationHandler = notificationHandler;
        this.modalHandler = modalHandler;
        this.executor = executor;
        this.importFileModel = importFileModel;
        this.importPasswordModel = importPasswordModel;
        this.notificationModel = notificationModel;
        this.progressStatusModel = progressStatusModel;
        this.consumingDetailTableModel = consumingDetailTableModel;
        this.importErrorInfoTableModel = importErrorInfoTableModel;
        this.dataImportErrorDialog = dataImportErrorDialog;
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

        // 导入文件标签。
        JLabel importFileLabel = new JLabel("导入文件");
        GridBagConstraints gbcImportFileLabel = new GridBagConstraints();
        gbcImportFileLabel.insets = new Insets(0, 0, 0, 5);
        gbcImportFileLabel.anchor = GridBagConstraints.EAST;
        gbcImportFileLabel.gridx = 0;
        gbcImportFileLabel.gridy = 0;
        add(importFileLabel, gbcImportFileLabel);

        // 文件路径文本框。
        importFileTextField.setEditable(false);
        GridBagConstraints gbcImportFileTextField = new GridBagConstraints();
        gbcImportFileTextField.insets = new Insets(0, 0, 0, 5);
        gbcImportFileTextField.fill = GridBagConstraints.BOTH;
        gbcImportFileTextField.gridx = 1;
        gbcImportFileTextField.gridy = 0;
        add(importFileTextField, gbcImportFileTextField);

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
        passwordField.getDocument().addDocumentListener(importPasswordDocumentListener);
        GridBagConstraints gbcPasswordField = new GridBagConstraints();
        gbcPasswordField.insets = new Insets(0, 0, 0, 5);
        gbcPasswordField.fill = GridBagConstraints.BOTH;
        gbcPasswordField.gridx = 1;
        gbcPasswordField.gridy = 1;
        add(passwordField, gbcPasswordField);

        // 读取文件按钮。
        importFileButton.setAction(importFileAction);
        importFileButton.setText("导入");
        GridBagConstraints gbcImportFileButton = new GridBagConstraints();
        gbcImportFileButton.insets = new Insets(0, 0, 0, 0);
        gbcImportFileButton.fill = GridBagConstraints.BOTH;
        gbcImportFileButton.gridx = 2;
        gbcImportFileButton.gridy = 1;
        add(importFileButton, gbcImportFileButton);

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
        importFileModel.addObserver(importFileObserver);
        importPasswordModel.addObserver(importPasswordObserver);
        consumingDetailTableModel.addTableModelListener(dataImportTableModelListener);
        importErrorInfoTableModel.addTableModelListener(dataImportTableModelListener);

        // 同步属性。
        syncProperties();
    }

    private void syncProperties() {
        SwingUtil.invokeInEventQueue(() -> {
            File file = importFileModel.get();
            importFileTextField.setText(
                    Optional.ofNullable(file).map(File::getAbsolutePath).orElse("")
            );
            if (Objects.isNull(file)) {
                importFileButton.setEnabled(false);
            } else {
                importFileButton.setEnabled(file.exists());
            }
            resultTextField.setText(String.format(
                    "%d 成功, %d 失败", consumingDetailTableModel.size(), importErrorInfoTableModel.size()
            ));
            showErrorButton.setEnabled(!importErrorInfoTableModel.isEmpty());
        });
    }

    @PreDestroy
    public void preDestroy() {
        // 移除侦听器。
        importFileModel.removeObserver(importFileObserver);
        importPasswordModel.removeObserver(importPasswordObserver);
        consumingDetailTableModel.removeTableModelListener(dataImportTableModelListener);
        importErrorInfoTableModel.removeTableModelListener(dataImportTableModelListener);
    }

    private class ImportFileObserver extends ReferenceAdapter<File> {

        @Override
        public void fireSet(File oldValue, File newValue) {
            SwingUtil.invokeInEventQueue(() -> {
                importFileTextField.setText(
                        Optional.ofNullable(newValue).map(File::getAbsolutePath).orElse("")
                );
                if (Objects.isNull(newValue)) {
                    importFileButton.setEnabled(false);
                } else {
                    importFileButton.setEnabled(newValue.exists());
                }
            });
        }
    }

    private class SelectFileAction extends AbstractAction {

        private static final long serialVersionUID = 8339367057593982411L;

        @SuppressWarnings("DuplicatedCode")
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // 创建文件选择器并进行设置。
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.setFileFilter(new FileNameExtensionFilter("Excel 97-03 文件(xls)", "xls"));
                jfc.addChoosableFileFilter(new FileNameExtensionFilter("Excel 文件(xlsx)", "xlsx"));
                jfc.setAcceptAllFileFilterUsed(false);
                // 如果已经有文件了，将当前路径设置为指定文件的路径。
                File currentFile = importFileModel.get();
                if (Objects.nonNull(currentFile)) {
                    if (currentFile.isDirectory()) {
                        jfc.setCurrentDirectory(currentFile);
                    } else {
                        jfc.setCurrentDirectory(currentFile.getParentFile());
                    }
                }
                // 显示界面，让用户选择文件。
                int result = jfc.showOpenDialog(DataImportPanel.this);
                // 如果用户按下了确定键，则将文件模型设置为用户选择的文件。
                if (Objects.equals(result, JFileChooser.APPROVE_OPTION)) {
                    passwordField.setText("");
                    File file = jfc.getSelectedFile();
                    importFileModel.set(file);
                }
            } catch (Exception ex) {
                LOGGER.warn("选择文件时发生异常，异常信息如下: ", ex);
                notificationHandler.error(SwingUtilities.getRoot(DataImportPanel.this), "选择文件时发出异常，详见日志");
            }
        }
    }

    private class ImportPasswordObserver extends ReferenceAdapter<String> {

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

    private class ImportPasswordDocumentListener implements DocumentListener {

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
            importPasswordObserver.setEnabled(false);
            importPasswordModel.set(new String(passwordField.getPassword()));
            importPasswordObserver.setEnabled(true);
        }
    }

    private class ImportFileAction extends AbstractAction implements Runnable {

        private static final long serialVersionUID = -3481669410577676179L;

        @Override
        public void actionPerformed(ActionEvent e) {
            // 在后台线程中执行方法，避免前端界面卡顿。
            executor.execute(this);
        }

        @Override
        public void run() {
            try {
                // 通过控制按钮的 enabled 属性，此处可以保证按钮按下时文件是有效的。
                File file = importFileModel.get();
                String password = importPasswordModel.get();

                // 保存文件读取的模态信息。
                modalHandler.setValue(ModalItem.MODAL_DATA_IMPORT_LAST_IMPORT_FILE, file);
                modalHandler.setValue(ModalItem.MODAL_DATA_IMPORT_LAST_IMPORT_FLAG, true);

                // 更新通知面板标签文本。
                notificationModel.set("导入数据...");

                // 创建侦听
                dataImportService.addObserver(dataImportProgressObserver);
                // 调用接口读取文件，并捕获异常。
                try {
                    dataImportService.execImport(file, password);
                } catch (ServiceException ex) {
                    // 分析异常。
                    int code = ex.getCode().getCode();
                    if (Objects.equals(code, ServiceExceptionCodes.WRONG_PASSWORD.getCode())) {
                        notificationHandler.warn(SwingUtilities.getRoot(DataImportPanel.this), "密码错误");
                    } else {
                        notificationHandler.warn(
                                SwingUtilities.getRoot(DataImportPanel.this), "程序内部错误，请联系开发人员"
                        );
                    }
                    // 更新通知面板标签文本。
                    notificationModel.set("数据导入失败");
                    return;
                } finally {
                    // 移除侦听。
                    dataImportService.removeObserver(dataImportProgressObserver);
                }

                // 重新加载结构化数据。
                consumingDetailTableModel.clear();
                importErrorInfoTableModel.clear();
                consumingDetailTableModel.addAll(consumingDetailMaintainService.lookupAsList());
                importErrorInfoTableModel.addAll(importErrorInfoMaintainService.lookupAsList());

                // 判断是否存在数据错误，如果有错误，则需要自动弹出错误对话框。
                if (importErrorInfoTableModel.isEmpty()) {
                    notificationModel.set("信息导入成功");
                } else {
                    notificationModel.set("信息导入完成，但存在错误");
                }
            } catch (Exception ex) {
                LOGGER.warn("导入文件时发生异常，异常信息如下: ", ex);
                notificationHandler.error(SwingUtilities.getRoot(DataImportPanel.this), "导入文件时发出异常，详见日志");
            }
        }
    }

    private class DataImportProgressObserver implements ProgressObserver {

        @Override
        public void onProgressChanged(ProgressStatus progressStatus) {
            progressStatusModel.set(progressStatus);
        }
    }

    private class DataImportTableModelListener implements TableModelListener {

        @Override
        public void tableChanged(TableModelEvent e) {
            SwingUtil.invokeInEventQueue(() -> {
                resultTextField.setText(String.format(
                        "%d 成功, %d 失败", consumingDetailTableModel.size(), importErrorInfoTableModel.size()
                ));
                showErrorButton.setEnabled(!importErrorInfoTableModel.isEmpty());
            });
        }
    }

    private class ShowErrorDialogAction extends AbstractAction {

        private static final long serialVersionUID = -3853442954699420875L;

        @Override
        public void actionPerformed(ActionEvent e) {
            dataImportErrorDialog.setLocationRelativeTo(SwingUtilities.getRoot(DataImportPanel.this));
            dataImportErrorDialog.setVisible(true);
        }
    }
}
