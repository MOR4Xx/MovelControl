package com.web2.movelcontrol.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Objects;

@Tag(name = "Pessoa Fisica", description = "Operações relacionadas as Pessoas Físicas")
public class PessoaFisicaResponseDTO {

    @Schema(description = "Nome da pessoa fisica", example = "Jorge")
    private String nome;
    @Schema(description = "Telefone da pessoa fisica", example = "(11)99999-9999")
    private String telefone;
    @Schema(description = "Endereço da pessoa fisica", example = "Rua 1, 1234-567, São Paulo - SP")
    private EnderecoResponseDTO endereco;
    @Schema(description = "CPF da pessoa fisica", example = "111.111.111-11")
    private String cpf;
    @Schema(description = "E-mail da pessoa fisica", example = "jorge@gmail.com")
    private String email;

    public PessoaFisicaResponseDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public EnderecoResponseDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoResponseDTO endereco) {
        this.endereco = endereco;
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

    @Override
    public boolean equals(Object o) {

        if (o == null || getClass() != o.getClass()) return false;
        PessoaFisicaResponseDTO that = (PessoaFisicaResponseDTO) o;
        return Objects.equals(nome, that.nome) && Objects.equals(telefone, that.telefone) && Objects.equals(endereco, that.endereco) && Objects.equals(cpf, that.cpf) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, telefone, endereco, cpf, email);
    }
}
