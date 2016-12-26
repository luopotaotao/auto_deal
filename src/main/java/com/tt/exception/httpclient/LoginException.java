package com.tt.exception.httpclient;

/**
 * Created by tt on 2016/12/23.
 */
public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super(message);
    }
}
