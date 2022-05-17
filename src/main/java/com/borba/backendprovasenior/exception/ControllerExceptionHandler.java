package com.borba.backendprovasenior.exception;

import com.borba.backendprovasenior.exception.errors.RecursoNaoEncontrado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontrado.class)
    public ResponseEntity<StandartError> ConflictErrorHandler(Exception ex, HttpServletRequest request) {
        var httpStatus = HttpStatus.CONFLICT;
        var error = StandartError
                .builder()
                .timestamp(Instant.now())
                .path(request.getMethod() + ": " + request.getRequestURI())
                .message(ex.getMessage())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .build();
        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(RecursoNaoEncontrado.class)
    public ResponseEntity<StandartError> RecursoNaoEncontradoHandler(Exception ex, HttpServletRequest request) {
        var httpStatus = HttpStatus.NOT_FOUND;
        var error = StandartError
                .builder()
                .timestamp(Instant.now())
                .path(request.getMethod() + ": " + request.getRequestURI())
                .message(ex.getMessage())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .build();
        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandartError> exceptionHandler(Exception ex, HttpServletRequest request) {
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        var error = StandartError
                .builder()
                .timestamp(Instant.now())
                .path(request.getMethod() + ": " + request.getRequestURI())
                .message(ex.getMessage())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .build();
        return new ResponseEntity<>(error, httpStatus);
    }
}
