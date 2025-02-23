package taodigital.interview.jpa.demo.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import taodigital.interview.jpa.demo.model.response.ErrorsResponse; 
import java.util.List;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorsResponse> constraintVioilationException(ConstraintViolationException request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorsResponse.builder()
                        .errors(List.of(request.getMessage()))
                        .build()
        );
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorsResponse> responseResponseEntity(ResponseStatusException request) {
        return ResponseEntity.status(request.getStatusCode())
                .body(ErrorsResponse.<ErrorsResponse>builder().errors(List.of(request.getReason())).build());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorsResponse> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorsResponse.builder()
                        .errors(List.of(request.getRootCause().getMessage()))
                        .build());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorsResponse> noResourceFoundException(NoResourceFoundException request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorsResponse.builder()
                        .errors(List.of(request.getMessage()))
                        .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorsResponse> httpMessageNotReadableException(HttpMessageNotReadableException request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorsResponse.builder()
                        .errors(List.of(request.getMessage().toString()))
                        .build());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorsResponse> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException request) {
        return ResponseEntity.status(request.getStatusCode())
                .body(ErrorsResponse.builder()
                        .errors(List.of(request.getMessage()))
                        .build());
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<ErrorsResponse> invalidDataAccessApiUsageException(InvalidDataAccessApiUsageException request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorsResponse.builder()
                        .errors(List.of(request.getMessage()))
                        .build());
    }

}
