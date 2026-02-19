package com.test.online.store.inventory.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class InventoryBean {
    @NotNull
    @Min(1)
    Long quantity;
}
