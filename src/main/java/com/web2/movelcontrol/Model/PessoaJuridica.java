package com.web2.movelcontrol.Model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue; // Importar
import jakarta.persistence.Entity;
// import jakarta.persistence.Table; // Remover Table

@Entity
@DiscriminatorValue("JURIDICA") // Define o valor para esta subclasse na coluna 'tipo'
public class PessoaJuridica extends Pessoa {

    @Column(name = "identificador", unique = true, length = 50) // CNPJ será armazenado aqui
    private String cnpj;

    // O campo 'tipo' foi removido, pois o @DiscriminatorValue cuida disso.

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