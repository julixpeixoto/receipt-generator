package me.julix.receipt.Receipt.Generator.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class DataDto {
    @NotBlank
    private String customerName;

    @NotBlank
    private String customerEmail;

    @NotBlank
    private String receiverName;

    @NotBlank
    private String paymentMethod;

    private String paymentDate;

    @NotNull
    private List<@Valid Receipt> receipts;
}
