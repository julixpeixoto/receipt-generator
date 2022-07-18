package me.julix.receipt.Receipt.Generator.handler;

import me.julix.receipt.Receipt.Generator.error.ErrorList;
import me.julix.receipt.Receipt.Generator.error.ErrorResponseBody;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ErrorList> details = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            details.add(ErrorList.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build());
        });

        ErrorResponseBody errorResponseBody = ErrorResponseBody.builder()
                .message("Invalid fields.")
                .errors(details)
                .build();

        return new ResponseEntity<>(errorResponseBody, status);
    }
}
