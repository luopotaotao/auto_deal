package com.tt.aspect;

import com.tt.ext.security.MyUserDetails;
import com.tt.util.SessionUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by tt on 2016/11/24.
 * 由于SpringAop不能进行嵌套调用,因此此方案不可用
 */
@Aspect
public class RoleAspect {
//    @Around("execution(* *.get(..))")
//    public void testAround(ProceedingJoinPoint pj){
//
//        System.out.println("before");
//        try {
//            Object[] args = pj.getArgs();
//            MyUserDetails user = SessionUtil.getInspector();
//
//            String username= user.getUsername();
//            System.out.println(username);
////            ((Map<String,Object>)args[0]).put("username",username);
//            System.out.println(String.format("获取参数:%s",args));
//            pj.proceed();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        System.out.println("after");
//    }


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
