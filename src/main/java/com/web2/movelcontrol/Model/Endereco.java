package com.web2.movelcontrol.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cep", length = 10)
    private String cep;
    @Column(name = "rua", length = 100)
    private String rua;
    @Column(name = "numero", length = 10)
    private String numero;
    @Column(name = "complemento", length = 100)
    private String complemento;
    @Column(name = "bairro", length = 100)
    private String bairro;

    public Endereco(Long id, String cep, String rua, String numero, String complemento, String bairro) {
        this.id = id;
        this.cep = cep;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
    }

    public Endereco() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
}
