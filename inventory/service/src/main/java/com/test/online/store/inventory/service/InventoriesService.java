package com.test.online.store.inventory.service;

import com.test.online.store.inventory.model.InventoryBean;

public interface InventoriesService {

    InventoryBean getByProductSlug(String productSlug);
    InventoryBean upsertByProductSlug(String productSlug, InventoryBean inventory);
    
}
