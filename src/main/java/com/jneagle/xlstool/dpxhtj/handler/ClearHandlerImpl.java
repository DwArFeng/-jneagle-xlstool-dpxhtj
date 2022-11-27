package com.jneagle.xlstool.dpxhtj.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.jneagle.xlstool.dpxhtj.service.ConsumingDetailMaintainService;
import com.jneagle.xlstool.dpxhtj.service.ExportErrorInfoMaintainService;
import com.jneagle.xlstool.dpxhtj.service.ImportErrorInfoMaintainService;
import com.jneagle.xlstool.dpxhtj.structure.ProgressStatus;
import org.springframework.stereotype.Component;

@Component
public class ClearHandlerImpl extends AbstractProgressHandler implements ClearHandler {

    private final ConsumingDetailMaintainService consumingDetailMaintainService;
    private final ImportErrorInfoMaintainService importErrorInfoMaintainService;
    private final ExportErrorInfoMaintainService exportErrorInfoMaintainService;

    public ClearHandlerImpl(
            ConsumingDetailMaintainService consumingDetailMaintainService,
            ImportErrorInfoMaintainService importErrorInfoMaintainService,
            ExportErrorInfoMaintainService exportErrorInfoMaintainService
    ) {
        this.consumingDetailMaintainService = consumingDetailMaintainService;
        this.importErrorInfoMaintainService = importErrorInfoMaintainService;
        this.exportErrorInfoMaintainService = exportErrorInfoMaintainService;
    }

    @Override
    public void clear() throws HandlerException {
        try {
            // 广播进度变更事件。
            fireProgressChanged(ProgressStatus.UNCERTAIN);

            // 清空相关实体的维护服务。
            consumingDetailMaintainService.clear();
            importErrorInfoMaintainService.clear();
            exportErrorInfoMaintainService.clear();
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            // 广播进度变更事件。
            fireProgressChanged(ProgressStatus.IDLE);
        }
    }
}
