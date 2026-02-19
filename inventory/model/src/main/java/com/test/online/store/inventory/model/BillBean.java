package com.test.online.store.inventory.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class BillBean {
    @NotNull
    @Min(1)
    Long quantity;
    BigDecimal totalPrice;
    LocalDateTime createdAt;
}
