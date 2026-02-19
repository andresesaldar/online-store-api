package com.test.online.store.common.service.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Value;

@Value
public class PageResult<T> implements Serializable {
    List<T> content = new ArrayList<>();
    int page;
    int size;
    Boolean last;
    Boolean first;
}
