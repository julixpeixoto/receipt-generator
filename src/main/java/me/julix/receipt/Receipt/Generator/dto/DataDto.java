package me.julix.receipt.Receipt.Generator.dto;

import lombok.Data;

import java.util.List;

@Data
public class DataDto {
    private String customerName;

    private String customerEmail;

    private List<Receipt> receipts;
}
