package com.test.online.store.inventory.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.online.store.common.service.error.CommonValidationError;
import com.test.online.store.inventory.domain.document.Inventory;
import com.test.online.store.inventory.domain.repository.InventoriesRepository;
import com.test.online.store.inventory.model.InventoryBean;
import com.test.online.store.inventory.service.InventoriesService;
import com.test.online.store.inventory.service.integration.ProductsIntegrationService;
import com.test.online.store.inventory.service.mapper.InventoriesMapper;
import com.test.online.store.product.model.ProductBean;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoriesServiceImpl implements InventoriesService {

    private final InventoriesRepository inventoriesRepository;
    private final InventoriesMapper inventoriesMapper;
    private final ProductsIntegrationService productsIntegrationService;

    @Override
    @Transactional(readOnly = true)
    public InventoryBean getByProductSlug(String productSlug) {
        final ProductBean product = productsIntegrationService.getBySlug(productSlug);
        
        if (product == null) {
            throw CommonValidationError.ITEM_NOT_FOUND.exception();
        }

        final Optional<Inventory> inventory = inventoriesRepository.findByProductSlug(productSlug);
        return inventory
                .map(inventoriesMapper::toBean)
                .orElseThrow(() -> CommonValidationError.ITEM_NOT_FOUND.exception());
    }

    @Override
    @Transactional
    public InventoryBean upsertByProductSlug(String productSlug, InventoryBean inventory) {
        final ProductBean product = productsIntegrationService.getBySlug(productSlug);

        if (product == null) {
            throw CommonValidationError.ITEM_NOT_FOUND.exception();
        }

        final Optional<Inventory> existingInventory = inventoriesRepository.findByProductSlug(productSlug);
        
        final Inventory inventoryDocument;

        if (existingInventory.isPresent()) {
            inventoryDocument = inventoriesMapper.merge(inventory, existingInventory.get());
        } else {
            inventoryDocument = inventoriesMapper.toDocument(inventory, productSlug);
        }

        final Inventory savedInventory = inventoriesRepository.save(inventoryDocument);
        return inventoriesMapper.toBean(savedInventory);
    }
    
}
