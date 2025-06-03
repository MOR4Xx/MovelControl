/*
 * Autor: Jorge Afonso
 * Responsavel: Jorge Afonso
 */

package com.web2.movelcontrol.Model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue; // Importar
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("JURIDICA")
public class PessoaJuridica extends Pessoa {

    @Column(name = "identificador", unique = true, length = 50)
    private String cnpj;

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
}