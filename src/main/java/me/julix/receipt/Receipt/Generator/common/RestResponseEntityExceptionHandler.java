package me.julix.receipt.Receipt.Generator.common;

import me.julix.receipt.Receipt.Generator.error.ErrorResponseBody;
import me.julix.receipt.Receipt.Generator.exception.FileNotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { FileNotSupportedException.class })
    protected ResponseEntity<Object> handleFileNotSupportedException(RuntimeException ex, WebRequest request) {
        ErrorResponseBody responseBody = ErrorResponseBody.builder()
                .message("File not supported!")
                .build();
        return new ResponseEntity(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { RuntimeException.class })
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        ErrorResponseBody responseBody = ErrorResponseBody.builder()
                .message(ex.getMessage())
                .build();
        return new ResponseEntity(responseBody, HttpStatus.BAD_REQUEST);
    }
}
