package com.web2.movelcontrol.Models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_pedido", nullable = false)
    private Date data_pedido;

    @Column(name ="status", nullable = false, length = 10)
    private String status;

    @Column(name = "descricao", length = 100)
    private String descricao;

    @OneToOne
    @JoinColumn(name = "orcamento_id", referencedColumnName = "id")
    private Orcamento orcamento;

    public Pedido() {
    }

    public Pedido(int id, Date data_pedido, Orcamento orcamento) {
        this.id = id;
        this.data_pedido = data_pedido;
        this.orcamento = orcamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData_pedido() {
        return data_pedido;
    }

    public void setData_pedido(Date data_pedido) {
        this.data_pedido = data_pedido;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }
}
