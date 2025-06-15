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
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        
        logger.info(
                String.format("Iniciando criação de orçamento para Cliente ID: %d com %d item(ns).",
                        orcamentoDTO.getClienteId(),
                        orcamentoDTO.getItens() != null ? orcamentoDTO.getItens().size() : 0
                )
        );
        Orcamento novoOrcamento = new Orcamento();
        
        if (orcamentoDTO.getDataCriacao() != null) {
            novoOrcamento.setDataCriacao(orcamentoDTO.getDataCriacao());
        }
        
        if (orcamentoDTO.getStatus() != null && !orcamentoDTO.getStatus().isEmpty()) {
            novoOrcamento.setStatus(orcamentoDTO.getStatus());
        }
        
        Pessoa cliente = pessoaRepository.findById(orcamentoDTO.getClienteId())
                .orElseThrow(() -> new NotFoundException("Recurso não encontrado: Cliente com ID " + orcamentoDTO.getClienteId()));
        novoOrcamento.setCliente(cliente);
        
        Set<OrcamentoItem> itensProcessados = new HashSet<>();
        if (orcamentoDTO.getItens() != null && !orcamentoDTO.getItens().isEmpty()) {
            for (ItemOrcamentoRequestDTO itemDto : orcamentoDTO.getItens()) {
                Item itemDoBanco = itemRepository.findById(itemDto.getItemId())
                        .orElseThrow(() -> new NotFoundException("Recurso não encontrado: Item com ID " + itemDto.getItemId()));
                
                OrcamentoItem novoOrcamentoItem = new OrcamentoItem();
                novoOrcamentoItem.setOrcamento(novoOrcamento);
                novoOrcamentoItem.setItem(itemDoBanco);
                novoOrcamentoItem.setQuantity(itemDto.getQuantity());
                itensProcessados.add(novoOrcamentoItem);
            }
        }
        novoOrcamento.setItensOrcamento(itensProcessados);
        
        novoOrcamento.calcularValorTotalOrcamento();
        
        Orcamento orcamentoSalvo = orcamentoRepository.save(novoOrcamento);
        
        logger.info(
                String.format("Orçamento ID: %d criado com sucesso para o Cliente ID: %d. Valor Total: R$ %.2f",
                        orcamentoSalvo.getId(),
                        orcamentoSalvo.getCliente().getId(),
                        orcamentoSalvo.getValorTotal()
                )
        );
        
        
        return orcamentoSalvo;
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
    public Orcamento atualizarOrcamento(Long id, OrcamentoRequestDTO requestDTO) { // o nome do parâmetro é requestDTO aqui
        logger.info("Atualizando orçamento com ID: " + id + " usando lógica de sincronização de itens.");
        Orcamento orcamentoExistente = orcamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Orçamento com ID " + id + " não encontrado, não foi possível atualizar."));
        
        // Atualiza campos simples do Orçamento
        if (requestDTO.getDataCriacao() != null) {
            orcamentoExistente.setDataCriacao(requestDTO.getDataCriacao());
        }
        if (requestDTO.getStatus() != null && !requestDTO.getStatus().isEmpty()) {
            orcamentoExistente.setStatus(requestDTO.getStatus());
        }
        
        // Atualiza Cliente
        if (requestDTO.getClienteId() != null) {
            if (orcamentoExistente.getCliente() == null || !orcamentoExistente.getCliente().getId().equals(requestDTO.getClienteId())) {
                Pessoa cliente = pessoaRepository.findById(requestDTO.getClienteId())
                        .orElseThrow(() -> new NotFoundException("Cliente com ID " + requestDTO.getClienteId() + " não encontrado."));
                orcamentoExistente.setCliente(cliente);
            }
        } else {
            orcamentoExistente.setCliente(null);
        }
        
        // Mapeia os OrcamentoItems existentes pelo ID do Item para fácil acesso
        Map<Long, OrcamentoItem> itensExistentesNoMapa = new HashSet<>(orcamentoExistente.getItensOrcamento())
                .stream()
                .collect(Collectors.toMap(
                        oi -> oi.getItem().getId(),
                        Function.identity()
                ));
        
        // Cria uma nova coleção para os itens que devem permanecer/ser adicionados
        Set<OrcamentoItem> itensAtualizadosDaRequisicao = new HashSet<>();
        
        if (requestDTO.getItens() != null) {
            for (ItemOrcamentoRequestDTO itemDto : requestDTO.getItens()) {
                OrcamentoItem itemExistente = itensExistentesNoMapa.get(itemDto.getItemId());
                
                if (itemExistente != null) {
                    //O item já existe. Apenas atualiza a quantidade.
                    itemExistente.setQuantity(itemDto.getQuantity());
                    itensAtualizadosDaRequisicao.add(itemExistente); // Re-adiciona o item atualizado
                } else {
                    //O item é novo. Busca no banco e cria a nova associação.
                    Item itemDoBanco = itemRepository.findById(itemDto.getItemId())
                            .orElseThrow(() -> new NotFoundException("Item com ID " + itemDto.getItemId() + " não encontrado."));
                    
                    OrcamentoItem novoOrcamentoItem = new OrcamentoItem();
                    novoOrcamentoItem.setOrcamento(orcamentoExistente);
                    novoOrcamentoItem.setItem(itemDoBanco);
                    novoOrcamentoItem.setQuantity(itemDto.getQuantity());
                    
                    itensAtualizadosDaRequisicao.add(novoOrcamentoItem); // Adiciona o novo item
                }
            }
        }
        
        // Substitui a coleção antiga pela nova (já atualizada/com novos itens)
        // O orphanRemoval=true cuida de remover do banco os itens que não estão mais na nova coleção
        orcamentoExistente.getItensOrcamento().clear();
        orcamentoExistente.getItensOrcamento().addAll(itensAtualizadosDaRequisicao);
        
        // Recalcula o valor total do orçamento
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