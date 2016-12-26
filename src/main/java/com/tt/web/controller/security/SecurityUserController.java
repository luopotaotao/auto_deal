package com.tt.web.controller.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tt on 2016/12/7.
 */
@RequestMapping("users")
@Controller
public class SecurityUserController {

    @RequestMapping(value = {"","index"})
    public String index(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(String.format("%s;%s;%s;%s", auth.getName(),auth.getAuthorities(),auth.getCredentials(),auth.getDetails()));
        return "index";
    }


}
