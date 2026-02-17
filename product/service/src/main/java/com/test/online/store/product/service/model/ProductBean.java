package com.test.online.store.product.service.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class ProductBean implements Serializable {
    String slug;
    String name;
    BigDecimal price;
    String description;    
}
