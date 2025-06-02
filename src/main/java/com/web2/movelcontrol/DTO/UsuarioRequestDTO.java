/*
 * Autor: Jorge Afonso
 * Responsavel: Jorge Afonso
 */

package com.web2.movelcontrol.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Schema(name = "UsuarioRequestDTO", description = "DTO para criação e atualização de usuários")
public class UsuarioRequestDTO {

    @Schema(description = "Nome completo do usuário", example = "João da Silva")
    @NotBlank(message = "O nome não pode ser vazio")
    private String nome;

    @Schema(description = "E-mail do usuário", example = "joao@email.com")
    @NotBlank(message = "O email não pode ser vazio")
    @Email(message = "O email deve ser válido")
    private String email;

    @Schema(description = "Senha do usuário", example = "senha123")
    @NotBlank(message = "A senha não pode ser vazia")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String senha;

    @Schema(description = "Nível de acesso do usuário", example = "ADMINISTRADOR")
    @NotBlank(message = "O nível de acesso não pode ser vazio")
    private String nivel_acesso;

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
        UsuarioRequestDTO that = (UsuarioRequestDTO) o;
        return Objects.equals(nome, that.nome) && Objects.equals(email, that.email) && Objects.equals(senha, that.senha) && Objects.equals(nivel_acesso, that.nivel_acesso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, email, senha, nivel_acesso);
    }
}
