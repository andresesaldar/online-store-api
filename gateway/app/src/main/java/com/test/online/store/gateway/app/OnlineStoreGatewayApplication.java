package com.test.online.store.gateway.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = "com.test.online.store.gateway.*")
@ConfigurationPropertiesScan(basePackages = "com.test.online.store.gateway.*")
public class OnlineStoreGatewayApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(OnlineStoreGatewayApplication.class, args);
    }
}
