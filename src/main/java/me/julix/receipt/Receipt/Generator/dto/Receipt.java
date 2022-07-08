package me.julix.receipt.Receipt.Generator.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Receipt {
    private Date date;
    private String description;
    private Float value;
}
