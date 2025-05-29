package com.web2.movelcontrol.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;

/*
 não precisa de todos os detalhes do cliente aninhados dentro da resposta do orçamento.
 Retornar o PessoaFisicaResponseDTO ou PessoaJuridicaResponseDTO completos dentro de cada orçamento em uma lista,
 por exemplo, tornaria a resposta da API muito grande e com informações possivelmente desnecessárias
 para aquele contexto.
 */
@Schema(name = "ClienteResponseDTO", description = "DTO simplificado para representar o cliente em respostas da API")
public class ClienteResponseDTO {
	
	@Schema(description = "ID único do cliente", example = "1")
	private Long id;
	
	@Schema(description = "Nome do cliente (seja pessoa física ou nome fantasia/razão social da pessoa jurídica)", example = "Artur Silva / Empresa do Artur Soluções")
	private String nome;
	
	@Schema(description = "Indica o tipo de pessoa do cliente, para que o frontend possa interpretar corretamente. Ex: FISICA, JURIDICA", example = "FISICA")
	private String tipoPessoa;
	
	// Construtor padrão (necessário para frameworks como Jackson)
	public ClienteResponseDTO() {
	}
	
	// Construtor completo (opcional, mas pode ser útil)
	public ClienteResponseDTO(Long id, String nome, String tipoPessoa) {
		this.id = id;
		this.nome = nome;
		this.tipoPessoa = tipoPessoa;
	}
	
	// Getters e Setters
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
	
	public String getTipoPessoa() {
		return tipoPessoa;
	}
	
	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ClienteResponseDTO that = (ClienteResponseDTO) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(nome, that.nome) &&
				Objects.equals(tipoPessoa, that.tipoPessoa);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, nome, tipoPessoa);
	}
	
	@Override
	public String toString() {
		return "ClienteResponseDTO{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", tipoPessoa='" + tipoPessoa + '\'' +
				'}';
	}
}