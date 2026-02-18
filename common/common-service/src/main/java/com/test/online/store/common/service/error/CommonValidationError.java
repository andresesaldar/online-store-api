package com.test.online.store.common.service.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonValidationError implements ValidationError {
    ITEM_ALREADY_EXISTS("C0001", "Item already exists"),
    ITEM_NOT_FOUND("C0002", "Item was not found");

    private final String code;
    private final String message;

    @Override
    public ValidationException exception() {
        return new ValidationException(this);
    }   

}
