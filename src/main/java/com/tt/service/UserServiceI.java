package com.tt.service;

import com.tt.ext.security.MyUserDetails;
import com.tt.model.Dept;

import java.util.List;

/**
 * Created by tt on 2016/10/2.
 */
public interface UserServiceI extends BaseService<MyUserDetails> {
    int del(List<Integer> ids);
    boolean isExist(String name);
    MyUserDetails createDefault(Dept dept);
    boolean changePwd(Integer id,String old,String cur);
}
