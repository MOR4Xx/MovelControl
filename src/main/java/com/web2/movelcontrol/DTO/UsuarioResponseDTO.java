package com.web2.movelcontrol.DTO;

import com.web2.movelcontrol.Model.Usuario;

import java.util.Objects;

public class UsuarioResponseDTO {

    private String nome;
    private String email;
    private String nivel_acesso;

    public UsuarioResponseDTO() {}

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
