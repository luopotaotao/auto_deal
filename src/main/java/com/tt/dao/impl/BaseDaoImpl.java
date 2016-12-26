package com.tt.dao.impl;


import com.tt.dao.BaseDaoI;
import com.tt.model.BaseModel;
import com.tt.model.Dept;
import com.tt.util.SessionUtil;
import com.tt.web.exception.DeptNullException;
import org.hibernate.*;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BaseDaoImpl<T> implements BaseDaoI<T> {
    private Class<T> entityClass;
    @Autowired
    private SessionFactory sessionFactory;

    public BaseDaoImpl() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass = (Class) params[0];
    }

    private Integer getDeptId() {
        Dept dept = SessionUtil.getUser().getDept();
        if(dept==null||dept.getId()==null){
            throw new DeptNullException("未设置公司!");
        }
        return dept.getId();
    }

    /**
     * 获得当前事物的session
     *
     * @return org.hibernate.Session
     */
    public Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public Criteria getCriteria() {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        if (entityClass.getSuperclass() == BaseModel.class) {
            criteria.add(Restrictions.eq("dept_id", getDeptId()));
        }
        return criteria;
    }

    @Override
    public Criteria getCriteria(boolean checkDept) {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        if (checkDept&&(entityClass.getSuperclass() == BaseModel.class)) {
            criteria.add(Restrictions.eq("dept_id", getDeptId()));
        }
        return criteria;
    }

    public Criteria getCriteria(Map<String, Object> params) {
        Criteria c = getCriteria(!(params!=null&&params.containsKey("dept_id")));
        if (params != null && !params.isEmpty()) {
            params.forEach((key, val) -> {
                if (val instanceof String) {
                    String str = (String) val;
                    if (str.startsWith("%") || str.endsWith("%")) {
                        c.add(Restrictions.like(key, str));
                    } else {
                        c.add(Restrictions.eq(key, str));
                    }
                } else if (val instanceof Collection) {
                    c.add(Restrictions.in(key, (Collection) val));
                } else {
                    c.add(Restrictions.eq(key, val));
                }
            });
        }

        return c;
    }


    public Criteria getCriteria(Integer page, Integer pageSize) {

        Criteria c = getCriteria();
        if (page != null && pageSize != null) {
            c.setFirstResult((page - 1) * pageSize).setMaxResults(pageSize);
        }
        return c;
    }

    public Criteria getCriteria(Map<String, Object> params, Integer page, Integer pageSize) {
        Criteria c = getCriteria(params);
        if (page != null && pageSize != null) {
            c.setFirstResult((page - 1) * pageSize).setMaxResults(pageSize);
        }
        return c;
    }

    @Override
    public Serializable save(T o) {
        if (o != null) {
            if (entityClass.getSuperclass() == BaseModel.class) {
                BaseModel target = (BaseModel)o;
                //若页面没有设置dept_id 或者 不具有超级管理员权限,则设置为当前登录用户所属组
//                if(target.getDept_id()==null||!SessionUtil.hasRole("ROLE_SUPER")){
                if(target.getDept_id()==null){
                    ((BaseModel)o).setDept_id(getDeptId());
                }
            }
            try {
                return this.getCurrentSession().save(o);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public T getById(Serializable id) {
        return (T) getCurrentSession().get(entityClass, id);
    }


    @Override
    public T get(Class<T> c, Serializable id) {
        return (T) this.getCurrentSession().get(c, id);
    }

    private Query createQuery(String hql, Map<String, Object> params) {
        Query q = createQuery(hql);
        setParameters(q, params);
        return q;
    }

    private Query createQuery(String hql) {
        Query q = this.getCurrentSession().createQuery(hql);
        if (hql.indexOf(":dept_id") > 0) {
            q.setParameter("dept_id", getDeptId());
        }
        return q;
    }

    private SQLQuery createSQLQuery(String sql, Map<String, Object> params) {
        SQLQuery q = createSQLQuery(sql);
        setParameters(q, params);
        return q;
    }

    private SQLQuery createSQLQuery(String sql){
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        if (sql.indexOf(":dept_id") > 0) {
            q.setParameter("dept_id", getDeptId());
        }
        return q;
    }
    @Override
    public T get(String hql) {
        return (T) createQuery(hql, new HashMap<String, Object>()).uniqueResult();
    }

    @Override
    public T get(String hql, Map<String, Object> params) {
        return (T) createQuery(hql, params).uniqueResult();
    }

    @Override
    public void delete(T o) {
        if (o != null) {
            this.getCurrentSession().delete(o);
        }
    }

    @Override
    public void update(T o) {
        if (o != null) {
            if (entityClass.getSuperclass() == BaseModel.class) {
                ((BaseModel)o).setDept_id(getDeptId());
            }
            this.getCurrentSession().update(o);
        }
    }

    @Override
    public void saveOrUpdate(T o) {
        if (o != null) {
            this.getCurrentSession().saveOrUpdate(o);
        }
    }

    @Override
    public List<T> find(String hql) {
        return createQuery(hql, new HashMap<String, Object>()).list();
    }

    @Override
    public List<T> find(String hql, Map<String, Object> params) {
        return createQuery(hql, params).list();
    }

    @Override
    public List<T> find(Map<String, Object> params) {
        return getCriteria(params).list();
    }

    @Override
    public List<T> find(Map<String, Object> params, Integer page, Integer rows) {
        return getCriteria(params).setMaxResults(rows).setFirstResult((page - 1) * rows).list();
    }

    @Override
    public List<Map<String, Object>> findList(String hql, Map<String, Object> params) {
        return createQuery(hql, params).list();
    }

    @Override
    public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
        return createQuery(hql, params).setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }


    @Override
    public List<T> find(String hql, int page, int rows) {
        return createQuery(hql).setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    @Override
    public Long count(String hql) {
        return (Long) createQuery(hql).uniqueResult();
    }

    @Override
    public Long count(String hql, Map<String, Object> params) {
        return (Long) createQuery(hql, params).uniqueResult();
    }

    public Long count(Map<String, Object> params) {
        return (Long) getCriteria().setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public int executeHql(String hql) {
        return createQuery(hql).executeUpdate();
    }

    @Override
    public int executeHql(String hql, Map<String, Object> params) {
        return createQuery(hql,params).executeUpdate();
    }

    @Override
    public List<Object[]> findBySql(String sql) {
        return createSQLQuery(sql).list();
    }

    @Override
    public List<Object[]> findBySql(String sql, int page, int rows) {
        return createSQLQuery(sql).setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    @Override
    public List<Object[]> findBySql(String sql, Map<String, Object> params) {
        return createSQLQuery(sql,params).list();
    }

    @Override
    public List<T> findBySql(Class<T> entity, String sql, Map<String, Object> params) {
        return createSQLQuery(sql,params).addEntity(entity).list();
    }

    protected void setParameters(Query q, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            params.forEach((k, v) -> {

                if (v instanceof String) {
                    q.setParameter(k, v);
                } else if (v instanceof Collection) {
                    q.setParameterList(k, (Collection) v);
                } else {
                    q.setParameter(k, v);
                }
            });
        }
    }

    @Override
    public List<Object[]> findBySql(String sql, Map<String, Object> params, int page, int rows) {
        return createSQLQuery(sql,params).setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    @Override
    public List<T> findBySql(Class<T> entity, String sql, Map<String, Object> params, int page, int rows) {
        return createSQLQuery(sql).addEntity(entity).setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    @Override
    public int executeSql(String sql) {
        return createSQLQuery(sql).executeUpdate();
    }

    @Override
    public int executeSql(String sql, Map<String, Object> params) {
        return createSQLQuery(sql,params).executeUpdate();
    }

    @Override
    public BigInteger countBySql(String sql) {
        return (BigInteger) createSQLQuery(sql).uniqueResult();
    }

    @Override
    public BigInteger countBySql(String sql, Map<String, Object> params) {
        return (BigInteger) createSQLQuery(sql,params).uniqueResult();
    }

    @Override
    public int logicDelete(String table, Map<String, Object> params) {

        StringBuilder sql = new StringBuilder("update ").append(table).append(" set deleted=true ");
        if (params != null && params.size() > 0) {
            sql.append(" where ");
            params.forEach((k, v) -> {
                sql.append("and ").append(k);
                if (v instanceof Collection) {
                    sql.append(" in (:").append(k).append(")");
                } else if (v instanceof String) {
                    String str = (String) v;
                    if (str.startsWith("%") || str.endsWith("%")) {
                        sql.append(" like :");
                    } else {
                        sql.append("= :");
                    }
                    sql.append(k);
                }
            });
        }
        return executeSql(sql.toString(), params);
    }


    /**
     * 避免用户输入特殊字符时查询结果不准确的问题
     */
    public String ConvertParam(String param) {
        param = param.replace("", "");
        param = param.replace("[", "[[]");
        param = param.replace("_", "[_]");
        param = param.replace("%", "[%]");
        return param;
    }

    protected boolean isEmpty(String val) {
        return val == null || val.trim().isEmpty();
    }

//    protected SimpleExpression like(String field, String val) {
//        StringBuilder val_like = new StringBuilder("%");
//        val_like.append(val.trim()).append("%");
//        return Restrictions.like(field, val_like.toString());
//    }
}
