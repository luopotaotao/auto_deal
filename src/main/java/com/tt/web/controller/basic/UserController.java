package com.tt.web.controller.basic;

import com.alibaba.fastjson.JSONObject;
import com.tt.ext.security.Authority;
import com.tt.ext.security.MyUserDetails;
import com.tt.model.Dept;
import com.tt.service.DeptServiceI;
import com.tt.service.UserServiceI;
import com.tt.util.SessionUtil;
import com.tt.util.UrlStringDecoder;
import com.tt.web.controller.BaseController;
import com.tt.web.exception.ExistException;
import com.tt.web.exception.PermissionDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tt on 2016/10/2.
 */
@Controller
@RequestMapping("basic/user")
public class UserController extends BaseController<MyUserDetails> {
    @Autowired
    private UserServiceI userService;

    @Autowired
    private DeptServiceI deptService;

    @RequestMapping("index/{dept_id}")
    public String index(@PathVariable Integer dept_id, Model model) {
        model.addAttribute("dept_id", dept_id);
        return "module_basic/dept_users";
    }

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public MyUserDetails get(@PathVariable int id) {
        return userService.get(id);
    }

    @RequestMapping(value = "/queryAll/{dept_id}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject list(@RequestParam(required = false) String name,@PathVariable(value = "dept_id",required = false) Integer dept_id) {
        Map<String, Object> params = new HashMap<>();
        name = UrlStringDecoder.decode(name);
        if (name != null) {
            params.put("name", name);
        }
        if(dept_id!=null){
            params.put("dept_id",dept_id);
        }
        List<MyUserDetails> list = userService.list(params);
        return listResponse(list);
    }


    @RequestMapping(value = "post", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject add(@ModelAttribute MyUserDetails user, BindingResult result) {
        if (userService.isExist(user.getUsername())) {
            throw new ExistException("账号已使用,请选择其他账号!");
        }
        if(user.getAuthority().getId()<SessionUtil.getUser().getId()){
            throw new PermissionDeniedException("权限不足!");
        }
        userService.add(user);
        return flagResponse(1);
    }
    @RequestMapping(value = "createDefault", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject createDefault(@RequestParam(value = "dept_id") Integer id) {
        Dept dept = deptService.get(id);
        if (userService.isExist(dept.getCode())) {
            throw new ExistException("账号已使用,请选择其他账号!");
        }
        MyUserDetails user = userService.createDefault(dept);
        return flagResponse(user.getId()>0);
    }

    @RequestMapping(value = "put", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject update(@ModelAttribute MyUserDetails MyUserDetails) {
        userService.update(MyUserDetails);
        return flagResponse(MyUserDetails.getId());
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delete(@RequestParam(value = "ids[]") Integer[] ids) {
        int ret = userService.del(Arrays.asList(ids));
        return flagResponse(ret);
    }

    @RequestMapping("currentUser")
    @ResponseBody
    public MyUserDetails currentUser(){
        return SessionUtil.getUser();
    }


    @RequestMapping(value = "changePwd",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject changePwd(@RequestParam(value = "old") String old,@RequestParam(value = "cur")String cur){
        boolean flag = userService.changePwd(SessionUtil.getUser().getId(),old,cur);
        return flagResponse(flag);
    }
}
