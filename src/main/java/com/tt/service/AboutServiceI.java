package com.tt.service;

import com.tt.model.About;

/**
 * Created by tt on 2016/9/29.
 */
public interface AboutServiceI extends BaseService<About>{
    About get();
    About save(About about);
}
