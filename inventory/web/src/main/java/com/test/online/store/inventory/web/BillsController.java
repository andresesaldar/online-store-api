package com.test.online.store.inventory.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.online.store.common.service.constant.ResponseType;
import com.test.online.store.common.service.factory.ResponseFactory;
import com.test.online.store.common.service.model.Response;
import com.test.online.store.common.web.route.BillRoute;
import com.test.online.store.common.web.route.CommonRoute;
import com.test.online.store.inventory.model.BillBean;
import com.test.online.store.inventory.service.BillsService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = BillRoute.BASE + CommonRoute.PRODUCT_SLUG_PARAM, produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "API_KEY")
@RequiredArgsConstructor
public class BillsController {

    private final ResponseFactory responseFactory;
    private final BillsService billsService;

    @PostMapping
    public Response<BillBean> createByProductSlug(
        @PathVariable("productSlug") String productSlug,
        @Valid @RequestBody BillBean billBean) {

        final BillBean createdBill = billsService.createByProductSlug(productSlug, billBean);
        return responseFactory.construct(ResponseType.SUCCESS, createdBill);
    }
}
