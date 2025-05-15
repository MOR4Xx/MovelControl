package com.web2.movelcontrol.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "estoque")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 100)
    private String nome;
    @Column(nullable = false, length = 150)
    private String descricao;
    @Column(nullable = false, length = 10)
    private String unidade_medida;
    @Column(nullable = false, length = 10)
    private Double preco_unitario;
    @Column(nullable = false, length = 10)
    private int quantidade_estoque;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private PedidoFornecedor pedidoFornecedor;

    public Item() {
    }

    public Item(int id, String nome, String descricao, String unidade_medida, Double preco_unitario, int quantidade_estoque, PedidoFornecedor pedidoFornecedor) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.unidade_medida = unidade_medida;
        this.preco_unitario = preco_unitario;
        this.quantidade_estoque = quantidade_estoque;
        this.pedidoFornecedor = pedidoFornecedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUnidade_medida() {
        return unidade_medida;
    }

    public void setUnidade_medida(String unidade_medida) {
        this.unidade_medida = unidade_medida;
    }

    public Double getPreco_unitario() {
        return preco_unitario;
    }

    public void setPreco_unitario(Double preco_unitario) {
        this.preco_unitario = preco_unitario;
    }

    public int getQuantidade_estoque() {
        return quantidade_estoque;
    }

    public void setQuantidade_estoque(int quantidade_estoque) {
        this.quantidade_estoque = quantidade_estoque;
    }

    public PedidoFornecedor getPedidoFornecedor() {
        return pedidoFornecedor;
    }

    public void setPedidoFornecedor(PedidoFornecedor pedidoFornecedor) {
        this.pedidoFornecedor = pedidoFornecedor;
    }
}

