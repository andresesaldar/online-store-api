package com.test.online.store.product.domain.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.test.online.store.product.domain.model.Product;

public interface ProductsRepository extends PagingAndSortingRepository<Product, Long>, CrudRepository<Product, Long> {
    Optional<Product> findBySlug(String slug);
}
