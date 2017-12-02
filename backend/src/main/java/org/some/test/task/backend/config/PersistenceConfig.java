package org.some.test.task.backend.config;

import org.apache.commons.dbcp.BasicDataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by thefp on 01.12.2017.
 */
@Configuration
@EnableTransactionManagement
public class PersistenceConfig {

    @Value("${jdbc.driverClassName}")
    private String jdbcDriverClassName;
    @Value( "${jdbc.url}" )
    private String jdbcUrl;
    @Value( "${jdbc.username}" )
    private String jdbcUsername;
    @Value( "${jdbc.password}" )
    private String jdbcPassword;
    @Value( "${hibernate.hbm2ddl.auto}" )
    private String hbm2ddl;
    @Value( "${hibernate.dialect}" )
    private String dialect;

    @Bean
    public DataSource dataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(jdbcDriverClassName);
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(jdbcUsername);
        dataSource.setPassword(jdbcPassword);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean localEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localEntityManagerFactoryBean.setDataSource(dataSource());
        localEntityManagerFactoryBean.setPackagesToScan(new String[] {"org.some.test.task.impl"});
        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto",hbm2ddl);
        properties.put("hibernate.dialect",dialect);
        localEntityManagerFactoryBean.setJpaProperties(properties);
        localEntityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter());
        return localEntityManagerFactoryBean;
    }

    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter (){
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setShowSql(true);
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public JpaTransactionManager jpaTransactionManager() {
        JpaTransactionManager txManager
                = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return txManager;
    }

}
