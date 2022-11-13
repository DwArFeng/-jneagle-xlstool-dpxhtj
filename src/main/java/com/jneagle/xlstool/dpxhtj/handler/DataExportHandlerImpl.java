package com.jneagle.xlstool.dpxhtj.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.jneagle.xlstool.dpxhtj.bean.dto.StatisticResult;
import com.jneagle.xlstool.dpxhtj.structure.ProgressStatus;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DataExportHandlerImpl extends AbstractProgressHandler implements DataExportHandler {

    @Override
    public void execExport(
            StatisticResult statisticResult, File file, int fileType, String password
    ) throws HandlerException {
        try {
            // 广播进度变更事件。
            fireProgressChanged(ProgressStatus.UNCERTAIN);

            // TODO Auto-generated method stub.
            throw new HandlerException();
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            // 广播进度变更事件。
            fireProgressChanged(ProgressStatus.IDLE);
        }
    }
}
