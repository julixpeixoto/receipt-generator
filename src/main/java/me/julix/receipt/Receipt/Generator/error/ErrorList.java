package me.julix.receipt.Receipt.Generator.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ErrorList {
    private String field;
    private String message;
}
