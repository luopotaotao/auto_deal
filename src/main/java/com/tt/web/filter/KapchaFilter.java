package com.tt.web.filter;

import com.google.code.kaptcha.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by tt on 2016/12/16.
 */
public class KapchaFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getMethod().equals("POST")&&req.getRequestURI().indexOf("login")>-1) {
            System.out.println("do filter");
            String kaptcha = request.getParameter("kaptcha");
            if (kaptcha == null || kaptcha.trim().isEmpty() || !kaptcha.equals(req.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY))) {
                ((HttpServletResponse)response).setHeader("Content-type", "text/html;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(String.format("{\"msg\":\"%s\"}","验证码错误"));
                writer.flush();
                writer.close();
            } else {
                filterChain.doFilter(request, response);
            }
        }else{
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
