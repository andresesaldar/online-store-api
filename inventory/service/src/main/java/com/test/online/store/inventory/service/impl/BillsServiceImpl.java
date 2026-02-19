package com.test.online.store.inventory.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.online.store.common.service.error.CommonValidationError;
import com.test.online.store.inventory.domain.document.Bill;
import com.test.online.store.inventory.domain.repository.BillsRepository;
import com.test.online.store.inventory.model.InventoryBean;
import com.test.online.store.inventory.service.InventoriesService;
import com.test.online.store.inventory.service.BillsService;
import com.test.online.store.inventory.service.error.InventoryValidationError;
import com.test.online.store.inventory.service.integration.ProductsIntegrationService;
import com.test.online.store.inventory.service.mapper.BillsMapper;
import com.test.online.store.inventory.model.BillBean;
import com.test.online.store.product.model.ProductBean;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillsServiceImpl implements BillsService {

    private final BillsRepository billsRepository;
    private final InventoriesService inventoriesService;
    private final BillsMapper billsMapper;
    private final ProductsIntegrationService productsIntegrationService;

    @Override
    @Transactional
    public BillBean createByProductSlug(String productSlug, BillBean bill) {
        final ProductBean product = productsIntegrationService.getBySlug(productSlug);

        if (product == null) {
            throw CommonValidationError.ITEM_NOT_FOUND.exception();
        }

        validateAndUpdateInventory(productSlug, bill.getQuantity());
        
        final Bill billDocument = billsMapper.toDocument(bill, productSlug);
        
        billDocument.setTotalPrice(calculateTotalPrice(product.getPrice(), bill.getQuantity()));

        final Bill savedBill = billsRepository.save(billDocument);

        return billsMapper.toBean(savedBill);
    }

    private void validateAndUpdateInventory(String productSlug, Long requestedQuantity) {
        final InventoryBean currentInventory = inventoriesService.getByProductSlug(productSlug);
        if (currentInventory.getQuantity() < requestedQuantity) {
            throw InventoryValidationError.INSUFFICIENT_STOCK.exception();
        }

        final long updatedQuantity = currentInventory.getQuantity() - requestedQuantity;
        inventoriesService.upsertByProductSlug(productSlug, new InventoryBean(updatedQuantity));
    }

    private BigDecimal calculateTotalPrice(BigDecimal productPrice, Long quantity) {
        return productPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
