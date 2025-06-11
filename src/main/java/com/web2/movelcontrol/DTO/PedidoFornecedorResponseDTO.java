package com.web2.movelcontrol.DTO;

import java.util.Date;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta com os dados de um pedido de fornecedor")
public class PedidoFornecedorResponseDTO {

    @Schema(description = "ID do pedido", example = "101")
    private Long id;

    @Schema(description = "Status atual do pedido", example = "APROVADO")
    private String status;

    @Schema(description = "Data em que o pedido foi realizado", example = "2025-06-10", type = "string", format = "date")
    private Date dataPedido;

    @Schema(description = "Nome do fornecedor relacionado ao pedido", example = "Móveis Brasil Ltda.")
    private String nomeFornecedor;

    @Schema(description = "Lista de itens incluídos no pedido")
    private List<ItemPedidoFornecedorResponseDTO> itens;


    public PedidoFornecedorResponseDTO(Long id, String status, Date dataPedido, String nomeFornecedor, List<ItemPedidoFornecedorResponseDTO> itens) {
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

    public List<ItemPedidoFornecedorResponseDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoFornecedorResponseDTO> itens) {
        this.itens = itens;
    }
}
