package com.test.online.store.product.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import com.test.online.store.common.service.builder.PageableBuilder;
import com.test.online.store.common.service.error.CommonValidationError;
import com.test.online.store.common.service.error.ValidationException;
import com.test.online.store.common.service.model.PageResult;
import com.test.online.store.product.domain.model.Product;
import com.test.online.store.product.domain.repository.ProductsRepository;
import com.test.online.store.product.model.ProductBean;
import com.test.online.store.product.service.mapper.ProductsMapper;

@ExtendWith(MockitoExtension.class)
class ProductsServiceImplTest {

    @Mock
    private ProductsRepository productsRepository;

    @Mock
    private PageableBuilder pageableBuilder;

    @Mock
    private ProductsMapper mockedProductsMapper;

    @InjectMocks
    private ProductsServiceImpl productsService;

    @BeforeEach
    void setUp() {
        final ProductsMapper mapper = Mappers.getMapper(ProductsMapper.class);
        ReflectionTestUtils.setField(productsService, "productsMapper", mapper);
    }

    @Test
    void shouldCreate() {
        // Arrange
        final ProductBean productBean = ProductBean.builder()
                .name("Gaming Mouse")
                .price(BigDecimal.valueOf(89.90))
                .description("Wireless gaming mouse")
                .build();

        when(productsRepository.findBySlug(productBean.getSlug())).thenReturn(Optional.empty());
        when(productsRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        final ProductBean created = productsService.create(productBean);

        // Assert
        assertEquals(productBean.getSlug(), created.getSlug());
        verify(productsRepository).save(any(Product.class));
    }

    @Test
    void shouldThrowWhenCreateAndItemAlreadyExists() {
        // Arrange
        final ProductBean productBean = ProductBean.builder()
                .name("Gaming Mouse")
                .price(BigDecimal.valueOf(89.90))
                .description("Wireless gaming mouse")
                .build();

        final Product existing = new Product();
        existing.setSlug(productBean.getSlug());
        when(productsRepository.findBySlug(productBean.getSlug())).thenReturn(Optional.of(existing));

        // Act
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> productsService.create(productBean));

        // Assert
        assertSame(CommonValidationError.ITEM_ALREADY_EXISTS, exception.getError());
    }

    @Test
    void shouldGetBySlug() {
        // Arrange
        final Product product = new Product();
        product.setSlug("gaming-mouse");
        product.setName("Gaming Mouse");
        product.setPrice(BigDecimal.valueOf(89.90));
        product.setDescription("Wireless gaming mouse");

        when(productsRepository.findBySlug("gaming-mouse")).thenReturn(Optional.of(product));

        // Act
        final ProductBean found = productsService.getBySlug("gaming-mouse");

        // Assert
        assertEquals(product.getSlug(), found.getSlug());
    }

    @Test
    void shouldThrowWhenGetBySlugAndItemNotFound() {
        // Arrange
        when(productsRepository.findBySlug("missing-product")).thenReturn(Optional.empty());

        // Act
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> productsService.getBySlug("missing-product"));

        // Assert
        assertSame(CommonValidationError.ITEM_NOT_FOUND, exception.getError());
    }

    @Test
    void shouldGetAll() {
        // Arrange
        final int page = 0;
        final int size = 2;
        final Pageable pageable = PageRequest.of(page, size);

        final Product first = new Product();
        first.setSlug("gaming-mouse");
        first.setName("Gaming Mouse");
        first.setPrice(BigDecimal.valueOf(89.90));

        final Product second = new Product();
        second.setSlug("mechanical-keyboard");
        second.setName("Mechanical Keyboard");
        second.setPrice(BigDecimal.valueOf(129.90));

        when(pageableBuilder.page(page)).thenReturn(pageableBuilder);
        when(pageableBuilder.size(size)).thenReturn(pageableBuilder);
        when(pageableBuilder.build()).thenReturn(pageable);
        when(productsRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(List.of(first, second), pageable, 2));

        // Act
        final PageResult<ProductBean> result = productsService.getAll(page, size);

        // Assert
        assertEquals(2, result.getContent().size());
    }
}
