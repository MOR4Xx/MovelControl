package com.web2.movelcontrol.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO utilizado para informar item e quantidade em um pedido de fornecedor")
public class ItemPedidoFornecedorRequestDTO {

    @Schema(description = "ID do item que será adicionado ao pedido", example = "10", required = true)
    private Long itemId;

    @Schema(description = "Quantidade do item no pedido", example = "50", required = true)
    private Integer quantidade;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
