package com.test.online.store.product.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.test.online.store.product.domain.model.Product;
import com.test.online.store.product.domain.repository.ProductsRepository;
import com.test.online.store.product.service.ProductsService;
import com.test.online.store.product.service.mapper.ProductsMapper;
import com.test.online.store.product.service.model.ProductBean;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {

    private final ProductsRepository productsRepository;
    private final ProductsMapper productsMapper;

    @Override
    @Transactional
    public ProductBean create(ProductBean productBean) {
        final Product product = productsMapper.toEntity(productBean);
        final Product savedProduct = productsRepository.save(product);
        return productsMapper.toProduct(savedProduct);
    }

    @Override
    public ProductBean getBySlug(String slug) {
        final Optional<Product> product = productsRepository.findBySlug(slug);
        return product.map(productsMapper::toProduct)
                // TODO Replace with orElseThrow and create a custom exception
                .orElse(null);
    }

}
