package com.jneagle.xlstool.dpxhtj.gui;

import com.dwarfeng.dutil.basic.cna.model.ReferenceModel;
import com.dwarfeng.dutil.basic.cna.model.obs.ReferenceAdapter;
import com.dwarfeng.dutil.basic.gui.swing.SwingUtil;
import com.jneagle.xlstool.dpxhtj.structure.ProgressStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.swing.*;

/**
 * 任务进度条。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
@DependsOn("viewConfiguration")
public class TaskProgressBar extends JProgressBar {

    private static final long serialVersionUID = -7609870684756441094L;

    private final ReferenceModel<ProgressStatus> progressStatusModel;

    private final ProgressStatusObserver observer = new ProgressStatusObserver();

    public TaskProgressBar(
            @Qualifier("progressStatusModel") ReferenceModel<ProgressStatus> progressStatusModel
    ) {
        this.progressStatusModel = progressStatusModel;
    }

    @PostConstruct
    public void postConstruct() {
        // 添加侦听器。
        progressStatusModel.addObserver(observer);

        // 同步属性。
        syncProperties();
    }

    private void syncProperties() {
        SwingUtil.invokeInEventQueue(() -> {
            ProgressStatus progressStatus = progressStatusModel.get();
            int current = progressStatus.getCurrent();
            int total = progressStatus.getTotal();
            setIndeterminate(current < 0);
            setValue(current);
            setMaximum(total);
        });
    }

    @PreDestroy
    public void preDestroy() {
        // 移除侦听器。
        progressStatusModel.removeObserver(observer);
    }

    private class ProgressStatusObserver extends ReferenceAdapter<ProgressStatus> {

        @Override
        public void fireSet(ProgressStatus oldValue, ProgressStatus newValue) {
            SwingUtil.invokeInEventQueue(() -> {
                int current = newValue.getCurrent();
                int total = newValue.getTotal();
                setIndeterminate(current < 0);
                setMaximum(total);
                setValue(current);
            });
        }
    }
}
