package com.web2.movelcontrol.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "pessoa")
public class PessoaFisica extends Pessoa {

    @Column(name = "identificador", nullable = false, unique = true, length = 50)
    private String cpf;
    @Column(name = "tipo", nullable = false, length = 10)
    private String tipo;

    public PessoaFisica() {
    }

    public PessoaFisica(Long id, String nome, String telefone, String email, String endereco, String cpf) {
        super(id, nome, telefone, email, endereco);
        this.cpf = cpf;
        this.tipo = "fisica";
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
