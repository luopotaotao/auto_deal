package com.tt.service;

import com.tt.model.Dept;

/**
 * Created by taotao on 2016/9/23.
 */
public interface DeptServiceI extends BaseService<Dept> {
//    List<Dept> list(Byte typ, String name, int page, int PageSize);
//    long count(Byte typ, String name);
    //TODO 额外参数
        Dept get(int id);
}
