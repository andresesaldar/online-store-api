package com.test.online.store.common.service.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@EqualsAndHashCode(callSuper=false)
public class ErrorResponse extends Response<String> {

    String code;

    String message;

    @Builder.Default
    Boolean ok = false;
}
