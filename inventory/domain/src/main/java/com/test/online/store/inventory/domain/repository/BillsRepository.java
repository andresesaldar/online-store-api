package com.test.online.store.inventory.domain.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.test.online.store.inventory.domain.document.Bill;

public interface BillsRepository extends MongoRepository<Bill, ObjectId> {

}
