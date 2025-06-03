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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orcamentos")
@Tag(name = "Orçamentos", description = "Endpoints para gerenciar Orçamentos")
public class OrcamentoController {
    
    @Autowired
    private OrcamentoService orcamentoService;
    
    
    @PostMapping
    @Operation(summary = "Cria um novo orçamento", // Resumo da operação
            description = "Este endpoint permite a criação de um novo orçamento no sistema. É necessário fornecer os detalhes do orçamento, incluindo o ID do cliente e os itens desejados com suas quantidades.", // Descrição mais detalhada
            tags = {"Orçamentos"}, // Reafirma a tag, útil se tiver múltiplas
            responses = {
                    @ApiResponse(description = "Orçamento Criado com Sucesso", responseCode = "201", // Descrição e código HTTP para sucesso
                            content = @Content(mediaType = "application/json", // Tipo de mídia da resposta
                                    schema = @Schema(implementation = OrcamentoResponseDTO.class) // Schema do DTO de resposta
                            )
                    ),
                    @ApiResponse(description = "Requisição Inválida", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Recurso Não Encontrado (ex: Cliente ou Item não existe)", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<OrcamentoResponseDTO> criarOrcamento(@RequestBody @Valid OrcamentoRequestDTO orcamentoDTO) {
        Orcamento orcamentoSalvo = orcamentoService.criarOrcamento(orcamentoDTO);
        OrcamentoResponseDTO responseDTO = mapToOrcamentoResponseDTO(orcamentoSalvo);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
    
    
    @GetMapping("/{id}")
    @Operation(summary = "Busca um orçamento por ID",
            description = "Retorna os detalhes de um orçamento específico com base no seu ID.",
            tags = {"Orçamentos"},
            responses = {
                    @ApiResponse(description = "Orçamento Encontrado", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrcamentoResponseDTO.class)
                            )
                    ),
                    @ApiResponse(description = "Orçamento Não Encontrado", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Requisição Inválida (ex: ID mal formatado)", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
            })
    public ResponseEntity<OrcamentoResponseDTO> buscarOrcamentoPorId(@PathVariable Long id) {
        Orcamento orcamento = orcamentoService.buscarOrcamentoPorId(id);
        OrcamentoResponseDTO responseDTO = mapToOrcamentoResponseDTO(orcamento);
        return ResponseEntity.ok(responseDTO);
    }
    
   
    @GetMapping
    @Operation(summary = "Lista todos os orçamentos",
            description = "Retorna uma lista de todos os orçamentos cadastrados no sistema.",
            tags = {"Orçamentos"},
            responses = {
                    @ApiResponse(description = "Lista de Orçamentos Obtida com Sucesso", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    // Como é uma lista, usamos arraySchema
                                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @Schema(implementation = OrcamentoResponseDTO.class))
                            )
                    ),
                    @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<List<OrcamentoResponseDTO>> listarTodosOrcamentos() {
        List<Orcamento> orcamentos = orcamentoService.listarTodosOrcamentos();
        List<OrcamentoResponseDTO> responseDTOs = orcamentos.stream()
                .map(this::mapToOrcamentoResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um orçamento existente",
            description = "Permite a atualização dos dados de um orçamento existente, identificado pelo seu ID. É necessário fornecer os novos dados do orçamento no corpo da requisição.",
            tags = {"Orçamentos"},
            responses = {
                    @ApiResponse(description = "Orçamento Atualizado com Sucesso", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrcamentoResponseDTO.class)
                            )
                    ),
                    @ApiResponse(description = "Requisição Inválida (dados do orçamento)", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Orçamento Não Encontrado", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<OrcamentoResponseDTO> atualizarOrcamento(@PathVariable Long id, @RequestBody @Valid OrcamentoRequestDTO orcamentoDTO) {
        Orcamento orcamentoAtualizado = orcamentoService.atualizarOrcamento(id, orcamentoDTO);
        OrcamentoResponseDTO responseDTO = mapToOrcamentoResponseDTO(orcamentoAtualizado);
        return ResponseEntity.ok(responseDTO);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um orçamento por ID",
            description = "Remove um orçamento do sistema com base no seu ID.",
            tags = {"Orçamentos"},
            responses = {
                    @ApiResponse(description = "Orçamento Deletado com Sucesso", responseCode = "204", content = @Content), // Sem conteúdo no corpo da resposta
                    @ApiResponse(description = "Requisição Inválida (ex: ID mal formatado)", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Orçamento Não Encontrado", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
            }
    )
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