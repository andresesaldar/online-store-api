package com.test.online.store.product.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = "com.test.online.store.product.*")
@ConfigurationPropertiesScan(basePackages = "com.test.online.store.product.*")
public class OnlineStoreProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineStoreProductApplication.class, args);
    }
    
}
