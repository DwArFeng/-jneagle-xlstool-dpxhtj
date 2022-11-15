package com.jneagle.xlstool.dpxhtj.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 模板加载失败异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TemplateLoadFailedException extends HandlerException {

    private static final long serialVersionUID = -8858084833433651959L;

    public TemplateLoadFailedException() {
    }

    public TemplateLoadFailedException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "模板加载失败";
    }
}
