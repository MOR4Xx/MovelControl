package com.web2.movelcontrol.Model;

import jakarta.persistence.*;

// ANTES: @MappedSuperclass
// AGORA: @Entity, @Table, @Inheritance, @DiscriminatorColumn
// PORQUÊ: Para que 'Pessoa' seja uma entidade raiz em uma hierarquia de herança e possa ser alvo de relacionamentos JPA (como em Orcamento.cliente).
//         '@MappedSuperclass' não cria uma entidade própria, apenas fornece mapeamentos para subclasses.
//         '@Inheritance(strategy = InheritanceType.SINGLE_TABLE)' define que todas as classes da hierarquia (Pessoa, PessoaFisica, PessoaJuridica)
//         serão mapeadas para uma única tabela no banco de dados.
//         '@DiscriminatorColumn' define a coluna ('tipo') que o Hibernate usará para diferenciar os tipos de Pessoa (Fisica ou Juridica) nessa tabela única.
// AFETA:  A forma como o JPA gerencia a persistência e os relacionamentos com Pessoa e suas subclasses.
//         Permite que Orcamento se relacione com 'Pessoa' de forma polimórfica.
//         As subclasses (PessoaFisica, PessoaJuridica) não precisarão mais da anotação @Table.
@Entity
@Table(name = "pessoa")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING, length = 10)
public abstract class Pessoa { // Pode ser abstrata se não houver necessidade de instanciar 'Pessoa' diretamente
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 50)
    private String nome;
    
    @Column(length = 50)
    private String telefone;
    
    @Column(length = 50)
    private String email;
    
    // O campo 'identificador' (CPF/CNPJ) será movido/mantido apenas nas subclasses específicas.
    // O campo 'tipo' que existia nas subclasses foi removido como atributo Java.
    // PORQUÊ: A coluna 'tipo' no banco agora é gerenciada pelo @DiscriminatorColumn e @DiscriminatorValue.
    // AFETA: As subclasses não precisam mais declarar ou gerenciar o atributo 'tipo'.
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;
    
    public Pessoa() {
    }
    
    public Pessoa(Long id, String nome, String telefone, String email, Endereco endereco) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
    }
    
    // Getters e Setters permanecem os mesmos
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
    
    public Endereco getEndereco() {
        return endereco;
    }
    
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}