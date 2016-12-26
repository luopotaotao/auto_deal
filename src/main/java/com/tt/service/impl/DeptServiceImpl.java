package com.tt.service.impl;

import com.tt.dao.UserDaoI;
import com.tt.ext.security.MyUserDetails;
import com.tt.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.tt.dao.DeptDaoI;
import com.tt.model.Dept;
import com.tt.service.DeptServiceI;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taotao on 2016/9/23.
 */
@Service("deptService")
@Transactional
public class DeptServiceImpl implements DeptServiceI {
    @Autowired
    private DeptDaoI deptDao;
    @Override
    public Dept get(Serializable id) {
        return deptDao.getById(id);
    }

    @Override
    public List<Dept> list(Map<String,Object> params, Integer page, Integer PageSize) {
        StringBuilder hql = new StringBuilder("from Dept WHERE 1=1 ");
//
        Object typ =  params.get("typ");
        Object name =  params.get("name");
        if(typ!=null&&(Byte)typ!=0){
            params.put("typ",typ);
            hql.append(" AND typ=:typ ");
        }
        if(name!=null&&!name.toString().isEmpty()){
            params.put("name","%"+name+"%");
            hql.append(" AND name like :name ");
        }
        if (!SessionUtil.hasRole("ROLE_SUPER")) {
            params.put("dept_id", SessionUtil.getUser().getDept().getId());
            hql.append(" and id=:dept_id");
        }
        List<Dept> ret = deptDao.find(hql.toString(), params, page, PageSize);
        return ret;
    }

    @Override
    public long count(Map<String,Object> params) {
        StringBuilder hql = new StringBuilder("select count(*) from Dept WHERE 1=1 ");
//
        Byte typ = (Byte) params.get("typ");
        String name = (String) params.get("name");
        if(typ!=null&&typ!=0){
            hql.append(" AND typ=:typ ");
        }
        if(name!=null){
            params.put("name","%"+name+"%");
            hql.append(" AND name like :name ");
        }
        long ret = deptDao.count(hql.toString(), params);
        return ret;
    }

    @Override
    public Dept add(Dept dept) {
        deptDao.save(dept);
        return dept;
    }

    @Override
    public int del(List<Integer> ids) {
        if(ids==null||ids.size()<1){
            return 0;
        }
        Map<String, Object> params = new HashMap<String, Object>();
//
        params.put("ids", ids);
        return deptDao.executeHql("delete from Dept where id in (:ids)", params);
    }

    @Override
    public Dept update(Dept dept) {
        deptDao.update(dept);
        return dept;
    }

    @Override
    public Dept get(int id) {
        System.out.println("run get");
        return deptDao.get(Dept.class, id);
    }
}
