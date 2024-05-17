package com.ktnet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import jakarta.annotation.PostConstruct;

/**
 * exclude = SecurityAutoConfiguration.class
 * 	: Spring Security login default page disable.
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableScheduling
@EnableCaching
@ComponentScan(basePackages = "com.ktnet.*") 
@PropertySource(value = { "classpath:common-env.properties" })
public class FtaServiceApplication {
	
	@Value("${spring.profiles.active}")
    private String server;

	public static void main(String[] args) {
		SpringApplication.run(FtaServiceApplication.class, args);
	}
	
	@PostConstruct
    private void start() {
        System.out.println("starting server resource = " + server);
    }
}
