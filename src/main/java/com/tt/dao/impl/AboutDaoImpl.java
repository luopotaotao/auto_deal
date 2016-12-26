package com.tt.dao.impl;

import com.tt.dao.AboutDaoI;
import com.tt.model.About;
import org.springframework.stereotype.Repository;


/**
 * Created by tt on 2016/9/29.
 */
@Repository("aboutDao")
public class AboutDaoImpl extends BaseDaoImpl<About> implements AboutDaoI {
    @Override
    public About get() {
        return (About) getCriteria().uniqueResult();
    }
}
