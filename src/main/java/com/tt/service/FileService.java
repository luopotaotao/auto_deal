package com.tt.service;

import com.tt.model.File;

/**
 * Created by tt on 2016/11/28.
 */
public interface FileService  extends BaseService<File>{
    File getById(String id);
}
