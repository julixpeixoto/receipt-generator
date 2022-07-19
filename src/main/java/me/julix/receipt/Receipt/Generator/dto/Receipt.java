package me.julix.receipt.Receipt.Generator.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class Receipt {
    @NotNull
    private LocalDate date;

    @NotBlank
    private String description;

    @NotNull
    private Float value;
}
