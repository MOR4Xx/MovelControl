/*
 * Autor: Jorge Afonso
 * Responsavel: Jorge Afonso
 */

package com.web2.movelcontrol.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

@Schema(description = "DTO para criação e atualização de endereços")
public class EnderecoRequestDTO {

    @NotBlank(message = "A rua não pode ser vazio")
    private String rua;

    @Schema(description = "CEP da Pessoa", example = "11.111-111")
    @NotBlank(message = "O CEP não pode ser vazio")
    @Pattern(regexp = "\\d{5}-\\d{3}|\\d{8}", message = "CEP inválido. Use o formato 12345-678 ou 12345678")
    private String cep;

    @Schema(description = "Número da rua", example = "123")
    @NotBlank(message = "O número não pode ser vazio")
    private String numero;

    @Schema(description = "Complemento da rua", example = "Apartamento 123")
    private String complemento;

    @Schema(description = "Bairro da Pessoa", example = "Centro")
    @NotBlank(message = "O bairro não pode ser vazio")
    private String bairro;

    public EnderecoRequestDTO() {
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EnderecoRequestDTO that = (EnderecoRequestDTO) o;
        return Objects.equals(rua, that.rua) && Objects.equals(cep, that.cep) && Objects.equals(numero, that.numero) && Objects.equals(complemento, that.complemento) && Objects.equals(bairro, that.bairro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rua, cep, numero, complemento, bairro);
    }
}
