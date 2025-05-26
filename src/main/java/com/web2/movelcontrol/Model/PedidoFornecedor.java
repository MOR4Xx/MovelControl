package com.web2.movelcontrol.Model;

import jakarta.persistence.*;

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


    public PedidoFornecedor() {

    }

    public PedidoFornecedor(Long id, Date dataPedido, String status) {
        this.id = id;
        this.dataPedido = dataPedido;
        this.status = status;
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
}
