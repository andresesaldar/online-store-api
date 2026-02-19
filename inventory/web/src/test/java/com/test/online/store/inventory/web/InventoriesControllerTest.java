package com.test.online.store.inventory.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.test.online.store.common.service.constant.ResponseType;
import com.test.online.store.common.service.factory.ResponseFactory;
import com.test.online.store.common.service.model.Response;
import com.test.online.store.common.service.model.SuccessResponse;
import com.test.online.store.inventory.model.InventoryBean;
import com.test.online.store.inventory.service.InventoriesService;

@ExtendWith(MockitoExtension.class)
class InventoriesControllerTest {

    @Mock
    private ResponseFactory responseFactory;

    @Mock
    private InventoriesService inventoriesService;

    @InjectMocks
    private InventoriesController inventoriesController;

    @Test
    void shouldGetByProductSlug() {
        // Arrange
        final InventoryBean inventory = new InventoryBean(8L);
        when(inventoriesService.getByProductSlug("gaming-mouse")).thenReturn(inventory);
        when(responseFactory.construct(ResponseType.SUCCESS, inventory))
                .thenReturn(SuccessResponse.<InventoryBean>builder().data(inventory).build());

        // Act
        final Response<InventoryBean> response = inventoriesController.getByProductSlug("gaming-mouse");

        // Assert
        assertEquals(8L, ((SuccessResponse<InventoryBean>) response).getData().getQuantity());
    }

    @Test
    void shouldUpsertByProductSlug() {
        // Arrange
        final InventoryBean inventory = new InventoryBean(10L);
        when(inventoriesService.upsertByProductSlug("gaming-mouse", inventory)).thenReturn(inventory);
        when(responseFactory.construct(ResponseType.SUCCESS, inventory))
                .thenReturn(SuccessResponse.<InventoryBean>builder().data(inventory).build());

        // Act
        final Response<InventoryBean> response = inventoriesController.upsertByProductSlug("gaming-mouse", inventory);

        // Assert
        assertEquals(10L, ((SuccessResponse<InventoryBean>) response).getData().getQuantity());
    }
}
