package com.web2.movelcontrol.Models;

import jakarta.persistence.*;

import java.util.Date;
@Entity
@Table(name = "nota_fiscal")
public class NotaFiscal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private long codigo;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date data_emissao;
    @Column(length = 50)
    private Double valor;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pedido_id", referencedColumnName = "id")
    private Pedido pedido;

    public NotaFiscal() {
    }

    public NotaFiscal(int id, long codigo, Date data_emissao, Double valor, Pedido pedido) {
        this.id = id;
        this.codigo = codigo;
        this.data_emissao = data_emissao;
        this.valor = valor;
        this.pedido = pedido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public Date getData_emissao() {
        return data_emissao;
    }

    public void setData_emissao(Date data_emissao) {
        this.data_emissao = data_emissao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
