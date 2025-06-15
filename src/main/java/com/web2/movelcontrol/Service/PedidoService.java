package com.web2.movelcontrol.Service;
/*
 * Autor: Artur Duarte
 * Responsavel: Artur Duarte
 */

import com.web2.movelcontrol.Controller.OrcamentoController;
import com.web2.movelcontrol.Controller.PedidoController;
import com.web2.movelcontrol.DTO.PedidoRequestDTO;
import com.web2.movelcontrol.DTO.PedidoResponseDTO;
import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.Pedido;
import com.web2.movelcontrol.Model.Orcamento;
import com.web2.movelcontrol.Repository.PedidoRepository;
import com.web2.movelcontrol.Repository.OrcamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PedidoService {
    
    private final PedidoRepository pedidoRepository;
    private final OrcamentoRepository orcamentoRepository;
    
    public PedidoService(PedidoRepository pedidoRepo,
                         OrcamentoRepository orcRepo) {
        this.pedidoRepository = pedidoRepo;
        this.orcamentoRepository = orcRepo;
    }
    
    @Transactional
    public PedidoResponseDTO criarPedido(PedidoRequestDTO dto) {
        Orcamento orçamento = orcamentoRepository.findById(dto.getOrcamentoId())
                .orElseThrow(() -> new NotFoundException("Orçamento não encontrado: " + dto.getOrcamentoId()));
        
        Pedido novo = new Pedido();
        novo.setData_pedido(dto.getDataPedido());
        novo.setStatus(dto.getStatus());
        novo.setDescricao(dto.getDescricao());
        novo.setOrcamento(orçamento);
        
        Pedido salvo = pedidoRepository.save(novo);
        return toResponseDTO(salvo);
    }
    
    public PedidoResponseDTO buscarPedidoPorId(Long id) {
        Pedido p = pedidoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado: " + id));
        return toResponseDTO(p);
    }
    
    public List<PedidoResponseDTO> listarTodosPedidos() {
        return pedidoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public PedidoResponseDTO atualizarPedido(Long id, PedidoRequestDTO dto) {
        Pedido existente = pedidoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado: " + id));
        
        existente.setData_pedido(dto.getDataPedido());
        existente.setStatus(dto.getStatus());
        existente.setDescricao(dto.getDescricao());
        
        if (!existente.getOrcamento().getId().equals(dto.getOrcamentoId())) {
            Orcamento novoOrc = orcamentoRepository.findById(dto.getOrcamentoId())
                    .orElseThrow(() -> new NotFoundException("Orçamento não encontrado: " + dto.getOrcamentoId()));
            existente.setOrcamento(novoOrc);
        }
        
        Pedido salvo = pedidoRepository.save(existente);
        return toResponseDTO(salvo);
    }
    
    @Transactional
    public void deletarPedido(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new NotFoundException("Pedido não encontrado: " + id);
        }
        pedidoRepository.deleteById(id);
    }
    
    // --- Mapeamento manual para DTO + HATEOAS ---
    private PedidoResponseDTO toResponseDTO(Pedido p) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(p.getId());
        dto.setDataPedido(p.getData_pedido());
        dto.setStatus(p.getStatus());
        dto.setDescricao(p.getDescricao());
        dto.setOrcamentoId(p.getOrcamento().getId());
        
        // self-link
        dto.add(linkTo(methodOn(PedidoController.class)
                .buscarPedidoPorId(p.getId()))
                .withSelfRel());
        // coleção
        dto.add(linkTo(methodOn(PedidoController.class)
                .listarTodosPedidos())
                .withRel("pedidos"));
        // link para o orçamento associado
        dto.add(linkTo(methodOn(OrcamentoController.class)
                .buscarOrcamentoPorId(p.getOrcamento().getId()))
                .withRel("orcamento"));
        
        return dto;
    }
}
