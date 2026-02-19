package com.test.online.store.common.model;

import java.time.LocalDateTime;

public interface Creatable {
    LocalDateTime getCreatedAt();
    void setCreatedAt(LocalDateTime createdAt);
}
