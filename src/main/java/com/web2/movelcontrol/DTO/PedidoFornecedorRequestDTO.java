package com.web2.movelcontrol.DTO;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO utilizado para criar um novo pedido de fornecedor")
public class PedidoFornecedorRequestDTO {

    @Schema(description = "Status atual do pedido", example = "PENDENTE", required = true)
    @NotBlank
    private String status;

    @Schema(description = "Data em que o pedido foi realizado", example = "2025-06-10", required = true, type = "string", format = "date")
    @NotNull
    private Date dataPedido;

    @Schema(description = "ID do fornecedor relacionado ao pedido", example = "3", required = true)
    @NotNull
    private Long fornecedorId;

    @Schema(description = "Lista de itens incluídos no pedido", required = true)
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
