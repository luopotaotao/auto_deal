package com.tt.dao.impl;

import com.tt.dao.UserDaoI;
import com.tt.ext.security.MyUserDetails;
import com.tt.util.SessionUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by taotao on 2016/9/23.
 */
@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<MyUserDetails> implements UserDaoI {

    @Override
    public List<MyUserDetails> find(Map<String,Object> params) {
        String name = (String) params.get("name");
        if(name!=null&&!name.trim().isEmpty()){
            params.put("name","%"+name+"%");
        }
        Criteria criteria = getCriteria(params).addOrder(Order.asc("id"));
        int role = 2;
        switch (SessionUtil.getUser().getAuthority().getAuthority()){
            case "ROLE_SUPER":
                role = 0;
                break;
            case "ROLE_ADMIN":
                role = 1;
                break;
            case "ROLE_CUSTOM":
                role = 2;
                break;
        }
        criteria.add(Restrictions.gt("authority.id",role));
        return criteria.list();
    }
}
