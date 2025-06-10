package com.web2.movelcontrol.DTO;

public class FornecedorResponseDTO {

    private Long id;
    private String nome;
    private String cnpj;
    private String telefone;
    private String email;
    EnderecoResponseDTO endereco;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public EnderecoResponseDTO getEndereco() {
        return endereco;
    }
    public void setEndereco(EnderecoResponseDTO endereco) {
        this.endereco = endereco;
    }
    

}
