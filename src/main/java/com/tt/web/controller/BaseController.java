package com.tt.web.controller;


import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangziqi
 * @version 2015年12月25日
 * @see BaseController
 * @since
 */
@Controller
@RequestMapping("/baseController")
public class BaseController<T>
{


    protected JSONObject jsonResponse(String key,Object val){
        JSONObject ret = new JSONObject();
        ret.put(key,val);
        return ret;
    }

    /**
     * Flag响应
     * @param flag
     * @return
     */
    protected JSONObject flagResponse(Object flag){
        return jsonResponse("flag",flag);
    }

    /**
     * 分页列表响应
     * @param count 数据总数
     * @param list 当前页数据
     * @return
     */
    protected JSONObject listResponse(long count, List<T> list){
        JSONObject ret = new JSONObject();
        ret.put("total",count);
        ret.put("rows",list==null?new LinkedList<>():list);
        return ret;
    }
    /**
     * 列表响应
     * @param list 当前页数据
     * @return
     */
    protected JSONObject listResponse(List list){
        JSONObject ret = new JSONObject();
        ret.put("total",list!=null?list.size():0);
        ret.put("rows",list!=null?list:new LinkedList<>());
        return ret;
    }

    protected Map<String,Object> createHashMap(String name,Object val){
        Map<String,Object> ret = new HashMap<>();
        ret.put(name,val);
        return ret;
    }
   protected Map<String,Object> createHashMap(){
        Map<String,Object> ret = new HashMap<>();
        return ret;
    }

    protected String decodeStr(String str){
        String ret = null;
        try {
            ret = URLDecoder.decode(str,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
