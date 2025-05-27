package com.web2.movelcontrol.DTO;

import java.util.Date;
import java.util.List;

public class PedidoFornecedorResponseDTO {
    private Long id;
    private String status;
    private Date dataPedido;
    private String nomeFornecedor;
    private List<ItemDTO> itens;

    public PedidoFornecedorResponseDTO(Long id, String status, Date dataPedido, String nomeFornecedor, List<ItemDTO> itens) {
        this.id = id;
        this.status = status;
        this.dataPedido = dataPedido;
        this.nomeFornecedor = nomeFornecedor;
        this.itens = itens;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public List<ItemDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemDTO> itens) {
        this.itens = itens;
    }

}
