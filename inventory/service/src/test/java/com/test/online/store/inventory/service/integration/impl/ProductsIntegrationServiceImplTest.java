package com.test.online.store.inventory.service.integration.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.net.URI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.online.store.common.service.discovery.ServiceInstanceResolver;
import com.test.online.store.common.service.model.SuccessResponse;
import com.test.online.store.product.model.ProductBean;

@ExtendWith(MockitoExtension.class)
class ProductsIntegrationServiceImplTest {

    @Mock
    private ServiceInstanceResolver serviceInstanceResolver;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ServiceInstance serviceInstance;

    @InjectMocks
    private ProductsIntegrationServiceImpl productsIntegrationService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(productsIntegrationService, "apiKey", "test-key");
        ReflectionTestUtils.setField(productsIntegrationService, "objectMapper", new ObjectMapper());
    }

    @Test
    void shouldGetBySlug() {
        // Arrange
        final ProductBean product = ProductBean.builder()
                .name("Gaming Mouse")
                .price(BigDecimal.valueOf(10))
                .description("Wireless")
                .build();
        final SuccessResponse<ProductBean> successResponse = SuccessResponse.<ProductBean>builder()
                .data(product)
                .build();

        when(serviceInstanceResolver.resolve("online-store-product")).thenReturn(serviceInstance);
        when(serviceInstance.getUri()).thenReturn(URI.create("http://product-app:8080"));
        when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class),
                eq("gaming-mouse")))
                .thenReturn(new ResponseEntity<>(successResponse, HttpStatus.OK));

        // Act
        final ProductBean result = productsIntegrationService.getBySlug("gaming-mouse");

        // Assert
        assertEquals(product.getSlug(), result.getSlug());
    }

    @Test
    void shouldReturnNullWhenGetBySlugAndItemNotFoundCode() {
        // Arrange
        final String body = "{\"code\":\"C0002\",\"message\":\"Item was not found\",\"ok\":false}";
        final HttpClientErrorException.BadRequest badRequest = mock(HttpClientErrorException.BadRequest.class);
        when(badRequest.getResponseBodyAsString()).thenReturn(body);

        when(serviceInstanceResolver.resolve("online-store-product")).thenReturn(serviceInstance);
        when(serviceInstance.getUri()).thenReturn(URI.create("http://product-app:8080"));
        when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class),
                eq("missing-product")))
                .thenThrow(badRequest);

        // Act
        final ProductBean result = productsIntegrationService.getBySlug("missing-product");

        // Assert
        assertNull(result);
    }

    @Test
    void shouldThrowWhenGetBySlugAndBadRequestWithDifferentCode() {
        // Arrange
        final String body = "{\"code\":\"C9999\",\"message\":\"Unexpected\",\"ok\":false}";
        final HttpClientErrorException.BadRequest badRequest = mock(HttpClientErrorException.BadRequest.class);
        when(badRequest.getResponseBodyAsString()).thenReturn(body);

        when(serviceInstanceResolver.resolve("online-store-product")).thenReturn(serviceInstance);
        when(serviceInstance.getUri()).thenReturn(URI.create("http://product-app:8080"));
        when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class),
                eq("broken-product")))
                .thenThrow(badRequest);

        // Act / Assert
        assertThrows(HttpClientErrorException.BadRequest.class,
                () -> productsIntegrationService.getBySlug("broken-product"));
    }
}
