package com.test.online.store.inventory.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.online.store.common.service.constant.ResponseType;
import com.test.online.store.common.service.factory.ResponseFactory;
import com.test.online.store.common.service.model.Response;
import com.test.online.store.common.web.route.CommonRoute;
import com.test.online.store.common.web.route.InventoryRoute;
import com.test.online.store.inventory.model.InventoryBean;
import com.test.online.store.inventory.service.InventoriesService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = InventoryRoute.BASE + CommonRoute.PRODUCT_SLUG_PARAM, produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "API_KEY")
@RequiredArgsConstructor
public class InventoriesController {

    private final ResponseFactory responseFactory;
    private final InventoriesService inventoriesService;

    @GetMapping
    public Response<InventoryBean> getByProductSlug(@PathVariable("productSlug") String productSlug) {
        final InventoryBean inventory = inventoriesService.getByProductSlug(productSlug);
        return responseFactory.construct(ResponseType.SUCCESS, inventory);
    }

    @PutMapping
    public Response<InventoryBean> upsertByProductSlug(
        @PathVariable("productSlug") String productSlug,
        @Valid @RequestBody InventoryBean inventoryBean) {

        final InventoryBean inventory = inventoriesService.upsertByProductSlug(productSlug, inventoryBean);
        return responseFactory.construct(ResponseType.SUCCESS, inventory);
    }
}
