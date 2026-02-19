package com.test.online.store.product.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import com.test.online.store.common.service.model.PageResult;
import com.test.online.store.product.domain.model.Product;
import com.test.online.store.product.model.ProductBean;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductsMapper {

    ProductBean toProduct(Product product);

    Product toEntity(ProductBean productBean);

    @Mapping(target = "page", source = "number")
    PageResult<ProductBean> toPageResult(Page<Product> page);


}
