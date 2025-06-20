/*
 * Autor: Jorge Afonso
 * Responsavel: Jorge Afonso
 */

package com.web2.movelcontrol.Model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "nota_fiscal")
public class NotaFiscal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String codigo;
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

    public NotaFiscal(Long id, String codigoDeBarras, Date data_emissao, Double valor, Pedido pedido) {
        this.id = id;
        this.codigo = codigoDeBarras;
        this.data_emissao = data_emissao;
        this.valor = valor;
        this.pedido = pedido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigoDeBarras) {
        this.codigo = codigoDeBarras;
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
