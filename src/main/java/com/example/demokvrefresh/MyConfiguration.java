package com.example.demokvrefresh;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MyConfiguration {

    @RefreshScope
    @Bean
    public DataSource myDataSource(@Value("${spring.datasource.url}") String url,
                                   @Value("${spring.datasource.username}") String username,
                                   @Value("${spring.datasource.password}") String password) {

        return DataSourceBuilder.create()
                .url(url)
                .password(password)
                .username(username)
                .build();
    }

    @Bean
    @RefreshScope
    public SomeBean someBean(@Value("${my.property}") String property) {
        return new SomeBean(property);
    }
}
