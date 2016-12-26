package com.tt.web.exception;

/**
 * Created by tt on 2016/12/8.
 */
public class OperationException extends RuntimeException {
    public OperationException() {
    }

    public OperationException(String message) {
        super(message);
    }
}
