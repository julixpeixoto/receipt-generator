package me.julix.receipt.Receipt.Generator.model;

import lombok.Data;

@Data
public class Services {
    private String data;
    private String descricao;
    private String valor;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Services(String data, String descricao, String valor) {
        this.data = data;
        this.descricao = descricao;
        this.valor = valor;
    }
}
