/*
 * Autor: Jorge Afonso
 * Responsavel: Jorge Afonso
 */

package com.web2.movelcontrol.Model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue; // Importar
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("FISICA")
public class PessoaFisica extends Pessoa {

    @Column(name = "identificador", unique = true, length = 50)
    private String cpf;

    public PessoaFisica() {
    }

    public PessoaFisica(Long id, String nome, String telefone, String email, Endereco endereco, String cpf) {
        super(id, nome, telefone, email, endereco);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}