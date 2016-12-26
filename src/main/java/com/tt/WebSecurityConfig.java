package com.tt;

import com.tt.ext.security.AuthenticationFilter;
import com.tt.ext.security.JsonAuthenticationFailureHandler;
import com.tt.ext.security.JsonAuthenticationSuccessHandler;
import com.tt.web.filter.KapchaFilter;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by tt on 2016/11/22.
 */
@Configuration
@EnableWebSecurity()
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private BasicDataSource dataSource;
    @Autowired
    @Qualifier(value = "myUserDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**").antMatchers("/kaptcha");
    }

    /**
     * Session 管理
     * http://docs.spring.io/spring-security/site/docs/4.2.1.BUILD-SNAPSHOT/reference/htmlsingle/#session-mgmt
     * http://www.baeldung.com/spring-security-session
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .addFilterBefore(new KapchaFilter(),UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .headers().frameOptions().sameOrigin()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureHandler(failureHandler())
                .successHandler(successHandler())
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/login**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionUrl("/login?invalid")  //session无效时页面
                .maximumSessions(2)//最大并发登录数

                .maxSessionsPreventsLogin(false)  //当登录的session到达最大值时:true:禁止登录;false:已有session过期,新用户登录,默认为false
                .sessionRegistry(sessionRegistry())
                .expiredUrl("/login?expired") //session过期时页面,过期是由于被其他登录用户挤掉
                .and()
                .sessionFixation().migrateSession() //Session Fixation Protection
        ;


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin").password("admin").roles("ADMIN")
//                .and().withUser("user").password("user").roles("ADMIN", "USER");
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * Session管理中session并发控制需要用到此bean
     *
     * @return
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    /**
     * 此bean可以操控已登录的Session
     *
     * @return
     */
    @Bean
    public SessionRegistry sessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println(new StandardPasswordEncoder("1234").encode("666666"));

        return new StandardPasswordEncoder("1234");
    }

    @Bean(name = "myAuthenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationFilter authenticationFilter() {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setAuthenticationFailureHandler(failureHandler());
        authenticationFilter.setAuthenticationSuccessHandler(successHandler());
        return new AuthenticationFilter();
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new JsonAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new JsonAuthenticationSuccessHandler();
    }
}
