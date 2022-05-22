package com.borba.backendprovasenior.exception;

import com.borba.backendprovasenior.exception.errors.ConflictError;
import com.borba.backendprovasenior.exception.errors.RecursoNaoEncontrado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandartError> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        var httpStatus = HttpStatus.BAD_REQUEST;

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        var error = StandartError
                .builder()
                .timestamp(Instant.now())
                .path(request.getMethod() + ": " + request.getRequestURI())
                .message(errors)
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .build();
        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(ConflictError.class)
    public ResponseEntity<StandartError> ConflictErrorHandler(Exception ex, HttpServletRequest request) {
        var httpStatus = HttpStatus.CONFLICT;
        Map<String, String> errors = new HashMap<>();
        errors.put("error:", ex.getMessage());

        var error = StandartError
                .builder()
                .timestamp(Instant.now())
                .path(request.getMethod() + ": " + request.getRequestURI())
                .message(errors)
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .build();
        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(RecursoNaoEncontrado.class)
    public ResponseEntity<StandartError> RecursoNaoEncontradoHandler(Exception ex, HttpServletRequest request) {
        var httpStatus = HttpStatus.BAD_REQUEST;
        Map<String, String> errors = new HashMap<>();
        errors.put("error:", ex.getMessage());

        var error = StandartError
                .builder()
                .timestamp(Instant.now())
                .path(request.getMethod() + ": " + request.getRequestURI())
                .message(errors)
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .build();
        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandartError> exceptionHandler(Exception ex, HttpServletRequest request) {
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        Map<String, String> errors = new HashMap<>();
        errors.put("error:", ex.getMessage());

        var error = StandartError
                .builder()
                .timestamp(Instant.now())
                .path(request.getMethod() + ": " + request.getRequestURI())
                .message(errors)
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .build();
        return new ResponseEntity<>(error, httpStatus);
    }
}
