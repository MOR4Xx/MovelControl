package com.web2.movelcontrol.DTO;
/*
 * Autor: Artur Duarte
 * Responsavel: Artur Duarte
 */

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import java.util.Objects;
import org.springframework.hateoas.RepresentationModel;

@Schema(name = "PedidoResponseDTO", description = "DTO para representar um Pedido na resposta da API")
public class PedidoResponseDTO extends RepresentationModel<PedidoResponseDTO> {
	
	@Schema(description = "ID do Pedido", example = "1")
	private Long id;
	
	@Schema(description = "Data do pedido", example = "2025-08-03T00:00:00.000+00:00")
	private Date dataPedido; // No model de Pedido o campo é data_pedido
	
	@Schema(description = "Status atual do pedido", example = "EM_PROCESSAMENTO")
	private String status;
	
	@Schema(description = "Descrição do pedido", example = "Pedido referente ao orçamento de ID 1")
	private String descricao;
	
	
	@Schema(description = "ID do Orçamento vinculado", example = "1")
	private Long orcamentoId;
	
	
	public PedidoResponseDTO()  {
	}
	
	// Getters e Setters
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
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
		PedidoResponseDTO that = (PedidoResponseDTO) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(dataPedido, that.dataPedido) &&
				Objects.equals(status, that.status) &&
				Objects.equals(descricao, that.descricao) &&
				Objects.equals(orcamentoId, that.orcamentoId);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, dataPedido, status, descricao, orcamentoId);
	}
}