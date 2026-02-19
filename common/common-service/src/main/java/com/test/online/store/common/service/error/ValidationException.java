package com.test.online.store.common.service.error;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
    final ValidationError error;

    public ValidationException(ValidationError error) {
        super(error.getMessage());
        this.error = error;
    }
    
}