package com.test.online.store.common.service;

import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {

    Authentication authenticate(HttpServletRequest request);

}
