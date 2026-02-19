package com.test.online.store.common.service.builder;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

public interface PageableBuilder {
    PageableBuilder page(Integer page);

    PageableBuilder size(Integer size);

    PageableBuilder sort(String sort, Direction direction);

    Pageable build();
}
