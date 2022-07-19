package me.julix.receipt.Receipt.Generator.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class Receipt {
    @NotBlank
    private LocalDate date;

    @NotBlank
    private String description;

    @NotBlank
    private Float value;
}
