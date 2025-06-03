package com.web2.movelcontrol.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;

@Schema(name = "ItemOrcamentoResponseDTO", description = "DTO para representar um item em uma resposta de orçamento")
public class ItemOrcamentoResponseDTO {
	
	@Schema(description = "ID do item (do estoque)", example = "1")
	private Long itemId;
	
	@Schema(description = "Nome do item", example = "Prateleira Modelo Artur") // Exemplo atualizado
	private String nomeItem;
	
	@Schema(description = "Descrição do item", example = "Prateleira de madeira maciça, acabamento especial por Artur") // Exemplo atualizado
	private String descricaoItem;
	
	@Schema(description = "Preço unitário do item no momento do orçamento", example = "120.50")
	private Double precoUnitarioItem;
	
	@Schema(description = "Quantidade do item no orçamento", example = "2")
	private Integer quantity;
	
	@Schema(description = "Subtotal para este item no orçamento (precoUnitarioItem * quantity)", example = "241.00")
	private Double subtotal;
	
	// Construtor padrão
	public ItemOrcamentoResponseDTO() {
	}
	
	// Getters e Setters (como definidos anteriormente)
	public Long getItemId() {
		return itemId;
	}
	
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	public String getNomeItem() {
		return nomeItem;
	}
	
	public void setNomeItem(String nomeItem) {
		this.nomeItem = nomeItem;
	}
	
	public String getDescricaoItem() {
		return descricaoItem;
	}
	
	public void setDescricaoItem(String descricaoItem) {
		this.descricaoItem = descricaoItem;
	}
	
	public Double getPrecoUnitarioItem() {
		return precoUnitarioItem;
	}
	
	public void setPrecoUnitarioItem(Double precoUnitarioItem) {
		this.precoUnitarioItem = precoUnitarioItem;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public Double getSubtotal() {
		return subtotal;
	}
	
	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ItemOrcamentoResponseDTO that = (ItemOrcamentoResponseDTO) o;
		return Objects.equals(itemId, that.itemId) &&
				Objects.equals(nomeItem, that.nomeItem) &&
				Objects.equals(descricaoItem, that.descricaoItem) &&
				Objects.equals(precoUnitarioItem, that.precoUnitarioItem) &&
				Objects.equals(quantity, that.quantity) &&
				Objects.equals(subtotal, that.subtotal);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(itemId, nomeItem, descricaoItem, precoUnitarioItem, quantity, subtotal);
	}
}