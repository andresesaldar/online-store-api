package com.test.online.store.inventory.service.integration;

import com.test.online.store.product.model.ProductBean;

public interface ProductsIntegrationService {
    ProductBean getBySlug(String slug);
}
