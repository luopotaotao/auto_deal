package com.tt.web.config;

import com.tt.aspect.DeptAspect;
import com.tt.aspect.RoleAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by tt on 2016/11/24.
 * <aop:aspectj-autoproxy proxy-target-class="true"/> 需要配置在MVC的上下文中,如果配置在RootConfig中将不会生效!
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackageClasses = com.tt.aspect.DeptAspect.class)
public class AspectjConfig {
    //    @Bean
//    public RoleAspect roleAspect(){
//        return new RoleAspect();
//    }
    @Bean
    public DeptAspect deptAspect() {
        System.out.println("****************************************init");
        return new DeptAspect();
    }
}
