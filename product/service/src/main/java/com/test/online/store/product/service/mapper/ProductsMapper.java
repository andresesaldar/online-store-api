package com.test.online.store.product.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.test.online.store.product.domain.model.Product;
import com.test.online.store.product.service.model.ProductBean;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductsMapper {

    ProductBean toProduct(Product product);

    Product toEntity(ProductBean productBean);

}
