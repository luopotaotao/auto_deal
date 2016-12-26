package com.tt.aspect;

import com.tt.ext.security.MyUserDetails;
import com.tt.model.Dept;
import com.tt.util.SessionUtil;
import com.tt.web.exception.DeptNullException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by tt on 2016/11/24.
 */
@Aspect
public class DeptAspect {
    @Around("@annotation(com.tt.annotation.NeedDept)")
    public Object testAround(ProceedingJoinPoint pj) throws Throwable {

        System.out.println("before");
        Dept dept = SessionUtil.getUser().getDept();
        if(dept==null||dept.getId()==null){
            throw new DeptNullException("需要设置公司才可查看数据!");
        }
        System.out.println("after");
        return pj.proceed();
    }


//    @AfterReturning(value = "execution(org.hibernate.Criteria *+.getCriteria())",returning = "c")
//    public void addDeptRestriction(JoinPoint joinPoint,Criteria c){
//        c.add(Restrictions.eq("dept_id",getDeptId()));
//    }
//
//    @Around("execution(* com.tt.dao.impl.*+.*(String,java.util.Map,..)) && args(hql,params,..)")
//    public Object addDeptParam(ProceedingJoinPoint pj,String hql,Map<String,Object> params) throws Throwable {
//        if(hql.indexOf("dept_id")>0){
//            params.put("dept_id",getDeptId());
//        }
//        Object ret = pj.proceed();
//        System.out.println("dept added");
//        return ret;
//    }
//    @Before("execution(* com.tt.dao.impl.*.*(String,java.util.Map,..)) && args(sql,params,..)")
//    public void addDeptParam(String sql,Map<String,Object> params) throws Throwable {
//        if(sql.indexOf("dept_id")>0){
//            params.put("dept_id",getDeptId());
//        }
//        System.out.println("dept added");
//    }
    private Integer getDeptId(){
        Integer id = null;
        MyUserDetails userDetails = SessionUtil.getUser();
        if(userDetails!=null&&userDetails.getDept()!=null){
            id =  userDetails.getDept().getId();
        }
        return id;
    }
}
