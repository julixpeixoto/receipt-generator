package me.julix.receipt.Receipt.Generator.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class DataDto {
    @NotBlank
    private String customerName;

    @NotBlank
    private String customerEmail;

    @NotNull
    private List<Receipt> receipts;
}
