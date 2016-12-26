package com.tt.ext.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tt on 2016/12/9.
 */

public class JsonAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String template = "{\"msg\":\"%s\"}";
        String msg = null;
        if(exception instanceof SessionAuthenticationException){
            msg="账户已在其他地方登录,如不是您的操作,请重新登录并修改密码!";
        }else if(exception instanceof UsernameNotFoundException){
            msg="账户不存在!";
        }else if(exception instanceof CredentialsExpiredException){
            msg="登录信息已过期,请重新登录!";
        }else if(exception instanceof BadCredentialsException){
            msg="登录失败,用户名或密码错误!";
        }else if(exception instanceof LockedException){
            msg="登录失败,用户名已锁定!";
        }
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(String.format(template,msg));
        response.getWriter().flush();
    }


}
