package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.DTO.OrcamentoRequestDTO;
import com.web2.movelcontrol.DTO.ItemOrcamentoRequestDTO;
import com.web2.movelcontrol.Model.Orcamento;
import com.web2.movelcontrol.Model.OrcamentoItem;
import com.web2.movelcontrol.Model.Item;
import com.web2.movelcontrol.Model.Pessoa;
import com.web2.movelcontrol.Repository.OrcamentoRepository;
import com.web2.movelcontrol.Repository.ItemRepository;
import com.web2.movelcontrol.Repository.PessoaRepository;
import com.web2.movelcontrol.Exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class OrcamentoService {
    
    private Logger logger = Logger.getLogger(OrcamentoService.class.getName());
    
    @Autowired
    private OrcamentoRepository orcamentoRepository;
    
    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Transactional
   
    public Orcamento criarOrcamento(OrcamentoRequestDTO orcamentoDTO) {
        Orcamento novoOrcamento = new Orcamento();
        
        if (orcamentoDTO.getDataCriacao() == null) {
            novoOrcamento.setDataCriacao(new Date());
        } else {
            novoOrcamento.setDataCriacao(orcamentoDTO.getDataCriacao());
        }
        
        if (orcamentoDTO.getStatus() == null || orcamentoDTO.getStatus().isEmpty()) {
            novoOrcamento.setStatus("PENDENTE");
        } else {
            novoOrcamento.setStatus(orcamentoDTO.getStatus());
        }
        
        // Associar o Cliente usando clienteId do DTO
        if (orcamentoDTO.getClienteId() != null) {
            Pessoa cliente = pessoaRepository.findById(orcamentoDTO.getClienteId())
                    .orElseThrow(() -> new NotFoundException("Cliente com ID " + orcamentoDTO.getClienteId() + " não encontrado."));
            novoOrcamento.setCliente(cliente);
        } else {
            
            throw new IllegalArgumentException("ID do Cliente é obrigatório para criar um orçamento.");
        }
        
        // Processar e adicionar os itens ao orçamento a partir do DTO
        Set<OrcamentoItem> itensProcessados = new HashSet<>();
        if (orcamentoDTO.getItens() != null && !orcamentoDTO.getItens().isEmpty()) {
            for (ItemOrcamentoRequestDTO itemDto : orcamentoDTO.getItens()) {
                // Validações de itemId e quantity já devem ter sido feitas pelo @Valid no controller
                Item itemDoBanco = itemRepository.findById(itemDto.getItemId())
                        .orElseThrow(() -> new NotFoundException("Item com ID " + itemDto.getItemId() + " não encontrado."));
                
                OrcamentoItem novoOrcamentoItem = new OrcamentoItem();
                novoOrcamentoItem.setOrcamento(novoOrcamento);
                novoOrcamentoItem.setItem(itemDoBanco);
                novoOrcamentoItem.setQuantity(itemDto.getQuantity());
                itensProcessados.add(novoOrcamentoItem);
            }
        }
        novoOrcamento.setItensOrcamento(itensProcessados);
        
        // Calcular o valor total
        novoOrcamento.calcularValorTotalOrcamento();
        
        logger.info("Criando um novo orçamento a partir de DTO.");
        return orcamentoRepository.save(novoOrcamento);
    }
    
    public Orcamento buscarOrcamentoPorId(Long id) {
        logger.info("Buscando orçamento com ID: " + id);
        return orcamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Orçamento não encontrado com ID: " + id));
    }
    
    public List<Orcamento> listarTodosOrcamentos() {
        logger.info("Listando todos os orçamentos.");
        return orcamentoRepository.findAll();
    }
    
    @Transactional
    public Orcamento atualizarOrcamento(Long id, OrcamentoRequestDTO orcamentoDTO) {
        logger.info("Atualizando orçamento com ID: " + id + " a partir de DTO.");
        Orcamento orcamentoExistente = orcamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Orçamento com ID " + id + " não encontrado, não foi possível atualizar."));
        
        // Atualiza campos simples
        if (orcamentoDTO.getDataCriacao() != null) {
            orcamentoExistente.setDataCriacao(orcamentoDTO.getDataCriacao());
        }
        if (orcamentoDTO.getStatus() != null && !orcamentoDTO.getStatus().isEmpty()) {
            orcamentoExistente.setStatus(orcamentoDTO.getStatus());
        }
        
        // Atualiza Cliente
        if (orcamentoDTO.getClienteId() != null) {
            if (orcamentoExistente.getCliente() == null || !orcamentoExistente.getCliente().getId().equals(orcamentoDTO.getClienteId())) {
                Pessoa cliente = pessoaRepository.findById(orcamentoDTO.getClienteId())
                        .orElseThrow(() -> new NotFoundException("Cliente com ID " + orcamentoDTO.getClienteId() + " não encontrado."));
                orcamentoExistente.setCliente(cliente);
            }
        } else {
            orcamentoExistente.setCliente(null);
        }
        
        
        // Atualiza Itens do Orçamento
        orcamentoExistente.getItensOrcamento().clear(); // Limpa a coleção existente (orphanRemoval=true removerá do banco)
        if (orcamentoDTO.getItens() != null && !orcamentoDTO.getItens().isEmpty()) {
            Set<OrcamentoItem> novosItensProcessados = new HashSet<>();
            for (ItemOrcamentoRequestDTO itemDto : orcamentoDTO.getItens()) {
                Item itemDoBanco = itemRepository.findById(itemDto.getItemId())
                        .orElseThrow(() -> new NotFoundException("Item com ID " + itemDto.getItemId() + " não encontrado."));
                
                OrcamentoItem novoOrcamentoItem = new OrcamentoItem();
                novoOrcamentoItem.setOrcamento(orcamentoExistente);
                novoOrcamentoItem.setItem(itemDoBanco);
                novoOrcamentoItem.setQuantity(itemDto.getQuantity());
                novosItensProcessados.add(novoOrcamentoItem);
            }
            orcamentoExistente.getItensOrcamento().addAll(novosItensProcessados);
        }
        
        orcamentoExistente.calcularValorTotalOrcamento();
        
        return orcamentoRepository.save(orcamentoExistente);
    }
    
    @Transactional
    public void deletarOrcamento(Long id) {
        logger.info("Deletando orçamento com ID: " + id);
        if (!orcamentoRepository.existsById(id)) {
            throw new NotFoundException("Orçamento não encontrado com ID: " + id + ", não foi possível deletar.");
        }
        orcamentoRepository.deleteById(id);
        logger.info("Orçamento com ID: " + id + " deletado com sucesso.");
    }
}