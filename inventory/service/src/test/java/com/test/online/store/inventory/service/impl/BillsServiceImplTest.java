package com.test.online.store.inventory.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.test.online.store.common.service.error.CommonValidationError;
import com.test.online.store.common.service.error.ValidationException;
import com.test.online.store.inventory.domain.document.Bill;
import com.test.online.store.inventory.domain.repository.BillsRepository;
import com.test.online.store.inventory.model.BillBean;
import com.test.online.store.inventory.model.InventoryBean;
import com.test.online.store.inventory.service.InventoriesService;
import com.test.online.store.inventory.service.error.InventoryValidationError;
import com.test.online.store.inventory.service.integration.ProductsIntegrationService;
import com.test.online.store.inventory.service.mapper.BillsMapper;
import com.test.online.store.product.model.ProductBean;

@ExtendWith(MockitoExtension.class)
class BillsServiceImplTest {

    @Mock
    private BillsRepository billsRepository;

    @Mock
    private InventoriesService inventoriesService;

    @Mock
    private ProductsIntegrationService productsIntegrationService;

    @Mock
    private BillsMapper mockedBillsMapper;

    @InjectMocks
    private BillsServiceImpl billsService;

    @BeforeEach
    void setUp() {
        final BillsMapper mapper = Mappers.getMapper(BillsMapper.class);
        ReflectionTestUtils.setField(billsService, "billsMapper", mapper);
    }

    @Test
    void shouldCreateByProductSlug() {
        // Arrange
        final String productSlug = "gaming-mouse";
        final BillBean request = new BillBean(2L, null, null);
        final ProductBean product = ProductBean.builder()
                .name("Gaming Mouse")
                .price(BigDecimal.valueOf(10))
                .description("Wireless")
                .build();

        final InventoryBean currentInventory = new InventoryBean(5L);
        final Bill savedDocument = new Bill();
        savedDocument.setProductSlug(productSlug);
        savedDocument.setQuantity(2L);
        savedDocument.setTotalPrice(BigDecimal.valueOf(20));

        when(productsIntegrationService.getBySlug(productSlug)).thenReturn(product);
        when(inventoriesService.getByProductSlug(productSlug)).thenReturn(currentInventory);
        when(billsRepository.save(any(Bill.class))).thenReturn(savedDocument);

        // Act
        final BillBean created = billsService.createByProductSlug(productSlug, request);

        // Assert
        assertEquals(BigDecimal.valueOf(20), created.getTotalPrice());
        verify(inventoriesService).upsertByProductSlug(eq(productSlug), eq(new InventoryBean(3L)));
    }

    @Test
    void shouldThrowWhenCreateByProductSlugAndProductNotFound() {
        // Arrange
        final BillBean request = new BillBean(1L, null, LocalDateTime.now());
        when(productsIntegrationService.getBySlug("missing")).thenReturn(null);

        // Act
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> billsService.createByProductSlug("missing", request));

        // Assert
        assertSame(CommonValidationError.ITEM_NOT_FOUND, exception.getError());
    }

    @Test
    void shouldThrowWhenCreateByProductSlugAndInsufficientStock() {
        // Arrange
        final String productSlug = "gaming-mouse";
        final BillBean request = new BillBean(3L, null, null);
        final ProductBean product = ProductBean.builder()
                .name("Gaming Mouse")
                .price(BigDecimal.valueOf(10))
                .description("Wireless")
                .build();

        when(productsIntegrationService.getBySlug(productSlug)).thenReturn(product);
        when(inventoriesService.getByProductSlug(productSlug)).thenReturn(new InventoryBean(2L));

        // Act
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> billsService.createByProductSlug(productSlug, request));

        // Assert
        assertSame(InventoryValidationError.INSUFFICIENT_STOCK, exception.getError());
    }
}
