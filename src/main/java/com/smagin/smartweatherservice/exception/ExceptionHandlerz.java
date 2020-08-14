package com.smagin.smartweatherservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.function.BiConsumer;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class ExceptionHandlerz extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<String> handleAllExceptions(Exception ex, WebRequest request) {
        return doHandle(ex, INTERNAL_SERVER_ERROR, logger::error);
    }

    private ResponseEntity<String> doHandle(Exception ex, HttpStatus status, BiConsumer<String, Throwable> log) {
        log.accept(status.getReasonPhrase(), ex);
        return new ResponseEntity<>(ex.getLocalizedMessage(), status);
    }
}
