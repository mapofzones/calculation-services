package com.mapofzones.calculations.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(basePackages = {
        "com.mapofzones.calculations.common.repository.postgres",
        "com.mapofzones.calculations.delegationamount.repository.postgres",
        "com.mapofzones.calculations.ibcvolume.repository.postgres",
        "com.mapofzones.calculations.transactions.repository.postgres",
        "com.mapofzones.calculations.ibctransfers.repository.postgres"
},
        entityManagerFactoryRef = "postgresEntityManager",
        transactionManagerRef = "postgresTransactionManager")

public class ConfigPostgresRepository {

    @Bean
    public LocalContainerEntityManagerFactoryBean postgresEntityManager(DataSource postgresDataSource) {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(postgresDataSource);
        em.setPackagesToScan(
                "com.mapofzones.calculations.delegationamount.repository.postgres",
                "com.mapofzones.calculations.ibcvolume.repository.postgres",
                "com.mapofzones.calculations.common.repository.postgres",
                "com.mapofzones.calculations.transactions.repository.postgres",
                "com.mapofzones.calculations.ibctransfers.repository.postgres"
        );

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        final HashMap<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto", env.getPropertyString("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    @ConfigurationProperties("spring.datasource.postgres")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource postgresDataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class)
                .build();
    }

    @Bean
    public PlatformTransactionManager postgresTransactionManager(LocalContainerEntityManagerFactoryBean postgresEntityManager) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(postgresEntityManager.getObject());
        return transactionManager;
    }
}




