package com.web2.movelcontrol.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

@Schema(name = "OrcamentoRequestDTO", description = "DTO para criar ou atualizar um orçamento")
public class OrcamentoRequestDTO {
	
	@Schema(description = "Data de criação do orçamento. Se não fornecida, será a data atual.", example = "2025-05-29")
	private Date dataCriacao;
	
	@Schema(description = "Status do orçamento", example = "PENDENTE_APROVACAO")
	private String status;
	
	@Schema(description = "ID do cliente associado ao orçamento.", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull(message = "O ID do cliente não pode ser nulo.")
	private Long clienteId;
	
	@Schema(description = "Lista de itens do orçamento.", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotEmpty(message = "A lista de itens não pode ser vazia.")
	@Valid // Para que as validações dentro de ItemOrcamentoRequestDTO sejam acionadas
	private Set<ItemOrcamentoRequestDTO> itens = new HashSet<>();
	
	public OrcamentoRequestDTO() {
	}
	
	public Date getDataCriacao() {
		return dataCriacao;
	}
	
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Long getClienteId() {
		return clienteId;
	}
	
	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}
	
	public Set<ItemOrcamentoRequestDTO> getItens() {
		return itens;
	}
	
	public void setItens(Set<ItemOrcamentoRequestDTO> itens) {
		this.itens = itens;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OrcamentoRequestDTO that = (OrcamentoRequestDTO) o;
		return Objects.equals(dataCriacao, that.dataCriacao) &&
				Objects.equals(status, that.status) &&
				Objects.equals(clienteId, that.clienteId) &&
				Objects.equals(itens, that.itens);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(dataCriacao, status, clienteId, itens);
	}
}