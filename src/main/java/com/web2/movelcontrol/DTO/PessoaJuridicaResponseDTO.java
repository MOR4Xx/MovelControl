package com.web2.movelcontrol.DTO;

import com.web2.movelcontrol.Model.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(name = "PessoaJuridicaResponseDTO", description = "Dados de saída de pessoas jurídicas retornados pela API")
public class PessoaJuridicaResponseDTO {

    @Schema(description = "Nome da pessoa jurídica", example = "Jorge")
    private String nome;
    @Schema(description = "Telefone da pessoa jurídica", example = "(11) 99999-9999")
    private String telefone;
    @Schema(description = "Endereço da pessoa jurídica", example = "Rua 1, 1234-567, São Paulo - SP")
    private EnderecoResponseDTO endereco;
    @Schema(description = "CNPJ da pessoa juridica", example = "11.111.111/0001-89")
    private String cnpj;
    @Schema(description = "E-mail da pessoa jurídica", example = "jorge.afonso@example.com")
    private String email;

    public PessoaJuridicaResponseDTO() {
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

    public void setEndereco(EnderecoResponseDTO endereco) {
        this.endereco = endereco;
    }

    public EnderecoResponseDTO getEndereco() {
        return endereco;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PessoaJuridicaResponseDTO that = (PessoaJuridicaResponseDTO) o;
        return Objects.equals(nome, that.nome) && Objects.equals(telefone, that.telefone) && Objects.equals(endereco, that.endereco) && Objects.equals(cnpj, that.cnpj) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, telefone, endereco, cnpj, email);
    }
}
