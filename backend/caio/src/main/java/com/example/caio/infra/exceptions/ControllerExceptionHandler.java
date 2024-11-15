package com.example.caio.infra.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.caio.infra.exceptions.conflict.ProductAlreadyExistsException;
import com.example.caio.infra.exceptions.notFound.ProductNotFoundException;
import com.example.caio.infra.exceptions.notFound.RoleNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jakarta.security.auth.message.AuthException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class, SizeLimitExceededException.class, IllegalArgumentException.class })
    public ResponseEntity<ErrorResponse> handleExceptions(RuntimeException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        "Unprocessable Entity: There was an issue processing your request due to invalid data. Please verify the information provided and resubmit.",
                        ex.getLocalizedMessage()),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({ AuthException.class })
    public ResponseEntity<ErrorResponse> handleAuthExceptions(RuntimeException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.UNAUTHORIZED,
                        "Conflict: Sorry, there is a conflict with the current state of the resource. Please refresh and try your request again.",
                        ex.getLocalizedMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        ErrorResponse errorResponse = new ErrorResponse((HttpStatus) status, "Validation Error", errors);
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NonNull HttpMessageNotReadableException ex, @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        Throwable rootCause = ex.getRootCause();
        String errorMessage = "Malformed JSON request";
        List<String> errors = new ArrayList<>();

        if (rootCause instanceof InvalidFormatException) {
            InvalidFormatException invalidFormatEx = (InvalidFormatException) rootCause;
            Object value = invalidFormatEx.getValue();
            String expectedType = invalidFormatEx.getTargetType().getSimpleName();
            String message = String.format("Invalid value '%s' for type '%s'", value, expectedType);
            errorMessage = "Invalid JSON value: " + message;
            errors.add(message);
        } else {
            errors.add(errorMessage);
        }

        ErrorResponse errorResponse = new ErrorResponse((HttpStatus) status, errorMessage, errors);
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @ExceptionHandler({ ProductNotFoundException.class, RoleNotFoundException.class })
    public ResponseEntity<ErrorResponse> handleNotFoundException(RuntimeException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.NOT_FOUND, "Not Found: The requested resource could not be found on the server. Please verify the URL and try again.",
                        ex.getLocalizedMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ ProductAlreadyExistsException.class })
    public ResponseEntity<ErrorResponse> handleConflictException(RuntimeException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.CONFLICT,
                        "Conflict: A product with the specified code already exists.",
                        ex.getLocalizedMessage()),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ErrorResponse> handleOtherExceptions(Exception ex) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Internal Server Error: An unexpected error occurred. Please try again later.",
                        ex.getLocalizedMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
