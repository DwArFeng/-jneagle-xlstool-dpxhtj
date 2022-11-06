package com.jneagle.xlstool.dpxhtj.service;

import com.dwarfeng.subgrade.stack.bean.key.UuidKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.jneagle.xlstool.dpxhtj.bean.entity.ExportErrorInfo;

/**
 * 导出错误信息维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExportErrorInfoMaintainService extends BatchCrudService<UuidKey, ExportErrorInfo>,
        EntireLookupService<ExportErrorInfo>, ClearableMaintainService {
}
