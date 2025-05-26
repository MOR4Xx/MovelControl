package com.web2.movelcontrol.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class UsuarioRequestDTO {

    @NotBlank(message = "O nome não pode ser vazio")
    private String nome;

    @NotBlank(message = "O email não pode ser vazio")
    @Email(message = "O email deve ser válido")
    private String email;

    @NotBlank(message = "A senha não pode ser vazia")
    private String senha;
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
