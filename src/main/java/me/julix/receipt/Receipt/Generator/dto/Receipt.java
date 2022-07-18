package me.julix.receipt.Receipt.Generator.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class Receipt {
    @NotBlank
    private Date date;

    @NotBlank
    private String description;

    @NotBlank
    private Float value;
}
