package com.web2.movelcontrol.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import java.util.Set;
import java.util.Objects;
import org.springframework.hateoas.RepresentationModel;

@Schema(name = "OrcamentoResponseDTO", description = "DTO para representar um orçamento na resposta da API")
public class OrcamentoResponseDTO  extends RepresentationModel<OrcamentoResponseDTO> {
	
	
	@Schema(description = "ID do orçamento", example = "1")
	private Long id;
	
	@Schema(description = "Data de criação do orçamento", example = "2025-05-29")
	private Date dataCriacao;
	
	@Schema(description = "Status atual do orçamento", example = "APROVADO")
	private String status;
	
	@Schema(description = "Valor total calculado do orçamento", example = "1500.75")
	private Double valorTotal;
	
	@Schema(description = "Dados do cliente associado ao orçamento")
	private ClienteResponseDTO cliente;
	
	@Schema(description = "Lista de itens incluídos no orçamento")
	private Set<ItemOrcamentoResponseDTO> itens; // Utilizará o ItemOrcamentoResponseDTO, antes era uma list
	
	public OrcamentoResponseDTO() {
	}
	
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public Date getDataCriacao() { return dataCriacao; }
	public void setDataCriacao(Date dataCriacao) { this.dataCriacao = dataCriacao; }
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
	public Double getValorTotal() { return valorTotal; }
	public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }
	public ClienteResponseDTO getCliente() { return cliente; }
	public void setCliente(ClienteResponseDTO cliente) { this.cliente = cliente; }
	public Set<ItemOrcamentoResponseDTO> getItens() { return itens; }
	public void setItens(Set<ItemOrcamentoResponseDTO> itens) { this.itens = itens; }
	
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OrcamentoResponseDTO that = (OrcamentoResponseDTO) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(dataCriacao, that.dataCriacao) &&
				Objects.equals(status, that.status) &&
				Objects.equals(valorTotal, that.valorTotal) &&
				Objects.equals(cliente, that.cliente) &&
				Objects.equals(itens, that.itens);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, dataCriacao, status, valorTotal, cliente, itens);
	}
}