package com.test.online.store.inventory.domain.document;

import java.math.BigDecimal;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.test.online.store.common.domain.model.Auditable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@Document(collection = "bill")
@NoArgsConstructor
public class Bill extends Auditable<ObjectId> {

    @Field(name = "product_slug")
    private String productSlug;

    private Long quantity;

    @Field(name = "total_price")
    private BigDecimal totalPrice;

}
