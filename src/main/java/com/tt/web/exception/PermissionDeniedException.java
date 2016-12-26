package com.tt.web.exception;

/**
 * Created by tt on 2016/12/14.
 */
public class PermissionDeniedException extends OperationException {
    public PermissionDeniedException(String message) {
        super(message);
    }
}
