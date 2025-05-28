package com.web2.movelcontrol.Model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "orcamento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<OrcamentoItem> itensOrcamento = new HashSet<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id") // Esta será a coluna de chave estrangeira na tabela 'orcamento'
    private Pessoa cliente;

    public Orcamento() {
    }

    public Orcamento(Date dataCriacao, String status, Pessoa cliente) {
        this.dataCriacao = dataCriacao;
        this.status = status;
        this.cliente = cliente;
        this.valorTotal = 0.0; // Inicializa com 0, será calculado
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

    public Pessoa getCliente() {
        return cliente;
    }

    public void setCliente(Pessoa cliente) {
        this.cliente = cliente;
    }

    public Set<OrcamentoItem> getItensOrcamento() {
        return itensOrcamento;
    }

    public void setItensOrcamento(Set<OrcamentoItem> itensOrcamento) {
        this.itensOrcamento = itensOrcamento;
    }


    public void adicionarItem(Item item, Integer quantidade) {
        OrcamentoItem orcamentoItem = new OrcamentoItem(this, item, quantidade);
        this.itensOrcamento.add(orcamentoItem);

    }


    public void removerItem(OrcamentoItem orcamentoItem) {
        this.itensOrcamento.remove(orcamentoItem);

    }


    public void calcularValorTotalOrcamento() {
        if (this.itensOrcamento == null) {
            this.valorTotal = 0.0;
            return;
        }
        this.valorTotal = this.itensOrcamento.stream()
                .mapToDouble(OrcamentoItem::getSubtotal)
                .sum();
    }

}
