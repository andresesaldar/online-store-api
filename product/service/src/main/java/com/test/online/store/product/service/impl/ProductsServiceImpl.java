package com.test.online.store.product.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.online.store.common.service.builder.PageableBuilder;
import com.test.online.store.common.service.error.CommonValidationError;
import com.test.online.store.common.service.error.ValidationError;
import com.test.online.store.common.service.model.PageResult;
import com.test.online.store.product.domain.model.Product;
import com.test.online.store.product.domain.repository.ProductsRepository;
import com.test.online.store.product.service.ProductsService;
import com.test.online.store.product.service.mapper.ProductsMapper;
import com.test.online.store.product.service.model.ProductBean;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {

    private final ProductsRepository productsRepository;
    private final ProductsMapper productsMapper;
    private final PageableBuilder pageableBuilder;

    @Override
    @Transactional
    public ProductBean create(ProductBean productBean) {
        final Product product = productsMapper.toEntity(productBean);

        final Optional<Product> existingProduct = productsRepository.findBySlug(product.getSlug());
        if (existingProduct.isPresent()) {
            throw CommonValidationError.ITEM_ALREADY_EXISTS.exception();
        }

        final Product savedProduct = productsRepository.save(product);
        return productsMapper.toProduct(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductBean getBySlug(String slug) {
        final Optional<Product> product = productsRepository.findBySlug(slug);
        return product.map(productsMapper::toProduct)
                .orElseThrow(() -> CommonValidationError.ITEM_NOT_FOUND.exception());
    }

    @Override
    @Transactional(readOnly = true)
    public PageResult<ProductBean> getAll(@Nullable Integer page, @Nullable Integer size) {
        final Pageable pageable = pageableBuilder.page(page).size(size).build();
        final Page<Product> productsPageable = productsRepository.findAll(pageable);
        return productsMapper.toPageResult(productsPageable);
    }

}
