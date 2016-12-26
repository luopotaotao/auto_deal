package com.tt.ext.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tt on 2016/12/9.
 */
public class JsonAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String template = "{\"msg\":\"%s\",\"flag\":true}";
        String msg = "登录成功!";
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(String.format(template,msg));
        response.getWriter().flush();
    }
}
