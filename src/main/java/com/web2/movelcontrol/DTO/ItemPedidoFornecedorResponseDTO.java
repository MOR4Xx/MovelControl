package com.web2.movelcontrol.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta com os dados do item incluído em um pedido de fornecedor")
public class ItemPedidoFornecedorResponseDTO {

    @Schema(description = "ID do item", example = "10")
    private Long itemId;

    @Schema(description = "Nome do item", example = "parafuso philips ")
    private String nome;

    @Schema(description = "Descrição detalhada do item", example = "parafuso philips 4x20mm em aço inox")
    private String descricao;

    @Schema(description = "Quantidade do item no pedido", example = "20")
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
