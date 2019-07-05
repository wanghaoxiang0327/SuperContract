package com.sskj.common.exception;

/**
 * Created by Administrator on 2018/5/11.
 */

public final class QuitCockroachException extends RuntimeException {
    public QuitCockroachException(String message) {
        super(message);
    }
}