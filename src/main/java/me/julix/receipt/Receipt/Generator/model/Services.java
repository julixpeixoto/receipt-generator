package me.julix.receipt.Receipt.Generator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Services {
    private String data;
    private String descricao;
    private String valor;
}
