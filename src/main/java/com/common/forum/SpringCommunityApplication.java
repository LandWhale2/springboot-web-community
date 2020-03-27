package com.common.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringCommunityApplication {

    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false");
        System.setProperty("spring.devtools.livereload.enabled", "true");
        System.setProperty("spring.thymeleaf.cache", "false");
        SpringApplication.run(SpringCommunityApplication.class, args);
    }

}
