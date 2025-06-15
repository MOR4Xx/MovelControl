package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.Controller.OrcamentoController;
import com.web2.movelcontrol.Controller.PedidoController;
import com.web2.movelcontrol.DTO.OrcamentoRequestDTO;
import com.web2.movelcontrol.DTO.OrcamentoResponseDTO;
import com.web2.movelcontrol.DTO.DataMapper;
import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.Orcamento;
import com.web2.movelcontrol.Model.OrcamentoItem;
import com.web2.movelcontrol.Model.Item;
import com.web2.movelcontrol.Model.Pessoa;
import com.web2.movelcontrol.Repository.OrcamentoRepository;
import com.web2.movelcontrol.Repository.ItemRepository;
import com.web2.movelcontrol.Repository.PessoaRepository;
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
    private final DataMapper dataMapper;
    
    public OrcamentoService(OrcamentoRepository orcRepo,
                            ItemRepository itemRepo,
                            PessoaRepository pessoaRepo,
                            DataMapper dataMapper) {
        this.orcamentoRepository = orcRepo;
        this.itemRepository = itemRepo;
        this.pessoaRepository = pessoaRepo;
        this.dataMapper = dataMapper;
    }
    
    @Transactional
    public OrcamentoResponseDTO criarOrcamento(OrcamentoRequestDTO dto) {
        Orcamento novo = dataMapper.parseObject(dto, Orcamento.class);
        
        Pessoa cliente = pessoaRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new NotFoundException(
                        "Cliente não encontrado: " + dto.getClienteId()));
        novo.setCliente(cliente);
        
        // Mapeia itens manualmente (entidade JPA)
        Set<OrcamentoItem> itens = new HashSet<>();
        if (dto.getItens() != null) {
            for (var itemDto : dto.getItens()) {
                Item item = itemRepository.findById(itemDto.getItemId())
                        .orElseThrow(() -> new NotFoundException(
                                "Item não encontrado: " + itemDto.getItemId()));
                OrcamentoItem oi = new OrcamentoItem();
                oi.setOrcamento(novo);
                oi.setItem(item);
                oi.setQuantity(itemDto.getQuantity());
                itens.add(oi);
            }
        }
        novo.setItensOrcamento(itens);
        
        // Calcula valor total
        novo.calcularValorTotalOrcamento();
        
        // Persiste e retorna DTO com _links
        Orcamento salvo = orcamentoRepository.save(novo);
        return montarResponseComLinks(salvo);
    }
    
    public OrcamentoResponseDTO buscarOrcamentoPorId(Long id) {
        Orcamento ent = orcamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Orçamento não encontrado: " + id));
        return montarResponseComLinks(ent);
    }
    
    public List<OrcamentoResponseDTO> listarTodosOrcamentos() {
        return orcamentoRepository.findAll()
                .stream()
                .map(this::montarResponseComLinks)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public OrcamentoResponseDTO atualizarOrcamento(Long id, OrcamentoRequestDTO dto) {
        Orcamento existe = orcamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Orçamento não encontrado: " + id));
        atualizarEntidade(existe, dto);
        Orcamento salvo = orcamentoRepository.save(existe);
        return montarResponseComLinks(salvo);
    }
    
    @Transactional
    public void deletarOrcamento(Long id) {
        if (!orcamentoRepository.existsById(id)) {
            throw new NotFoundException(
                    "Orçamento não encontrado: " + id);
        }
        orcamentoRepository.deleteById(id);
    }
    
    private void atualizarEntidade(Orcamento existe, OrcamentoRequestDTO dto) {
        if (dto.getDataCriacao() != null) existe.setDataCriacao(dto.getDataCriacao());
        if (dto.getStatus() != null && !dto.getStatus().isEmpty()) existe.setStatus(dto.getStatus());
        if (dto.getClienteId() != null && !existe.getCliente().getId().equals(dto.getClienteId())) {
            Pessoa cliente = pessoaRepository.findById(dto.getClienteId())
                    .orElseThrow(() -> new NotFoundException(
                            "Cliente não encontrado: " + dto.getClienteId()));
            existe.setCliente(cliente);
        }
        var mapa = existe.getItensOrcamento().stream()
                .collect(Collectors.toMap(i -> i.getItem().getId(), i -> i));
        Set<OrcamentoItem> novos = new HashSet<>();
        if (dto.getItens() != null) {
            for (var itemDto : dto.getItens()) {
                var existent = mapa.get(itemDto.getItemId());
                if (existent != null) {
                    existent.setQuantity(itemDto.getQuantity());
                    novos.add(existent);
                } else {
                    Item item = itemRepository.findById(itemDto.getItemId())
                            .orElseThrow(() -> new NotFoundException(
                                    "Item não encontrado: " + itemDto.getItemId()));
                    OrcamentoItem oi = new OrcamentoItem();
                    oi.setOrcamento(existe);
                    oi.setItem(item);
                    oi.setQuantity(itemDto.getQuantity());
                    novos.add(oi);
                }
            }
        }
        existe.getItensOrcamento().clear();
        existe.getItensOrcamento().addAll(novos);
        existe.calcularValorTotalOrcamento();
    }
    
    // Converte entidade para DTO usando DataMapper e adiciona HATEOAS links
    private OrcamentoResponseDTO montarResponseComLinks(Orcamento entity) {
        OrcamentoResponseDTO dto = dataMapper.parseObject(entity, OrcamentoResponseDTO.class);
        dto.add(linkTo(methodOn(OrcamentoController.class)
                .buscarOrcamentoPorId(entity.getId()))
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
