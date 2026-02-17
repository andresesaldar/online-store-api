package com.test.online.store.product.service;

import com.test.online.store.product.service.model.ProductBean;

public interface ProductsService {

    ProductBean create(ProductBean productBean);
    ProductBean getBySlug(String slug);
    // TODO Get all products with pagination and sorting
    
}
