package com.github.merkurevsergei.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:app.properties")
@EnableJpaRepositories("com.github.merkurevsergei.repository")
@EnableTransactionManagement
public class DataConfig {

    @Bean
    public DataSource ds(@Value("${jdbc.driver}") String driver,
                         @Value("${jdbc.url}") String url,
                         @Value("${jdbc.username}") String username,
                         @Value("${jdbc.password}") String password) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Value("${hibernate.default_schema}") String schema,
            @Value("${hibernate.show_sql}") String showSQL,
            @Value("${hibernate.format_sql}") String formatSQL,
            @Value("${hibernate.hbm2ddl.auto}") String hbm2dll,
            @Value("${use_sql_comments}") String useSQLComments,
            DataSource ds) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(ds);
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setPackagesToScan("com.github.merkurevsergei.model");
        Properties cfg = new Properties();
        cfg.put("hibernate.default_schema", schema);
        cfg.put("hibernate.show_sql", showSQL);
        cfg.put("hibernate.format_sql", formatSQL);
        cfg.put("hibernate.hbm2ddl.auto", hbm2dll);
        cfg.put("use_sql_comments", useSQLComments);
        emf.setJpaProperties(cfg);
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(emf);
        return txManager;
    }
}
