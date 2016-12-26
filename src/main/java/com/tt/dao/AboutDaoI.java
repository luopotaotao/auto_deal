package com.tt.dao;

import com.tt.dao.BaseDaoI;
import com.tt.model.About;

/**
 * Created by tt on 2016/12/4.
 */
public interface AboutDaoI extends BaseDaoI<About> {
    About get();
}
