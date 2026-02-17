package com.test.online.store.product.domain.model;

import java.math.BigDecimal;

import com.test.online.store.common.domain.model.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "product", uniqueConstraints = @UniqueConstraint(columnNames = "slug"))
public class Product extends Auditable<Long> {

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column
    private String description;
}
