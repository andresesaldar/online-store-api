package com.test.online.store.common.web.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.test.online.store.common.service.model.ErrorResponse;

@RestControllerAdvice
@Order(2)
public class CommonExceptionHandler {

    private static final String INTERNAL_ERROR_MESSAGE = "Internal error. Contact with administrator";

    private final Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponse handleException(BadCredentialsException exception) {
        return ErrorResponse.builder()
            .message(exception.getMessage())
            .build();
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception exception) {
        logger.error("Internal error.", exception);

        return ErrorResponse.builder()
            .message(INTERNAL_ERROR_MESSAGE)
            .build();
    }
    

}
