package com.test.online.store.product.web;

import com.test.online.store.common.service.constant.ResponseType;
import com.test.online.store.common.service.factory.ResponseFactory;
import com.test.online.store.common.service.model.PageResult;
import com.test.online.store.common.service.model.Response;
import com.test.online.store.common.web.route.ProductRoute;
import com.test.online.store.common.web.route.CommonRoute;
import com.test.online.store.product.service.ProductsService;
import com.test.online.store.product.service.model.ProductBean;

import jakarta.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping(value = ProductRoute.BASE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProductsController {

    private final ResponseFactory responseFactory;
    private final ProductsService productsService;

    @GetMapping
    public Response<PageResult<ProductBean>> getAll(
        @RequestParam(value = "page", required = false) Integer page,
        @RequestParam(value = "size", required = false) Integer size) {

        final PageResult<ProductBean> products = productsService.getAll(page, size);
        
        return responseFactory.construct(ResponseType.SUCCESS, products);
    }

    @GetMapping(CommonRoute.SLUG_PARAM)
    public Response<ProductBean> getBySlug(@PathVariable("slug") String slug) {

        final ProductBean product = productsService.getBySlug(slug);

        return responseFactory.construct(ResponseType.SUCCESS, product);
    }

    @PostMapping
    public Response<ProductBean> create(@Valid @RequestBody ProductBean productBean) {
        
        final ProductBean createdProduct = productsService.create(productBean);
        
        return responseFactory.construct(ResponseType.SUCCESS, createdProduct);
    }
}
