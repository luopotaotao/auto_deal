package com.tt.web.controller;

import com.tt.ext.security.MyUserDetails;
import com.tt.model.Dept;
import com.tt.service.DeptServiceI;
import com.tt.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tt on 2016/12/8.
 */
@Controller
public class MainController {

    @Autowired
    private DeptServiceI deptService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        return "main/index";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(@RequestParam(required = false) String info) {
        return "main/admin_login";
    }

    @RequestMapping("/logout")
    @ResponseBody
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        response.setStatus(HttpServletResponse.SC_OK);
        return "{msg:'logout success'}";
    }

    @RequestMapping("authimg")
    public void kaptcha() {

    }

    @RequestMapping("/welcome")
    public String welcome() {
        return "main/welcome";
    }

    @Secured({"ROLE_SUPER"})
    @RequestMapping("switchDept/{id}")
    @ResponseBody
    public Dept switchDept(@PathVariable(value = "id") Integer id) {
        Dept dept = deptService.get(id);
        SessionUtil.getUser().setDept(dept);
        return dept;
    }
}
