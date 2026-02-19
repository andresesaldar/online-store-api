package com.test.online.store.product.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.test.online.store.common.model.helper.SlugHelper;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
public class ProductBean implements Serializable {
    @NotEmpty
    String slug;
    
    @NotEmpty
    @Size(min = 3)
    String name;
    
    @Min(1)
    BigDecimal price;

    String description;

    @Builder
    @Jacksonized
    public ProductBean(String name, BigDecimal price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.slug = SlugHelper.toSlug(name);
    }
}
