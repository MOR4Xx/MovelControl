/*
 * Autor: Jorge Afonso
 * Responsavel: Jorge Afonso
 */

package com.web2.movelcontrol.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Schema(name = "PessoaFisicaRequestDTO", description = "DTO para criação e atualização de pessoas fisicas")
public class PessoaFisicaRequestDTO {

    @Schema(description = "Nome da pessoa jurídica", example = "Jorge")
    @NotBlank(message = "O nome não pode ser vazio")
    private String nome;

    @Schema(description = "CPF da pessoa jurídica", example = "111.111.111-11")
    @NotBlank(message = "O CPF não pode ser nulo")
    private String cpf;

    @Schema(description = "E-mail da pessoa jurídica", example = "jorge@gmail.com")
    @NotBlank(message = "O email não pode ser vazio")
    private String email;

    @Schema(description = "Telefone da pessoa juridica", example = "(64)99999-9999")
    private String telefone;

    @Schema(description = "Tipo da pessoa FISICA", example = "FISICA")
    private String tipo = "FISICA";

    @Schema(description = "Endereço da pessoa fisica", required = true, implementation = EnderecoRequestDTO.class)
    @NotNull(message = "O endereço não pode ser vazio")
    private EnderecoRequestDTO endereco;

    public PessoaFisicaRequestDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PessoaFisicaRequestDTO that = (PessoaFisicaRequestDTO) o;
        return Objects.equals(nome, that.nome) && Objects.equals(cpf, that.cpf) && Objects.equals(email, that.email) && Objects.equals(telefone, that.telefone) && Objects.equals(tipo, that.tipo) && Objects.equals(endereco, that.endereco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, cpf, email, telefone, tipo, endereco);
    }
}

