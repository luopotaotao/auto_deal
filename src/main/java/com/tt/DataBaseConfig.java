package com.tt;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by tt on 2016/11/23.
 */
@Configuration
@ComponentScan("com.tt.service")
@EnableTransactionManagement(proxyTargetClass = true)
public class DataBaseConfig {
    @Bean
    public BasicDataSource dataSource(@Value("${jdbc_url}") String jdbc_url,
                                      @Value("${jdbc_username}") String jdbc_username,
                                      @Value("${jdbc_password}") String jdbc_password,
                                      @Value("${jdbc_initial_size}") String jdbc_initial_size,
                                      @Value("${jdbc_max_active}") String jdbc_max_active,
                                      @Value("${validationQuery}") String validationQuery
    ) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl(jdbc_url);
        ds.setUsername(jdbc_username);
        ds.setPassword(jdbc_password);
        ds.setInitialSize(Integer.parseInt(jdbc_initial_size));
        ds.setMaxActive(Integer.parseInt(jdbc_max_active));
        ds.setValidationQuery(validationQuery);
        ds.addConnectionProperty("useSSL", "false");

        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource,
                                                  @Value("${hibernate.dialect}") String dialect,
                                                  @Value("${hibernate.default_schema}") String default_schema,
                                                  @Value("${hibernate.hbm2ddl.auto}") String hbm2ddl_auto,
                                                  @Value("${hibernate.show_sql}") String show_sql,
                                                  @Value("${hibernate.format_sql}") String format_sql,
                                                  @Value("${hibernate.use_sql_comments}") String use_sql_comments
    ) {
        LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
        sfb.setDataSource(dataSource);

        sfb.setPackagesToScan("com.tt.model","com.tt.ext.security");
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", dialect);
        properties.setProperty("hibernate.default_schema", default_schema);
        properties.setProperty("hibernate.hbm2ddl.auto", hbm2ddl_auto);
        properties.setProperty("hibernate.show_sql", show_sql);
        properties.setProperty("hibernate.format_sql", format_sql);
        properties.setProperty("hibernate.use_sql_comments", use_sql_comments);
        sfb.setHibernateProperties(properties);
        return sfb;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory){
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }
}
