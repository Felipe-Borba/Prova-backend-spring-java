package com.borba.backendprovasenior.exception;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Builder
public class StandartError implements Serializable {
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
