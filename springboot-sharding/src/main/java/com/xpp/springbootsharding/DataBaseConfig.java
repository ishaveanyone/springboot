package com.xpp.springbootsharding;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfig {
    public static final String LOCAL_DB = "local-db";


    @Bean(name = LOCAL_DB)
    @ConfigurationProperties(prefix = "spring.datasource.local")
    @Primary
    public DataSource dataSourceFintek() {
        return new HikariDataSource();
    }

}
