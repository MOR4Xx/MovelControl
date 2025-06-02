package com.web2.movelcontrol.DTO;

public class ItemPedidoFornecedorResponseDTO {
    private Long itemId;
    private String nome;
    private String descricao;
    private Integer quantidade;

    public ItemPedidoFornecedorResponseDTO(Long itemId, String nome, String descricao, Integer quantidade) {
        this.itemId = itemId;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

}
