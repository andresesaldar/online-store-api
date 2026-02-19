package com.test.online.store.common.service.discovery;

import org.springframework.cloud.client.ServiceInstance;

public interface ServiceInstanceResolver {
    ServiceInstance resolve(String serviceId);
}
