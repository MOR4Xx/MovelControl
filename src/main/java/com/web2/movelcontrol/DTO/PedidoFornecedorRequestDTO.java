package com.web2.movelcontrol.DTO;

import java.util.*;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class PedidoFornecedorRequestDTO {
    @NotBlank
    private String status;
    @NotNull
    private Date dataPedido;
    @NotNull
    private Long fornecedorId;
    @NotEmpty
    private List<ItemPedidoFornecedorRequestDTO> itens;
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Date getDataPedido() {
        return dataPedido;
    }
    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }
    public Long getFornecedorId() {
        return fornecedorId;
    }
    public void setFornecedorId(Long fornecedorId) {
        this.fornecedorId = fornecedorId;
    }
    public List<ItemPedidoFornecedorRequestDTO> getItens() {
        return itens;
    }
    public void setItens(List<ItemPedidoFornecedorRequestDTO> itens) {
        this.itens = itens;
    }

}

