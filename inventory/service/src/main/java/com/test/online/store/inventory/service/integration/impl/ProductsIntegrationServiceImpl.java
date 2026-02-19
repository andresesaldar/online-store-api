package com.test.online.store.inventory.service.integration.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.test.online.store.common.service.discovery.ServiceInstanceResolver;
import com.test.online.store.common.service.model.ErrorResponse;
import com.test.online.store.common.service.model.SuccessResponse;
import com.test.online.store.common.web.route.CommonRoute;
import com.test.online.store.common.web.route.ProductRoute;
import com.test.online.store.inventory.service.integration.ProductsIntegrationService;
import com.test.online.store.product.model.ProductBean;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductsIntegrationServiceImpl implements ProductsIntegrationService {

    private static final String PRODUCT_APPLICATION = "online-store-product";
    private static final String API_KEY_HEADER_NAME = "X-API-KEY";
    private static final String ITEM_NOT_FOUND_CODE = "C0002";

    private final ServiceInstanceResolver serviceInstanceResolver;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${app.security.api-key}")
    private String apiKey;

    @Override
    public ProductBean getBySlug(String slug) {
        final String url = serviceInstanceResolver
                            .resolve(PRODUCT_APPLICATION)
                            .getUri()
                            .toString()
                            .concat(ProductRoute.BASE)
                            .concat(CommonRoute.SLUG_PARAM);
        try {
            final SuccessResponse<ProductBean> response =
                restTemplate
                    .exchange(
                        url,
                        HttpMethod.GET,
                        buildApiKeyRequestEntity(),
                        new ParameterizedTypeReference<SuccessResponse<ProductBean>>() {}, slug)
                    .getBody();

            return response != null ? response.getData() : null;
        } catch (HttpClientErrorException.BadRequest ex) {
            if (isItemNotFound(ex.getResponseBodyAsString())) {
                return null;
            }
            throw ex;
        }
    }

    private HttpEntity<Void> buildApiKeyRequestEntity() {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(API_KEY_HEADER_NAME, apiKey);
        return new HttpEntity<>(headers);
    }

    private boolean isItemNotFound(String responseBody) {
        try {
            final ErrorResponse errorResponse = objectMapper.readValue(responseBody, ErrorResponse.class);
            return errorResponse != null && ITEM_NOT_FOUND_CODE.equals(errorResponse.getCode());
        } catch (Exception ex) {
            return false;
        }
    }
}
