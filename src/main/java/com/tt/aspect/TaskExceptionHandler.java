package com.tt.aspect;

import com.tt.schedule.WebTask;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by tt on 2016/12/26.
 */
@Aspect
public class TaskExceptionHandler {
    @AfterThrowing(value = "execution(* com.tt.schedule.WebTask.*(..))",throwing = "e")
    public void testAround(ProceedingJoinPoint pj,Exception e){
        if(pj.getTarget() instanceof WebTask){
            WebTask.addMsg(e.getMessage());
        }
    }

}
