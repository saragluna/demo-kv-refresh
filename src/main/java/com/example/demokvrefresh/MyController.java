package com.example.demokvrefresh;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class MyController {

    private SomeBean someBean;
    private JdbcTemplate jdbcTemplate;

    public MyController(SomeBean someBean, JdbcTemplate jdbcTemplate) {
        this.someBean = someBean;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/property")
    public String getProperty() {
        return someBean.getProperty();
    }

    @GetMapping("/jdbc")
    public String getJdbc() {
        return jdbcTemplate.queryForObject("SELECT CURDATE();", String.class);
    }

}
