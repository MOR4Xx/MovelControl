package com.web2.movelcontrol.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "pessoa")
public class PessoaJuridica extends Pessoa {

    @Column(name = "identificador", unique = true, length = 50)
    private String cnpj;

    @Column(length = 10)
    private String tipo = "JURIDICA";

    public PessoaJuridica() {}

    public PessoaJuridica(Long id, String nome, String telefone, String email, Endereco endereco, String cnpj) {
        super(id, nome, telefone, email, endereco);
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
