package com.borba.backendprovasenior.exception;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;

@Getter
@Builder
public class StandartError implements Serializable {
    private Instant timestamp;
    private Integer status;
    private String error;
    private Map message;
    private String path;
}
