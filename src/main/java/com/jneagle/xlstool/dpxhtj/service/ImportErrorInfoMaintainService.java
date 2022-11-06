package com.jneagle.xlstool.dpxhtj.service;

import com.dwarfeng.subgrade.stack.bean.key.UuidKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.jneagle.xlstool.dpxhtj.bean.entity.ImportErrorInfo;

/**
 * 导入错误信息维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ImportErrorInfoMaintainService extends BatchCrudService<UuidKey, ImportErrorInfo>,
        EntireLookupService<ImportErrorInfo>, ClearableMaintainService {
}
