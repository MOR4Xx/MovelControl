package com.web2.movelcontrol.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "pedido_item")
public class ItemPedidoFornecedor {

    @EmbeddedId
    private ItemPedidoFornecedorId id = new ItemPedidoFornecedorId();

    @ManyToOne
    @MapsId("pedidoId")
    @JoinColumn(name = "pedido_id")
    private PedidoFornecedor pedido;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer quantidade;

    public ItemPedidoFornecedorId getId() {
        return id;
    }

    public void setId(ItemPedidoFornecedorId id) {
        this.id = id;
    }

    public PedidoFornecedor getPedido() {
        return pedido;
    }

    public void setPedido(PedidoFornecedor pedido) {
        this.pedido = pedido;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

}
