package com.test.online.store.product.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.test.online.store.common.service.constant.ResponseType;
import com.test.online.store.common.service.factory.ResponseFactory;
import com.test.online.store.common.service.model.PageResult;
import com.test.online.store.common.service.model.Response;
import com.test.online.store.common.service.model.SuccessResponse;
import com.test.online.store.product.model.ProductBean;
import com.test.online.store.product.service.ProductsService;

@ExtendWith(MockitoExtension.class)
class ProductsControllerTest {

    @Mock
    private ResponseFactory responseFactory;

    @Mock
    private ProductsService productsService;

    @InjectMocks
    private ProductsController productsController;

    @Test
    void shouldGetBySlug() {
        // Arrange
        final ProductBean product = ProductBean.builder()
                .name("Gaming Mouse")
                .price(BigDecimal.valueOf(89.90))
                .description("Wireless")
                .build();
        when(productsService.getBySlug("gaming-mouse")).thenReturn(product);
        when(responseFactory.construct(ResponseType.SUCCESS, product))
                .thenReturn(SuccessResponse.<ProductBean>builder().data(product).build());

        // Act
        final Response<ProductBean> response = productsController.getBySlug("gaming-mouse");

        // Assert
        assertEquals("gaming-mouse", ((SuccessResponse<ProductBean>) response).getData().getSlug());
    }

    @Test
    void shouldCreate() {
        // Arrange
        final ProductBean product = ProductBean.builder()
                .name("Gaming Mouse")
                .price(BigDecimal.valueOf(89.90))
                .description("Wireless")
                .build();
        when(productsService.create(product)).thenReturn(product);
        when(responseFactory.construct(ResponseType.SUCCESS, product))
                .thenReturn(SuccessResponse.<ProductBean>builder().data(product).build());

        // Act
        final Response<ProductBean> response = productsController.create(product);

        // Assert
        assertEquals("gaming-mouse", ((SuccessResponse<ProductBean>) response).getData().getSlug());
    }

    @Test
    void shouldGetAll() {
        // Arrange
        final PageResult<ProductBean> pageResult = new PageResult<>(0, 10, true, true);

        when(productsService.getAll(0, 10)).thenReturn(pageResult);
        when(responseFactory.construct(eq(ResponseType.SUCCESS), eq(pageResult)))
                .thenReturn(SuccessResponse.<PageResult<ProductBean>>builder().data(pageResult).build());

        // Act
        final Response<PageResult<ProductBean>> response = productsController.getAll(0, 10);

        // Assert
        assertEquals(0, ((SuccessResponse<PageResult<ProductBean>>) response).getData().getPage());
    }
}
