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
    @OneToMany(mappedBy = "pedidoFornecedor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Item> itens_pedido = new ArrayList<>();

    public PedidoFornecedor() {

    }

    public PedidoFornecedor(Integer id, Date dataPedido, String status, List<Item> itens_pedido) {
        this.id = id;
        this.dataPedido = dataPedido;
        this.status = status;
        this.itens_pedido = itens_pedido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
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
