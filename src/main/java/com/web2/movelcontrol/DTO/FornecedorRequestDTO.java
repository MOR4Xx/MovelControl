package com.web2.movelcontrol.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

import com.web2.movelcontrol.Model.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO utilizado para criação de um novo fornecedor")
public class FornecedorRequestDTO {

    @Schema(
        description = "Nome completo do fornecedor",
        example = "Móveis Brasil Ltda.",
        required = true
    )
    @NotBlank
    private String nome;

    @Schema(
        description = "CNPJ do fornecedor",
        example = "12.345.678/0001-99",
        required = true
    )
    @NotBlank
    @CNPJ
    private String cnpj;

    @Schema(
        description = "Telefone de contato do fornecedor",
        example = "(62) 91234-5678",
        required = true
    )
    @NotBlank
    private String telefone;

    @Schema(
        description = "E-mail de contato do fornecedor",
        example = "contato@moveisbrasil.com.br"
    )
    @Email
    private String email;

    @Schema(
        description = "Endereço completo do fornecedor (objeto embutido)",
        implementation = Endereco.class
    )
    private Endereco endereco;

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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
