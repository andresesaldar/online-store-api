package com.test.online.store.inventory.service;

import com.test.online.store.inventory.model.BillBean;

public interface BillsService {
    BillBean createByProductSlug(String productSlug, BillBean bill);
}
