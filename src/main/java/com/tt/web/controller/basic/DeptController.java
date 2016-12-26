package com.tt.web.controller.basic;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.tt.web.controller.BaseController;
import com.tt.model.Dept;
import com.tt.service.DeptServiceI;
import com.tt.util.UrlStringDecoder;

import java.util.*;

/**
 * Created by taotao on 2016/9/27.
 * 检测机构
 */

@Controller
@RequestMapping("basic/dept")
public class DeptController extends BaseController<Dept> {
    @Autowired
    private DeptServiceI deptService;

    @Secured({"ROLE_SUPER","ROLE_ADMIN"})
    @RequestMapping("index")
    public String index(Model model) {
        return "module_basic/dept_";
    }

    @RequestMapping("{dept_id}/personal")
    public String personal(@PathVariable Integer dept_id, Model model) {
        model.addAttribute("dept_id", dept_id);
        return "module_basic/dept_user";
    }

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Dept get(@PathVariable int id) {
        return deptService.get(id);
    }

    @RequestMapping(value = "query", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject list(@RequestParam(required = false) Byte typ,
                           @RequestParam(required = false) String name,
                           @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                           @RequestParam(value = "rows", required = false, defaultValue = "10") Integer pageSize) {
        Map<String, Object> params = new HashMap<>();
        name = UrlStringDecoder.decode(name);
        if (name != null) {
            params.put("name", name);
        }
        if (typ != null) {
            params.put("typ", typ);
        }
        List<Dept> list = null;
        long count = deptService.count(params);
        if (count > 0) {
            list = deptService.list(params, page, pageSize);
        }
        return listResponse(count, list);
    }

    //    @RequestMapping(value = "post", method = RequestMethod.POST)
    @RequestMapping(value = "post")
    @ResponseBody
    public Dept add(@ModelAttribute Dept dept) {
        dept = deptService.add(dept);
        return dept;
    }

    @RequestMapping(value = "put", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject update(@ModelAttribute Dept dept) {
        deptService.update(dept);
        return flagResponse(dept.getId() > 0);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delete(@RequestParam(value = "ids[]") int[] ids) {
        List<Integer> list = new LinkedList<>();
        Arrays.stream(ids).forEach(id -> list.add(id));
        int ret = deptService.del(list);
        return flagResponse(ret);
    }
}
