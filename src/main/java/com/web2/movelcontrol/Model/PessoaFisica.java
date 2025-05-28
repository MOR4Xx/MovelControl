package com.web2.movelcontrol.Model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue; // Importar
import jakarta.persistence.Entity;
// import jakarta.persistence.Table; // Remover Table

@Entity
@DiscriminatorValue("FISICA") // Define o valor para esta subclasse na coluna 'tipo'
public class PessoaFisica extends Pessoa {

    @Column(name = "identificador", unique = true, length = 50) // CPF será armazenado aqui
    private String cpf;

    // O campo 'tipo' foi removido, pois o @DiscriminatorValue cuida disso.

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