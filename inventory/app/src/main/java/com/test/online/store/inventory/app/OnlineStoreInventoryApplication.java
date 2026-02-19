package com.test.online.store.inventory.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.test.online.store.common.domain.configuration.JpaAuditingConfiguration;

@SpringBootApplication(
    scanBasePackages = {"com.test.online.store.inventory.*", "com.test.online.store.common.*"},
    exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
@ComponentScan(
    basePackages = {"com.test.online.store.inventory.*", "com.test.online.store.common.*"},
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JpaAuditingConfiguration.class))
@ConfigurationPropertiesScan(basePackages = {"com.test.online.store.inventory.*", "com.test.online.store.common.*"})
@EnableMongoRepositories(basePackages = {"com.test.online.store.inventory.*", "com.test.online.store.common.*"})
@EntityScan(basePackages = {"com.test.online.store.inventory.*", "com.test.online.store.common.*"})
@EnableDiscoveryClient
public class OnlineStoreInventoryApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(OnlineStoreInventoryApplication.class, args);
    }
    
}
