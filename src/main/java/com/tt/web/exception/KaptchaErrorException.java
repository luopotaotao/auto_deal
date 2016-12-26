package com.tt.web.exception;

/**
 * Created by tt on 2016/12/16.
 */
public class KaptchaErrorException extends OperationException {
    public KaptchaErrorException(String message) {
        super(message);
    }
}
