package com.test.online.store.inventory.domain.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.test.online.store.inventory.domain.document.Inventory;

public interface InventoriesRepository extends MongoRepository<Inventory, ObjectId> {
    Optional<Inventory> findByProductSlug(String productSlug);

}
