package com.tt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tt.dao.AboutDaoI;
import com.tt.model.About;
import com.tt.service.AboutServiceI;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tt on 2016/9/29.
 */
@Service("aboutService")
@Transactional
public class AboutServiceImpl implements AboutServiceI {
    @Autowired
    private AboutDaoI aboutDao;

    @Override
    public About get() {
        return aboutDao.get();
    }

    @Override
    public About save(About about) {
        About ret = null;
        int count = aboutDao.countBySql("select count(*) from b_about").intValue();
        if(count>0){
            About old = aboutDao.get();
            old.setContent(about.getContent());
            aboutDao.update(old);
            ret = old;
        }else{
            aboutDao.save(about);
            ret = about;
        }
        return ret;
    }
}
