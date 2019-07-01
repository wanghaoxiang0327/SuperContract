package com.sskj.common.exception;

public class LogoutException extends Exception {

    private String message;

    public LogoutException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
