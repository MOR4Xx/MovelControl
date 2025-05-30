package com.web2.movelcontrol.Model;

import java.io.Serializable;

import jakarta.persistence.*;

@Embeddable
public class ItemPedidoFornecedorId implements Serializable {
    private Long pedidoId;
    private Long itemId;

    public ItemPedidoFornecedorId() {}

    public ItemPedidoFornecedorId(Long pedidoId, Long itemId) {
        this.pedidoId = pedidoId;
        this.itemId = itemId;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    
}
