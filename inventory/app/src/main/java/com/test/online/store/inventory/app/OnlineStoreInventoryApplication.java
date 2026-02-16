package com.test.online.store.inventory.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = "com.test.online.store.inventory.*")
@ConfigurationPropertiesScan(basePackages = "com.test.online.store.inventory.*")
public class OnlineStoreInventoryApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(OnlineStoreInventoryApplication.class, args);
    }
    
}
