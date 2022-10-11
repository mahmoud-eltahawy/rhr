package com.rhr.heat.config;

import com.zaxxer.hikari.HikariDataSource;

import org.flywaydb.core.Flyway;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatasourceConfig {

    @Bean
    @Primary
    public HikariDataSource hikariDataSource() {
        return DataSourceBuilder
                .create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://localhost:5432/postgres")
                .username("admin")
                .password("admin")
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(HikariDataSource hikariDataSource) {
        return new JdbcTemplate(hikariDataSource);
    }
    
    @Bean
    public Flyway flyway(HikariDataSource hikariDataSource) {
    	Flyway flyway = Flyway.configure().dataSource(hikariDataSource).load();
    	flyway.migrate();
    	return flyway;
    }
}