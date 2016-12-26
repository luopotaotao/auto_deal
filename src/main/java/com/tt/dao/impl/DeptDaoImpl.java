package com.tt.dao.impl;

import org.springframework.stereotype.Repository;
import com.tt.dao.DeptDaoI;
import com.tt.dao.impl.BaseDaoImpl;
import com.tt.model.Dept;

/**
 * Created by taotao on 2016/9/23.
 */
@Repository("deptDao")
public class DeptDaoImpl extends BaseDaoImpl<Dept> implements DeptDaoI {
}
