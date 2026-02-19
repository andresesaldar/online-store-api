package com.test.online.store.inventory.service.error;

import com.test.online.store.common.service.error.ValidationError;
import com.test.online.store.common.service.error.ValidationException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InventoryValidationError implements ValidationError {
    INSUFFICIENT_STOCK("I0001", "Insufficient stock");

    private final String code;
    private final String message;

    @Override
    public ValidationException exception() {
        return new ValidationException(this);
    }
}
