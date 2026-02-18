package com.test.online.store.common.model;

public interface Identifiable<ID> {
    ID getId();
    void setId(ID id);
}
