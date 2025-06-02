package com.web2.movelcontrol.DTO;

public class ItemPedidoFornecedorRequestDTO {
    private Long itemId;
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
