package com.web2.movelcontrol.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "pedido_fornecedor")
public class PedidoFornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_pedido")
    private Date dataPedido;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

    @ManyToMany
    @JoinTable(name = "pedido_item", joinColumns = @JoinColumn(name = "pedido_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> itens_pedido = new ArrayList<>();

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public PedidoFornecedor() {

    }

    public PedidoFornecedor(Long id, Date dataPedido, String status, List<Item> itens_pedido) {
        this.id = id;
        this.dataPedido = dataPedido;
        this.status = status;
        this.itens_pedido = itens_pedido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Item> getItens_pedido() {
        return itens_pedido;
    }

    public void setItens_pedido(List<Item> itens_pedido) {
        this.itens_pedido = itens_pedido;
    }
}
