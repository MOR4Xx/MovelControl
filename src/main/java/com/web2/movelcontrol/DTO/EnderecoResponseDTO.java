/*
 * Autor: Jorge Afonso
 * Responsavel: Jorge Afonso
 */

package com.web2.movelcontrol.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(name = "EnderecoResponseDTO", description = "Dados de saída do endereço retornados pela API")
public class EnderecoResponseDTO {

    @Schema(description = "Nome da rua", example = "Rua 1")
    private String rua;
    @Schema(description = "Número do endereço", example = "123")
    private String numero;
    @Schema(description = "Bairro do endereço", example = "Centro")
    private String bairro;
    @Schema(description = "CEP do endereço", example = "11.111-111")
    private String cep;
    @Schema(description = "Complemento do endereço", example = "Apartamento 123")
    private String complemento;

    public EnderecoResponseDTO() {
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EnderecoResponseDTO that = (EnderecoResponseDTO) o;
        return Objects.equals(rua, that.rua) && Objects.equals(numero, that.numero) && Objects.equals(bairro, that.bairro) && Objects.equals(cep, that.cep) && Objects.equals(complemento, that.complemento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rua, numero, bairro, cep, complemento);
    }
}
