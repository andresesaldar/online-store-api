package com.test.online.store.common.service.error;

public interface ValidationError {
    String getMessage();
    String getCode();
    ValidationException exception();
}
