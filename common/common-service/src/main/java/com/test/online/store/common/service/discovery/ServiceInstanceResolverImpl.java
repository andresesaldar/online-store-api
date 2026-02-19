package com.test.online.store.common.service.discovery;

import java.util.List;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import com.test.online.store.common.service.error.CommonValidationError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceInstanceResolverImpl implements ServiceInstanceResolver {

    private final DiscoveryClient discoveryClient;

    @Override
    public ServiceInstance resolve(String serviceId) {
        final List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceId);

        if (serviceInstances == null || serviceInstances.isEmpty()) {
            throw CommonValidationError.SERVICE_INSTANCE_NOT_FOUND.exception();
        }

        return serviceInstances.get(0);
    }
}
