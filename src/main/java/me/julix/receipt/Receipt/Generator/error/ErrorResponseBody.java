package me.julix.receipt.Receipt.Generator.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ErrorResponseBody {
    private String message;
    private List<ErrorList> errors;
}
