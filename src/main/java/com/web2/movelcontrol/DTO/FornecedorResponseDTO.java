package com.web2.movelcontrol.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta contendo os dados de um fornecedor")
public class FornecedorResponseDTO {

    @Schema(description = "Identificador único do fornecedor", example = "1")
    private Long id;

    @Schema(description = "Nome do fornecedor", example = "maderanit.")
    private String nome;

    @Schema(description = "CNPJ do fornecedor", example = "12.345.678/0001-99")
    private String cnpj;

    @Schema(description = "Telefone de contato do fornecedor", example = "(62) 91234-5678")
    private String telefone;

    @Schema(description = "E-mail de contato do fornecedor", example = "contato@maderanit.com.br")
    private String email;

    @Schema(description = "Endereço associado ao fornecedor")
    private EnderecoResponseDTO endereco;


    

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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EnderecoResponseDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoResponseDTO endereco) {
        this.endereco = endereco;
    }
}
