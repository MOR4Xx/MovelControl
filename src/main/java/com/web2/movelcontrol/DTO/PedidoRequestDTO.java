package com.web2.movelcontrol.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Schema(name = "PedidoRequestDTO", description = "DTO para criar ou atualizar um Pedido")
public class PedidoRequestDTO {
	
	@Schema(description = "Data do pedido. Se não fornecida, o serviço pode definir a data atual.", example = "2025-08-03")
	private Date dataPedido; // No model de Pedido o campo é data_pedido
	
	@Schema(description = "Status do pedido", example = "AGUARDANDO_PAGAMENTO", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message = "O status não pode ser vazio.")
	@Size(max = 50, message = "O status deve ter no máximo 50 caracteres.") // Conforme entidade Pedido
	private String status;
	
	@Schema(description = "Descrição adicional para o pedido", example = "Pedido urgente, cliente VIP.")
	@Size(max = 100, message = "A descrição deve ter no máximo 100 caracteres.") // Conforme entidade Pedido
	private String descricao;
	
	@Schema(description = "ID do Orçamento vinculado a este pedido.", example = "6", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull(message = "O ID do orçamento não pode ser nulo.")
	private Long orcamentoId;
	
	public PedidoRequestDTO() {
	}
	
	// Getters e Setters
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
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Long getOrcamentoId() {
		return orcamentoId;
	}
	
	public void setOrcamentoId(Long orcamentoId) {
		this.orcamentoId = orcamentoId;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PedidoRequestDTO that = (PedidoRequestDTO) o;
		return Objects.equals(dataPedido, that.dataPedido) &&
				Objects.equals(status, that.status) &&
				Objects.equals(descricao, that.descricao) &&
				Objects.equals(orcamentoId, that.orcamentoId);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(dataPedido, status, descricao, orcamentoId);
	}
}