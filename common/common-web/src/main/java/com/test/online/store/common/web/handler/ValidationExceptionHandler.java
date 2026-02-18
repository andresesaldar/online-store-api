package com.test.online.store.common.web.handler;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.test.online.store.common.service.error.ValidationException;
import com.test.online.store.common.service.model.ErrorResponse;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
@Order(1)
public class ValidationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ErrorResponse handleValidationException(
            ValidationException ex) {

        return ErrorResponse.builder()
            .code(ex.getError().getCode())
            .message(ex.getError().getMessage())
            .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleConstraintViolation(
            ConstraintViolationException ex) {

        final StringBuilder errors = new StringBuilder();

        ex.getConstraintViolations()
            .forEach(violation -> {
                errors.append(violation.getPropertyPath().toString());
                errors.append(": ");
                errors.append(violation.getMessage());
                errors.append(". ");
            });

        return ErrorResponse.builder()
            .message(errors.toString())
            .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        final StringBuilder errors = new StringBuilder();

        ex.getBindingResult()
            .getFieldErrors()
            .forEach(error -> {
                errors.append(error.getField());
                errors.append(": ");
                errors.append(error.getDefaultMessage());
                errors.append(". ");
            });

        return ErrorResponse.builder()
            .message(errors.toString())
            .build();
    }

}
