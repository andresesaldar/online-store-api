package com.test.online.store.common.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import com.test.online.store.common.service.AuthenticationService;
import com.test.online.store.common.service.model.ApiKeyAuthentication;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ApiKeyAuthenticationService implements AuthenticationService {

    private static final String API_KEY_HEADER_NAME = "X-API-KEY";

    @Value("${app.security.api-key}")
    private String API_KEY;

    @Override
    public Authentication authenticate(HttpServletRequest request) {
        final String apiKey = request.getHeader(API_KEY_HEADER_NAME);

        if(!API_KEY.equals(apiKey)) {
            throw new BadCredentialsException("Invalid API Key");
        }

        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);


    }

}
