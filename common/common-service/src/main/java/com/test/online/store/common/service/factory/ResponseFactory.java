package com.test.online.store.common.service.factory;

import com.test.online.store.common.service.constant.ResponseType;
import com.test.online.store.common.service.model.Response;

public interface ResponseFactory {
    <T> Response<T> construct(ResponseType type, T data);
}
