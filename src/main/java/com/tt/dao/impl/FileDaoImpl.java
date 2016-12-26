package com.tt.dao.impl;

import org.springframework.stereotype.Repository;
import com.tt.dao.FileDaoI;
import com.tt.dao.impl.BaseDaoImpl;
import com.tt.model.File;

/**
 * Created by tt on 2016/11/28.
 */
@Repository("fileDao")
public class FileDaoImpl extends BaseDaoImpl<File> implements FileDaoI {
}
