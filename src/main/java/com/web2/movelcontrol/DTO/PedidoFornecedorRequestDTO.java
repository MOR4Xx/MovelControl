package com.web2.movelcontrol.DTO;

import java.util.*;

public class PedidoFornecedorRequestDTO {
    private String status;
    private Date dataPedido;
    private Long fornecedorId;
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

