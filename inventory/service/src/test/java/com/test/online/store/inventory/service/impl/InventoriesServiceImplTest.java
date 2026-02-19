package com.test.online.store.inventory.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

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
import com.test.online.store.inventory.domain.document.Inventory;
import com.test.online.store.inventory.domain.repository.InventoriesRepository;
import com.test.online.store.inventory.model.InventoryBean;
import com.test.online.store.inventory.service.integration.ProductsIntegrationService;
import com.test.online.store.inventory.service.mapper.InventoriesMapper;
import com.test.online.store.product.model.ProductBean;

@ExtendWith(MockitoExtension.class)
class InventoriesServiceImplTest {

    @Mock
    private InventoriesRepository inventoriesRepository;

    @Mock
    private ProductsIntegrationService productsIntegrationService;

    @Mock
    private InventoriesMapper mockedInventoriesMapper;

    @InjectMocks
    private InventoriesServiceImpl inventoriesService;

    @BeforeEach
    void setUp() {
        final InventoriesMapper mapper = Mappers.getMapper(InventoriesMapper.class);
        ReflectionTestUtils.setField(inventoriesService, "inventoriesMapper", mapper);
    }

    @Test
    void shouldGetByProductSlug() {
        // Arrange
        final String productSlug = "gaming-mouse";
        final ProductBean product = ProductBean.builder()
                .name("Gaming Mouse")
                .price(BigDecimal.valueOf(10))
                .description("Wireless")
                .build();

        final Inventory inventory = new Inventory();
        inventory.setProductSlug(productSlug);
        inventory.setQuantity(7L);

        when(productsIntegrationService.getBySlug(productSlug)).thenReturn(product);
        when(inventoriesRepository.findByProductSlug(productSlug)).thenReturn(Optional.of(inventory));

        // Act
        final InventoryBean result = inventoriesService.getByProductSlug(productSlug);

        // Assert
        assertEquals(7L, result.getQuantity());
    }

    @Test
    void shouldThrowWhenGetByProductSlugAndProductNotFound() {
        // Arrange
        when(productsIntegrationService.getBySlug("missing")).thenReturn(null);

        // Act
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> inventoriesService.getByProductSlug("missing"));

        // Assert
        assertSame(CommonValidationError.ITEM_NOT_FOUND, exception.getError());
    }

    @Test
    void shouldThrowWhenGetByProductSlugAndInventoryNotFound() {
        // Arrange
        final String productSlug = "gaming-mouse";
        final ProductBean product = ProductBean.builder()
                .name("Gaming Mouse")
                .price(BigDecimal.valueOf(10))
                .description("Wireless")
                .build();

        when(productsIntegrationService.getBySlug(productSlug)).thenReturn(product);
        when(inventoriesRepository.findByProductSlug(productSlug)).thenReturn(Optional.empty());

        // Act
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> inventoriesService.getByProductSlug(productSlug));

        // Assert
        assertSame(CommonValidationError.ITEM_NOT_FOUND, exception.getError());
    }

    @Test
    void shouldUpsertByProductSlugWhenInventoryExists() {
        // Arrange
        final String productSlug = "gaming-mouse";
        final ProductBean product = ProductBean.builder()
                .name("Gaming Mouse")
                .price(BigDecimal.valueOf(10))
                .description("Wireless")
                .build();
        final InventoryBean request = new InventoryBean(8L);

        final Inventory existing = new Inventory();
        existing.setProductSlug(productSlug);
        existing.setQuantity(3L);

        when(productsIntegrationService.getBySlug(productSlug)).thenReturn(product);
        when(inventoriesRepository.findByProductSlug(productSlug)).thenReturn(Optional.of(existing));
        when(inventoriesRepository.save(any(Inventory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        final InventoryBean result = inventoriesService.upsertByProductSlug(productSlug, request);

        // Assert
        assertEquals(8L, result.getQuantity());
        verify(inventoriesRepository).save(any(Inventory.class));
    }

    @Test
    void shouldUpsertByProductSlugWhenInventoryDoesNotExist() {
        // Arrange
        final String productSlug = "gaming-mouse";
        final ProductBean product = ProductBean.builder()
                .name("Gaming Mouse")
                .price(BigDecimal.valueOf(10))
                .description("Wireless")
                .build();
        final InventoryBean request = new InventoryBean(4L);

        when(productsIntegrationService.getBySlug(productSlug)).thenReturn(product);
        when(inventoriesRepository.findByProductSlug(productSlug)).thenReturn(Optional.empty());
        when(inventoriesRepository.save(any(Inventory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        final InventoryBean result = inventoriesService.upsertByProductSlug(productSlug, request);

        // Assert
        assertEquals(4L, result.getQuantity());
    }

    @Test
    void shouldThrowWhenUpsertByProductSlugAndProductNotFound() {
        // Arrange
        when(productsIntegrationService.getBySlug("missing")).thenReturn(null);

        // Act
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> inventoriesService.upsertByProductSlug("missing", new InventoryBean(2L)));

        // Assert
        assertSame(CommonValidationError.ITEM_NOT_FOUND, exception.getError());
    }
}
