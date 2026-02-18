package com.test.online.store.product.service;

import com.test.online.store.common.service.model.PageResult;
import com.test.online.store.product.service.model.ProductBean;

public interface ProductsService {

    ProductBean create(ProductBean productBean);
    ProductBean getBySlug(String slug);
    PageResult<ProductBean> getAll(Integer page, Integer size);    
    
}
