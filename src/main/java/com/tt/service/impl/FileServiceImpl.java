package com.tt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tt.dao.FileDaoI;
import com.tt.model.File;
import com.tt.service.FileService;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by tt on 2016/11/28.
 */
@Service
@Transactional
public class FileServiceImpl implements FileService {
    @Autowired
    private FileDaoI fileDao;
    @Override
    public File get(Serializable id) {
        return fileDao.getById(id);
    }

    @Override
    public List<File> list(Map<String, Object> params, Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public File add(File instance) {
 //       instance.setDept_id(dept_id);
        fileDao.save(instance);
        return instance;
    }

    @Override
    public int del(List<Integer> ids) {
        return 0;
    }

    @Override
    public File update(File instance) {
        return null;
    }

    @Override
    public File getById(String id) {
        return fileDao.getById(id);
    }
}
