package com.test.online.store.inventory.domain.document;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.test.online.store.common.domain.model.Auditable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@Document(collection = "inventory")
@NoArgsConstructor
public class Inventory extends Auditable<ObjectId> {

    @Field(name = "product_slug")
    @Indexed(unique = true)
    private String productSlug;
    
    private Long quantity;
    
}
