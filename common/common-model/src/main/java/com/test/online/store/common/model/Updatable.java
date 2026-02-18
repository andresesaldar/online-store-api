package com.test.online.store.common.model;

import java.time.LocalDateTime;

public interface Updatable {
    LocalDateTime getLastModifiedAt();
    void setLastModifiedAt(LocalDateTime lastModifiedAt);
}
