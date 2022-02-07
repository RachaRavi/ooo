package com.coreantech.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages="com.coreantech.data")
@EnableJpaRepositories("com.coreantech.data.repo")
public class AssetApplication {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String args[]) {
//        SpringApplication.run(AssetApplication.class,args);
        ApplicationContext applicationContext = SpringApplication.run(AssetApplication.class, args);
        for (String name: applicationContext.getBeanDefinitionNames()) {
            System.out.println(name);
        }
    }

}
