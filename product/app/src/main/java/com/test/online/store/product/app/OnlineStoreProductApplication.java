package com.test.online.store.product.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.test.online.store.common.domain.configuration.MongoAuditingConfiguration;

@SpringBootApplication(
    scanBasePackages = {"com.test.online.store.product.*", "com.test.online.store.common.*"},
    exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
@ComponentScan(
    basePackages = {"com.test.online.store.product.*", "com.test.online.store.common.*"},
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = MongoAuditingConfiguration.class))
@ConfigurationPropertiesScan(basePackages = {"com.test.online.store.product.*", "com.test.online.store.common.*"})
@EnableJpaRepositories(basePackages = {"com.test.online.store.product.*", "com.test.online.store.common.*"})
@EntityScan(basePackages = {"com.test.online.store.product.*", "com.test.online.store.common.*"})
@EnableDiscoveryClient
public class OnlineStoreProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineStoreProductApplication.class, args);
    }
    
}
