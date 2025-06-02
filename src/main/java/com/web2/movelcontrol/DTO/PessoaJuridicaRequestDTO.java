/*
 * Autor: Jorge Afonso
 * Responsavel: Jorge Afonso
 */

package com.web2.movelcontrol.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Schema(name = "PessoaJuridicaRequestDTO",description = "DTO para criação e atualização de pessoas jurídicas")
public class PessoaJuridicaRequestDTO {

    @Schema(description = "Nome da pessoa jurídica", example = "Jorge")
    @NotBlank(message = "O nome não pode ser vazio")
    private String nome;

    @Schema(description = "CNPJ da pessoa jurídica", example = "11.111.111/0001-89")
    @NotBlank(message = "O cnpj não pode ser nulo")
    private String cnpj;

    @Schema(description = "E-mail da pessoa jurídica", example = "jorge@gmail.com")
    @NotBlank(message = "O email não pode ser vazio")
    private String email;

    @Schema(description = "Telefone da pessoa juridica", example = "(64)99999-9999")
    private String telefone;

    @Schema(description = "Tipo da pessoa jurídica", example = "JURIDICA")
    private String tipo = "JURIDICA";

    @Schema(description = "Endereço da pessoa jurídica", required = true, implementation = EnderecoRequestDTO.class)
    @NotNull(message = "O endereço não pode ser vazio")
    private EnderecoRequestDTO endereco;

    public PessoaJuridicaRequestDTO() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public EnderecoRequestDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoRequestDTO endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PessoaJuridicaRequestDTO that = (PessoaJuridicaRequestDTO) o;
        return Objects.equals(nome, that.nome) && Objects.equals(cnpj, that.cnpj) && Objects.equals(email, that.email) && Objects.equals(tipo, that.tipo) && Objects.equals(endereco, that.endereco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, cnpj, email, tipo, endereco);
    }
}
