package com.web2.movelcontrol.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "estoque")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String nome;
    @Column(nullable = false, length = 150)
    private String descricao;
    @Column(nullable = false, length = 10)
    private String unidadeMedida;
    @Column(nullable = false, length = 10)
    private Double precoUnitario;
    @Column(nullable = false, length = 10)
    private int quantidade_estoque;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private PedidoFornecedor pedidoFornecedor;


    public Item() {
    }

    public Item(Long id, String nome, String descricao, String unidade_medida, Double precoUnitario, int quantidadeEstoque, PedidoFornecedor pedidoFornecedor) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.unidadeMedida = unidade_medida;
        this.precoUnitario = precoUnitario;
        this.quantidade_estoque = quantidadeEstoque;
        this.pedidoFornecedor = pedidoFornecedor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidade_medida) {
        this.unidadeMedida = unidade_medida;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double preco_unitario) {
        this.precoUnitario = preco_unitario;
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

