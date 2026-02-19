package com.test.online.store.inventory.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.test.online.store.inventory.domain.document.Inventory;
import com.test.online.store.inventory.model.InventoryBean;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InventoriesMapper {
        
        InventoryBean toBean(Inventory inventory);

        @Mapping(target = "productSlug", source = "productSlug")
        Inventory toDocument(InventoryBean inventory, String productSlug);

        Inventory merge(InventoryBean inventory, @MappingTarget Inventory target);

}
