package com.test.online.store.common.service.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(callSuper=false)
public class SuccessResponse<T> extends Response<T> {
    T data;

    @Builder.Default
    Boolean ok = true;
}
