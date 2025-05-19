package com.web2.movelcontrol.Model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orcamento")
public class Orcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_criacao", nullable = false)
    private Date dataCriacao;

    @Column(name = "valor_total")
    private double valorTotal;

    @Column(name = "status", length = 50)
    private String status;

    @ManyToMany
    @JoinTable(name = "orcamento_material",
            joinColumns = @JoinColumn(name = "orcamento_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> listaMateriais;

//    @ManyToOne
//    @JoinColumn(name = "cliente_id")
//    private Pessoa cliente;

    public Orcamento() {
    }

    public Orcamento(Long id, Date dataCriacao, double valorTotal, String status, List<Item> listaMateriais, Pessoa cliente) {
        this.id = id;
        this.dataCriacao = dataCriacao;
        this.valorTotal = valorTotal;
        this.status = status;
        this.listaMateriais = listaMateriais;
        //this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id_orcamento) {
        this.id = id_orcamento;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Item> getListaMateriais() {
        return listaMateriais;
    }

    public void setListaMateriais(List<Item> listaMateriais) {
        this.listaMateriais = listaMateriais;
    }

//    public Pessoa getCliente() {
//        return cliente;
//    }
//
//    public void setCliente(Pessoa cliente) {
//        this.cliente = cliente;
//    }
}
