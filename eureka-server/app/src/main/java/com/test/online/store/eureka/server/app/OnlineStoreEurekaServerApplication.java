package com.test.online.store.eureka.server.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class OnlineStoreEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineStoreEurekaServerApplication.class, args);
    }
}
