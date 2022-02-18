package com.github.merkurevsergei.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
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
@EnableTransactionManagement
public class JPAConfig {

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

    //CHECKSTYLE:OFF
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Value("${hibernate.default_schema}") String schema,
            @Value("${hibernate.current_session_context_class}") String sessionContext,
            @Value("${hibernate.dialect}") String dialect,
            @Value("${hibernate.connection.characterEncoding}") String characterEncoding,
            @Value("${hibernate.show_sql}") String showSQL,
            @Value("${hibernate.format_sql}") String formatSQL,
            @Value("${hibernate.hbm2ddl.auto}") String hbm2dll,
            @Value("${use_sql_comments}") String useSQLComments,
            DataSource ds) {
        LocalContainerEntityManagerFactoryBean emfc = new LocalContainerEntityManagerFactoryBean();
        emfc.setDataSource(ds);
        emfc.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emfc.setPackagesToScan("com.github.merkurevsergei.model");
        Properties cfg = new Properties();
        cfg.put("hibernate.default_schema", schema);
        cfg.put("hibernate.current_session_context_class", sessionContext);
        cfg.put("hibernate.dialect", dialect);
        cfg.put("hibernate.connection.characterEncoding", characterEncoding);
        cfg.put("hibernate.show_sql", showSQL);
        cfg.put("hibernate.format_sql", formatSQL);
        cfg.put("hibernate.hbm2ddl.auto", hbm2dll);
        cfg.put("use_sql_comments", useSQLComments);
        emfc.setJpaProperties(cfg);
        return emfc;
    }
    //CHECKSTYLE:ON

    @Bean
    public PlatformTransactionManager jtx(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}