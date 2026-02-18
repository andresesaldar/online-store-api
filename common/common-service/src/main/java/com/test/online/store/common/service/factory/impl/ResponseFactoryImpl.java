package com.test.online.store.common.service.factory.impl;

import org.springframework.stereotype.Component;

import com.test.online.store.common.service.constant.ResponseType;
import com.test.online.store.common.service.factory.ResponseFactory;
import com.test.online.store.common.service.model.Response;
import com.test.online.store.common.service.model.SuccessResponse;

@Component
public class ResponseFactoryImpl implements ResponseFactory {

    @Override
    public <T> Response<T> construct(ResponseType type, T data) {
        switch (type) {
            case SUCCESS:
                return SuccessResponse.<T>builder()
                        .data(data)
                        .build();
            default:
                return SuccessResponse.<T>builder()
                        .data(data)
                        .build();
        }
    }
    
}
