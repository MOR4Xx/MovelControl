package com.web2.movelcontrol.Controller;

import com.web2.movelcontrol.DTO.OrcamentoRequestDTO;
import com.web2.movelcontrol.DTO.OrcamentoResponseDTO;
import com.web2.movelcontrol.DTO.ClienteResponseDTO;
import com.web2.movelcontrol.DTO.ItemOrcamentoResponseDTO;
import com.web2.movelcontrol.DTO.DataMapper;
import com.web2.movelcontrol.Model.Orcamento;
import com.web2.movelcontrol.Model.PessoaFisica;
import com.web2.movelcontrol.Model.PessoaJuridica;
import com.web2.movelcontrol.Service.OrcamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orcamentos")

public class OrcamentoController {
    
    @Autowired
    private OrcamentoService orcamentoService;
    
    
    @PostMapping
    @Operation(summary = "Cria um novo orçamento", responses = {
            @ApiResponse(responseCode = "201", description = "Orçamento criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrcamentoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos")
    })
    public ResponseEntity<OrcamentoResponseDTO> criarOrcamento(@RequestBody @Valid OrcamentoRequestDTO orcamentoDTO) {
        Orcamento orcamentoSalvo = orcamentoService.criarOrcamento(orcamentoDTO);
        OrcamentoResponseDTO responseDTO = mapToOrcamentoResponseDTO(orcamentoSalvo);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
    
    
    @GetMapping("/{id}")
    @Operation(summary = "Busca um orçamento por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Orçamento encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrcamentoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Orçamento não encontrado")
    })
    public ResponseEntity<OrcamentoResponseDTO> buscarOrcamentoPorId(@PathVariable Long id) {
        Orcamento orcamento = orcamentoService.buscarOrcamentoPorId(id);
        OrcamentoResponseDTO responseDTO = mapToOrcamentoResponseDTO(orcamento);
        return ResponseEntity.ok(responseDTO);
    }
    
   
    @GetMapping
    @Operation(summary = "Lista todos os orçamentos", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de orçamentos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrcamentoResponseDTO.class)))
    })
    public ResponseEntity<List<OrcamentoResponseDTO>> listarTodosOrcamentos() {
        List<Orcamento> orcamentos = orcamentoService.listarTodosOrcamentos();
        List<OrcamentoResponseDTO> responseDTOs = orcamentos.stream()
                .map(this::mapToOrcamentoResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um orçamento existente", responses = {
            @ApiResponse(responseCode = "200", description = "Orçamento atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrcamentoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Orçamento não encontrado")
    })
    public ResponseEntity<OrcamentoResponseDTO> atualizarOrcamento(@PathVariable Long id, @RequestBody @Valid OrcamentoRequestDTO orcamentoDTO) {
        Orcamento orcamentoAtualizado = orcamentoService.atualizarOrcamento(id, orcamentoDTO);
        OrcamentoResponseDTO responseDTO = mapToOrcamentoResponseDTO(orcamentoAtualizado);
        return ResponseEntity.ok(responseDTO);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um orçamento por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Orçamento deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Orçamento não encontrado")
    })
    public ResponseEntity<Void> deletarOrcamento(@PathVariable Long id) {
        orcamentoService.deletarOrcamento(id);
        return ResponseEntity.noContent().build();
    }
    
    
    private OrcamentoResponseDTO mapToOrcamentoResponseDTO(Orcamento orcamento) {
        if (orcamento == null) {
            return null;
        }
        // Usando DataMapper para os campos diretos
        OrcamentoResponseDTO dto = DataMapper.parseObject(orcamento, OrcamentoResponseDTO.class);
        
        // Mapeamento customizado para o cliente
        if (orcamento.getCliente() != null) {
            ClienteResponseDTO clienteDto = new ClienteResponseDTO();
            clienteDto.setId(orcamento.getCliente().getId());
            clienteDto.setNome(orcamento.getCliente().getNome());
            // Determinar tipoPessoa
            if (orcamento.getCliente() instanceof PessoaFisica) {
                clienteDto.setTipoPessoa("FISICA");
            } else if (orcamento.getCliente() instanceof PessoaJuridica) {
                clienteDto.setTipoPessoa("JURIDICA");
            }
            dto.setCliente(clienteDto);
        }
        
        // Mapeamento customizado para os itens do orçamento
        if (orcamento.getItensOrcamento() != null) {
            dto.setItens(
                    orcamento.getItensOrcamento().stream().map(orcamentoItem -> {
                        ItemOrcamentoResponseDTO itemDto = new ItemOrcamentoResponseDTO();
                        itemDto.setItemId(orcamentoItem.getItem().getId());
                        itemDto.setNomeItem(orcamentoItem.getItem().getNome());
                        itemDto.setDescricaoItem(orcamentoItem.getItem().getDescricao());
                        itemDto.setPrecoUnitarioItem(orcamentoItem.getItem().getPrecoUnitario());
                        itemDto.setQuantity(orcamentoItem.getQuantity());
                        itemDto.setSubtotal(orcamentoItem.getSubtotal());
                        return itemDto;
                    }).collect(Collectors.toSet())
            );
        }
       
        return dto;
    }
}