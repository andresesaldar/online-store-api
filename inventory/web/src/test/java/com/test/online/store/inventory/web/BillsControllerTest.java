package com.test.online.store.inventory.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.test.online.store.common.service.constant.ResponseType;
import com.test.online.store.common.service.factory.ResponseFactory;
import com.test.online.store.common.service.model.Response;
import com.test.online.store.common.service.model.SuccessResponse;
import com.test.online.store.inventory.model.BillBean;
import com.test.online.store.inventory.service.BillsService;

@ExtendWith(MockitoExtension.class)
class BillsControllerTest {

    @Mock
    private ResponseFactory responseFactory;

    @Mock
    private BillsService billsService;

    @InjectMocks
    private BillsController billsController;

    @Test
    void shouldCreateByProductSlug() {
        // Arrange
        final BillBean bill = new BillBean(2L, BigDecimal.valueOf(20), null);
        when(billsService.createByProductSlug("gaming-mouse", bill)).thenReturn(bill);
        when(responseFactory.construct(ResponseType.SUCCESS, bill))
                .thenReturn(SuccessResponse.<BillBean>builder().data(bill).build());

        // Act
        final Response<BillBean> response = billsController.createByProductSlug("gaming-mouse", bill);

        // Assert
        assertEquals(BigDecimal.valueOf(20), ((SuccessResponse<BillBean>) response).getData().getTotalPrice());
    }
}
