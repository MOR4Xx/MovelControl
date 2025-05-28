package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.Model.Orcamento;
import com.web2.movelcontrol.Model.OrcamentoItem; // Importar OrcamentoItem
import com.web2.movelcontrol.Model.Item;
import com.web2.movelcontrol.Model.Pessoa; // Importar Pessoa (para o cliente)
import com.web2.movelcontrol.Repository.OrcamentoRepository;
import com.web2.movelcontrol.Repository.ItemRepository;
import com.web2.movelcontrol.Repository.PessoaFisicaRepository; // Exemplo, ou um PessoaRepository genérico
import com.web2.movelcontrol.Repository.PessoaJuridicaRepository; // Exemplo
// Você pode precisar de um PessoaRepository se for buscar Pessoa de forma genérica
// ou usar os repositórios específicos e verificar o tipo.
import com.web2.movelcontrol.Exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importar para transações

import java.util.Date;
import java.util.List;
import java.util.HashSet; // Para criar o Set de itens
import java.util.Set; // Para o tipo do Set
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Service
public class OrcamentoService {

    private Logger logger = Logger.getLogger(OrcamentoService.class.getName());

    @Autowired
    private OrcamentoRepository orcamentoRepository;

    @Autowired
    private ItemRepository itemRepository;

    // Para buscar o cliente, você precisará de um repositório para Pessoa.
    // Como Pessoa é @MappedSuperclass, você pode precisar de repositórios
    // para as entidades concretas (PessoaFisica, PessoaJuridica) ou uma query customizada.
    // Por simplicidade, vamos assumir que o objeto Pessoa cliente já viria populado com ID
    // ou você teria uma lógica para buscá-lo aqui.
    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository; // Exemplo
    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository; // Exemplo


    @Transactional // É uma boa prática ter transações em métodos que modificam dados
    public Orcamento criarOrcamento(Orcamento orcamentoInput) {
        // 1. Preparar o objeto Orçamento principal
        Orcamento novoOrcamento = new Orcamento();

        if (orcamentoInput.getDataCriacao() == null) {
            novoOrcamento.setDataCriacao(new Date());
        } else {
            novoOrcamento.setDataCriacao(orcamentoInput.getDataCriacao());
        }

        if (orcamentoInput.getStatus() == null || orcamentoInput.getStatus().isEmpty()) {
            novoOrcamento.setStatus("PENDENTE");
        } else {
            novoOrcamento.setStatus(orcamentoInput.getStatus());
        }

        // 2. Associar o Cliente
        if (orcamentoInput.getCliente() != null && orcamentoInput.getCliente().getId() != null) {
            // Aqui você precisaria buscar a Pessoa (Cliente) do banco de dados.
            // A forma de buscar dependerá se você tem um PessoaRepository genérico
            // ou precisa verificar o tipo e usar PessoaFisicaRepository/PessoaJuridicaRepository.
            // Exemplo simplificado (assumindo que o ID é de PessoaFisica por ora):
            Pessoa cliente = pessoaFisicaRepository.findById(orcamentoInput.getCliente().getId())
                    .orElseThrow(() -> new NotFoundException("Cliente com ID " + orcamentoInput.getCliente().getId() + " não encontrado."));
            novoOrcamento.setCliente(cliente);
        } else {
            // Lançar exceção ou definir como nulo se cliente não for obrigatório
            // throw new IllegalArgumentException("Cliente é obrigatório para criar um orçamento.");
            novoOrcamento.setCliente(null); // Ou tratar conforme regra de negócio
        }


        // 3. Processar e adicionar os itens ao orçamento
        // A forma como orcamentoInput.getItensOrcamento() chega aqui dependerá do DTO.
        // Vamos supor que orcamentoInput.getItensOrcamento() já vem com OrcamentoItem
        // onde o 'Item' dentro de cada OrcamentoItem tem pelo menos o ID, e 'quantity' está preenchida.
        Set<OrcamentoItem> itensProcessados = new HashSet<>();
        if (orcamentoInput.getItensOrcamento() != null && !orcamentoInput.getItensOrcamento().isEmpty()) {
            for (OrcamentoItem itemSubmetidoInfo : orcamentoInput.getItensOrcamento()) {
                if (itemSubmetidoInfo.getItem() == null || itemSubmetidoInfo.getItem().getId() == null) {
                    throw new IllegalArgumentException("ID do Item não fornecido para um dos itens do orçamento.");
                }
                if (itemSubmetidoInfo.getQuantity() == null || itemSubmetidoInfo.getQuantity() <= 0) {
                    throw new IllegalArgumentException("Quantidade inválida para o item ID: " + itemSubmetidoInfo.getItem().getId());
                }

                Item itemDoBanco = itemRepository.findById(itemSubmetidoInfo.getItem().getId())
                        .orElseThrow(() -> new NotFoundException("Item com ID " + itemSubmetidoInfo.getItem().getId() + " não encontrado."));

                // Criar o OrcamentoItem e associar com o novoOrcamento e itemDoBanco
                // O construtor de OrcamentoItem já faz new OrcamentoItemKey(orcamento.getId(), item.getId());
                // mas como novoOrcamento ainda não tem ID, ajustaremos isso.
                // O ideal é que o construtor de OrcamentoItem não dependa do ID do orçamento ainda não salvo.
                // O JPA cuidará de setar as FKs com base nos objetos associados.

                // Ajuste: Primeiro criamos o OrcamentoItem com as referências de objeto.
                // O 'novoOrcamento' será associado ao OrcamentoItem.
                // Quando 'novoOrcamento' for salvo, o CascadeType.ALL irá persistir os 'OrcamentoItem'
                // e o JPA preencherá a FK 'orcamento_id' em 'orcamento_item'.
                OrcamentoItem novoOrcamentoItem = new OrcamentoItem();
                novoOrcamentoItem.setOrcamento(novoOrcamento); // Associa ao orçamento que será salvo
                novoOrcamentoItem.setItem(itemDoBanco);
                novoOrcamentoItem.setQuantity(itemSubmetidoInfo.getQuantity());
                // A chave composta (OrcamentoItemKey) será populada pelo JPA ao persistir
                // por causa dos @MapsId nas associações dentro de OrcamentoItem.
                // É importante que o OrcamentoItemKey tenha os campos orcamentoId e itemId
                // e que os @MapsId estejam corretos.
                // Alternativamente, se você já setou o ID no OrcamentoItemKey no construtor
                // (orcamento.getId(), item.getId()), você precisaria salvar o orçamento ANTES
                // de criar os OrcamentoItems se o ID do orçamento for necessário na chave.
                // Mas com @MapsId e associações de objeto, o JPA pode gerenciar isso.

                itensProcessados.add(novoOrcamentoItem);
            }
        }
        novoOrcamento.setItensOrcamento(itensProcessados);


        // 4. Calcular o valor total
        novoOrcamento.calcularValorTotalOrcamento(); // Chama o método da entidade

        logger.info("Criando um novo orçamento.");
        return orcamentoRepository.save(novoOrcamento);
    }

    public Orcamento buscarOrcamentoPorId(Long id) {
        logger.info("Buscando orçamento com ID: " + id);
        Orcamento orcamento = orcamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Orçamento não encontrado com ID: " + id));
        // Para carregar os itens e cliente se forem LAZY e você precisar deles aqui:
        // Hibernate.initialize(orcamento.getItensOrcamento());
        // Hibernate.initialize(orcamento.getCliente());
        return orcamento;
    }

    public List<Orcamento> listarTodosOrcamentos() {
        logger.info("Listando todos os orçamentos.");
        return orcamentoRepository.findAll();
    }

    @Transactional
    public Orcamento atualizarOrcamento(Long id, Orcamento orcamentoAtualizadoInput) {
        logger.info("Atualizando orçamento com ID: " + id);
        Orcamento orcamentoExistente = orcamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Orçamento não encontrado com ID: " + id + ", não foi possível atualizar."));

        // Atualiza campos simples
        if (orcamentoAtualizadoInput.getDataCriacao() != null) {
            orcamentoExistente.setDataCriacao(orcamentoAtualizadoInput.getDataCriacao());
        }
        if (orcamentoAtualizadoInput.getStatus() != null && !orcamentoAtualizadoInput.getStatus().isEmpty()) {
            orcamentoExistente.setStatus(orcamentoAtualizadoInput.getStatus());
        }

        // Atualiza Cliente
        if (orcamentoAtualizadoInput.getCliente() != null && orcamentoAtualizadoInput.getCliente().getId() != null) {
            // Lógica similar à de criarOrcamento para buscar e setar o cliente
            Pessoa cliente = pessoaFisicaRepository.findById(orcamentoAtualizadoInput.getCliente().getId()) // Exemplo
                    .orElseThrow(() -> new NotFoundException("Cliente com ID " + orcamentoAtualizadoInput.getCliente().getId() + " não encontrado."));
            orcamentoExistente.setCliente(cliente);
        } else {
            orcamentoExistente.setCliente(null); // Ou tratar conforme regra de negócio
        }

        // Atualiza Itens do Orçamento
        // Uma abordagem comum é limpar os itens existentes e adicionar os novos.
        // `orphanRemoval=true` na entidade Orcamento cuidará de remover os antigos do banco.
        orcamentoExistente.getItensOrcamento().clear(); // Limpa a coleção existente

        if (orcamentoAtualizadoInput.getItensOrcamento() != null && !orcamentoAtualizadoInput.getItensOrcamento().isEmpty()) {
            Set<OrcamentoItem> novosItensProcessados = new HashSet<>();
            for (OrcamentoItem itemInfo : orcamentoAtualizadoInput.getItensOrcamento()) {
                if (itemInfo.getItem() == null || itemInfo.getItem().getId() == null) {
                    throw new IllegalArgumentException("ID do Item não fornecido para um dos itens do orçamento.");
                }
                if (itemInfo.getQuantity() == null || itemInfo.getQuantity() <= 0) {
                    throw new IllegalArgumentException("Quantidade inválida para o item ID: " + itemInfo.getItem().getId());
                }

                Item itemDoBanco = itemRepository.findById(itemInfo.getItem().getId())
                        .orElseThrow(() -> new NotFoundException("Item com ID " + itemInfo.getItem().getId() + " não encontrado."));

                OrcamentoItem novoOrcamentoItem = new OrcamentoItem();
                novoOrcamentoItem.setOrcamento(orcamentoExistente); // Associa ao orçamento existente
                novoOrcamentoItem.setItem(itemDoBanco);
                novoOrcamentoItem.setQuantity(itemInfo.getQuantity());

                novosItensProcessados.add(novoOrcamentoItem);
            }
            // Adiciona todos os novos itens de uma vez.
            // O JPA gerencia a adição à coleção e a persistência devido ao CascadeType.ALL.
            orcamentoExistente.getItensOrcamento().addAll(novosItensProcessados);
        }

        orcamentoExistente.calcularValorTotalOrcamento(); // Recalcula o valor total

        return orcamentoRepository.save(orcamentoExistente);
    }

    @Transactional
    public void deletarOrcamento(Long id) {
        logger.info("Deletando orçamento com ID: " + id);
        if (!orcamentoRepository.existsById(id)) {
            throw new NotFoundException("Orçamento não encontrado com ID: " + id + ", não foi possível deletar.");
        }
        orcamentoRepository.deleteById(id); // CascadeType.ALL e orphanRemoval=true cuidarão dos OrcamentoItem
        logger.info("Orçamento com ID: " + id + " deletado com sucesso.");
    }
}