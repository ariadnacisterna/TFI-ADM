package com.example.tfiadm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class TfiAdmApplication {

    public static void main(String[] args) {
        SpringApplication.run(TfiAdmApplication.class, args);
    }

}