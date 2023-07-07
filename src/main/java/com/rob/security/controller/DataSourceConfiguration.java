package com.rob.security.controller;

import com.rob.security.jdbc.JdbcProducts;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    @Bean
    @Primary
    public DataSource usersDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
                .build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.test")
    public DataSourceProperties testDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource testDataSource() {
        return testDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.alfa")
    public DataSourceProperties alfaDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource alfaDataSource() {
        return alfaDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        UserDetails user1 = User.withDefaultPasswordEncoder()
                .username("alfa")
                .password("alfa")
                .roles("USER")
                .build();
        UserDetails user2 = User.withDefaultPasswordEncoder()
                .username("beta")
                .password("beta")
                .roles("USER")
                .build();
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.createUser(user1);
        users.createUser(user2);
        return users;
    }

    @Bean(name = "alfaJdbc")
    public JdbcProducts alfaJdbc( @Qualifier("alfaDataSource") DataSource dataSource) {
        JdbcProducts jdbcProducts = new JdbcProducts();
        jdbcProducts.setDataSource(dataSource);
        return jdbcProducts;
    }

    @Bean(name = "testJdbc")
    public JdbcProducts testJdbc( @Qualifier("testDataSource") DataSource dataSource) {
        JdbcProducts jdbcProducts = new JdbcProducts();
        jdbcProducts.setDataSource(dataSource);
        return jdbcProducts;
    }
}
