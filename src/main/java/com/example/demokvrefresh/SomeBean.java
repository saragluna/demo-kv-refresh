package com.example.demokvrefresh;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

public class SomeBean {

    private String property;

    public SomeBean(String property) {
        this.property = property;
        System.out.println("Property value is: " + property);
    }

    public String getProperty() {
        return property;
    }

}
