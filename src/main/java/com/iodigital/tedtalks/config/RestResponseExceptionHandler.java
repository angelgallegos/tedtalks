package com.iodigital.tedtalks.config;

import com.iodigital.tedtalks.exceptions.TedTalksNotFoundException;
import lombok.NonNull;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = TedTalksNotFoundException.class)
    public ResponseEntity<Object> handle(TedTalksNotFoundException exception) {
        String errorMessage = exception.getMessage();
        ApiError apiError = new ApiError(errorMessage, Collections.singletonList(exception.getMessage()), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError, null, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        String errorMessage = "Validation failed";
        ApiError apiError = new ApiError(errorMessage, exception.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList(), (HttpStatus) status);
        return this.createResponseEntity(apiError, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
        TypeMismatchException exception,
        @NonNull HttpHeaders headers,
        @NonNull HttpStatusCode status,
        @NonNull WebRequest request
    ) {
        String errorMessage = exception.getMessage();
        ApiError apiError = new ApiError(errorMessage, Collections.singletonList(exception.getMessage()), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError, headers, HttpStatus.NOT_FOUND);
    }

}
