package com.web2.movelcontrol.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Schema(name = "ItemOrcamentoRequestDTO", description = "DTO para representar um item em uma requisição de orçamento")
public class ItemOrcamentoRequestDTO {
	
	@Schema(description = "ID do item (do estoque) a ser adicionado ao orçamento", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull(message = "O ID do item não pode ser nulo.")
	private Long itemId;
	
	@Schema(description = "Quantidade do item desejada no orçamento", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull(message = "A quantidade não pode ser nula.")
	@Min(value = 1, message = "A quantidade deve ser no mínimo 1.")
	private Integer quantity;
	
	public ItemOrcamentoRequestDTO() {
	}
	
	public ItemOrcamentoRequestDTO(Long itemId, Integer quantity) {
		this.itemId = itemId;
		this.quantity = quantity;
	}
	
	public Long getItemId() {
		return itemId;
	}
	
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ItemOrcamentoRequestDTO that = (ItemOrcamentoRequestDTO) o;
		return Objects.equals(itemId, that.itemId) && Objects.equals(quantity, that.quantity);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(itemId, quantity);
	}
}