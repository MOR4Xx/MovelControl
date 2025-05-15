package com.web2.movelcontrol.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "pessoa")
public class PessoaJuridica extends Pessoa {

    @Column(name = "identificador", nullable = false, unique = true, length = 50)
    private String cnpj;

    @Column(name = "tipo", nullable = false, length = 10)
    private String tipo;

    public PessoaJuridica() {}

    public PessoaJuridica(int id, String nome, String telefone, String email, String endereco, String cnpj) {
        super(id, nome, telefone, email, endereco);
        this.cnpj = cnpj;
        this.tipo = "juridica";
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
