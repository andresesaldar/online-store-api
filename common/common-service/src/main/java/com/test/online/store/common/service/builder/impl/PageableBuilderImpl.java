package com.test.online.store.common.service.builder.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.test.online.store.common.service.builder.PageableBuilder;

import jakarta.annotation.Nullable;

@Component
public class PageableBuilderImpl implements PageableBuilder {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 100;

    private int page;
    private int size;
    private Sort sort;

    @Override
    public PageableBuilder page(Integer page) {
        if(page == null || page < 0) {
            this.page = DEFAULT_PAGE;
        } else {
            this.page = page;
        }
        return this;
    }

    @Override
    public PageableBuilder size(Integer size) {
        if(size == null || size <= 0) {
            this.size = DEFAULT_SIZE;
        } else if(size > MAX_PAGE_SIZE) {
            this.size = MAX_PAGE_SIZE;
        } else {
            this.size = size;
        }
        return this;
    }

    @Override
    public PageableBuilder sort(String sort, @Nullable Direction direction) {
        this.sort = Sort.by(direction == null ? Direction.ASC : direction, sort);
        return this;
    }

    @Override
    public Pageable build() {
        if (sort != null) {
            return PageRequest.of(page, size, sort);
        } else {
            return PageRequest.of(page, size);
        }    
    }

}
