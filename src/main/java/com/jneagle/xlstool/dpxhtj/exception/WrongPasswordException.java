package com.jneagle.xlstool.dpxhtj.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 密码错误异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WrongPasswordException extends HandlerException {

    private static final long serialVersionUID = -674332822979793963L;

    public WrongPasswordException() {
    }

    public WrongPasswordException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "密码错误";
    }
}
