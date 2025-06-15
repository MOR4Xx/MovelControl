package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.Controller.OrcamentoController;
import com.web2.movelcontrol.Controller.PedidoController;
import com.web2.movelcontrol.DTO.OrcamentoRequestDTO;
import com.web2.movelcontrol.DTO.OrcamentoResponseDTO;
import com.web2.movelcontrol.DTO.ClienteResponseDTO;
import com.web2.movelcontrol.DTO.ItemOrcamentoResponseDTO;
import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.Orcamento;
import com.web2.movelcontrol.Model.OrcamentoItem;
import com.web2.movelcontrol.Model.Item;
import com.web2.movelcontrol.Model.Pessoa;
import com.web2.movelcontrol.Repository.OrcamentoRepository;
import com.web2.movelcontrol.Repository.ItemRepository;
import com.web2.movelcontrol.Repository.PessoaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class OrcamentoService {
    
    private final OrcamentoRepository orcamentoRepository;
    private final ItemRepository itemRepository;
    private final PessoaRepository pessoaRepository;
    private final JdbcTemplate jdbcTemplate;
    
    public OrcamentoService(OrcamentoRepository orcRepo,
                            ItemRepository itemRepo,
                            PessoaRepository pessoaRepo,
                            JdbcTemplate jdbcTemplate) {
        this.orcamentoRepository = orcRepo;
        this.itemRepository = itemRepo;
        this.pessoaRepository = pessoaRepo;
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Transactional
    public OrcamentoResponseDTO criarOrcamento(OrcamentoRequestDTO dto) {
        Orcamento novo = new Orcamento();
        if (dto.getDataCriacao() != null) {
            novo.setDataCriacao(dto.getDataCriacao());
        }
        if (dto.getStatus() != null && !dto.getStatus().isEmpty()) {
            novo.setStatus(dto.getStatus());
        }
        Pessoa cliente = pessoaRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado: " + dto.getClienteId()));
        novo.setCliente(cliente);
        
        Set<OrcamentoItem> itens = new HashSet<>();
        if (dto.getItens() != null) {
            for (var itemDto : dto.getItens()) {
                Item item = itemRepository.findById(itemDto.getItemId())
                        .orElseThrow(() -> new NotFoundException("Item não encontrado: " + itemDto.getItemId()));
                OrcamentoItem oi = new OrcamentoItem();
                oi.setOrcamento(novo);
                oi.setItem(item);
                oi.setQuantity(itemDto.getQuantity());
                itens.add(oi);
            }
        }
        novo.setItensOrcamento(itens);
        novo.calcularValorTotalOrcamento();
        Orcamento salvo = orcamentoRepository.save(novo);
        return toResponseDTO(salvo);
    }
    
    public OrcamentoResponseDTO buscarOrcamentoPorId(Long id) {
        Orcamento ent = orcamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Orçamento não encontrado: " + id));
        return toResponseDTO(ent);
    }
    
    public List<OrcamentoResponseDTO> listarTodosOrcamentos() {
        return orcamentoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public OrcamentoResponseDTO atualizarOrcamento(Long id, OrcamentoRequestDTO dto) {
        Orcamento existe = orcamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Orçamento não encontrado: " + id));
        
        if (dto.getDataCriacao() != null) {
            existe.setDataCriacao(dto.getDataCriacao());
        }
        if (dto.getStatus() != null && !dto.getStatus().isEmpty()) {
            existe.setStatus(dto.getStatus());
        }
        if (dto.getClienteId() != null &&
                !existe.getCliente().getId().equals(dto.getClienteId())) {
            Pessoa cliente = pessoaRepository.findById(dto.getClienteId())
                    .orElseThrow(() -> new NotFoundException("Cliente não encontrado: " + dto.getClienteId()));
            existe.setCliente(cliente);
        }
        
        var mapa = existe.getItensOrcamento().stream()
                .collect(Collectors.toMap(i -> i.getItem().getId(), i -> i));
        Set<OrcamentoItem> updated = new HashSet<>();
        if (dto.getItens() != null) {
            for (var itemDto : dto.getItens()) {
                var existsItem = mapa.get(itemDto.getItemId());
                if (existsItem != null) {
                    existsItem.setQuantity(itemDto.getQuantity());
                    updated.add(existsItem);
                } else {
                    Item item = itemRepository.findById(itemDto.getItemId())
                            .orElseThrow(() -> new NotFoundException("Item não encontrado: " + itemDto.getItemId()));
                    OrcamentoItem oi = new OrcamentoItem();
                    oi.setOrcamento(existe);
                    oi.setItem(item);
                    oi.setQuantity(itemDto.getQuantity());
                    updated.add(oi);
                }
            }
        }
        existe.getItensOrcamento().clear();
        existe.getItensOrcamento().addAll(updated);
        existe.calcularValorTotalOrcamento();
        Orcamento salvo = orcamentoRepository.save(existe);
        return toResponseDTO(salvo);
    }
    
    @Transactional
    public void deletarOrcamento(Long id) {
        if (!orcamentoRepository.existsById(id)) {
            throw new NotFoundException("Orçamento não encontrado: " + id);
        }
        orcamentoRepository.deleteById(id);
    }
    
    // HATEOAS manual mapping com JDBC para dados de Pessoa
    private OrcamentoResponseDTO toResponseDTO(Orcamento ent) {
        OrcamentoResponseDTO dto = new OrcamentoResponseDTO();
        dto.setId(ent.getId());
        dto.setDataCriacao(ent.getDataCriacao());
        dto.setStatus(ent.getStatus());
        dto.setValorTotal(ent.getValorTotal());
        
        // Fetch raw fields via JDBC para evitar discriminator
        var row = jdbcTemplate.queryForMap(
                "SELECT id, nome, tipo FROM pessoa WHERE id = ?", ent.getCliente().getId());
        ClienteResponseDTO clienteDto = new ClienteResponseDTO();
        clienteDto.setId(((Number) row.get("id")).longValue());
        clienteDto.setNome((String) row.get("nome"));
        clienteDto.setTipoPessoa((String) row.get("tipo"));
        dto.setCliente(clienteDto);
        
        Set<ItemOrcamentoResponseDTO> itensDto = ent.getItensOrcamento().stream()
                .map(oi -> {
                    Item item = itemRepository.findById(oi.getItem().getId())
                            .orElseThrow(() -> new NotFoundException("Item não encontrado: " + oi.getItem().getId()));
                    ItemOrcamentoResponseDTO i = new ItemOrcamentoResponseDTO();
                    i.setItemId(item.getId());
                    i.setNomeItem(item.getNome());
                    i.setDescricaoItem(item.getDescricao());
                    i.setPrecoUnitarioItem(item.getPrecoUnitario());
                    i.setQuantity(oi.getQuantity());
                    i.setSubtotal(oi.getQuantity() * item.getPrecoUnitario());
                    return i;
                })
                .collect(Collectors.toSet());
        dto.setItens(itensDto);
        
        dto.add(linkTo(methodOn(OrcamentoController.class)
                .buscarOrcamentoPorId(ent.getId()))
                .withSelfRel());
        dto.add(linkTo(methodOn(OrcamentoController.class)
                .listarTodosOrcamentos())
                .withRel("orcamentos"));
        dto.add(linkTo(methodOn(PedidoController.class)
                .criarPedido(null))
                .withRel("criar-pedido"));
        return dto;
    }
}