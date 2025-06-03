/*
 * Autor: Jorge Afonso
 * Responsavel: Jorge Afonso
 */

package com.web2.movelcontrol.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(name = "UsuarioResponseDTO", description = "Dados de saída do usuário retornados pela API")
public class UsuarioResponseDTO {
    @Schema(description = "Nome completo do usuário", example = "João Silva")
    private String nome;

    @Schema(description = "Endereço de e-mail do usuário", example = "joao.silva@email.com")
    private String email;

    @Schema(description = "Nível de acesso do usuário", example = "ADMIN ou COMUM")
    private String nivel_acesso;

    public UsuarioResponseDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNivel_acesso() {
        return nivel_acesso;
    }

    public void setNivel_acesso(String nivel_acesso) {
        this.nivel_acesso = nivel_acesso;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioResponseDTO that = (UsuarioResponseDTO) o;
        return Objects.equals(nome, that.nome) && Objects.equals(email, that.email) && Objects.equals(nivel_acesso, that.nivel_acesso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, email, nivel_acesso);
    }
}
