package com.test.online.store.inventory.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.test.online.store.inventory.domain.document.Bill;
import com.test.online.store.inventory.model.BillBean;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BillsMapper {

        BillBean toBean(Bill bill);

        @Mapping(target = "productSlug", source = "productSlug")
        Bill toDocument(BillBean bill, String productSlug);

}
