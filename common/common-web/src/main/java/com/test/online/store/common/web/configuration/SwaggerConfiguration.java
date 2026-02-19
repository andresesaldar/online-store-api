package com.test.online.store.common.web.configuration;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@SecurityScheme(
    type = SecuritySchemeType.APIKEY,
    paramName = "X-API-KEY",
    name = "API_KEY",
    in = SecuritySchemeIn.HEADER)
public class SwaggerConfiguration {

}
