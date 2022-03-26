package me.julix.receipt.Receipt.Generator.error;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseBody {
    private String message;
}
