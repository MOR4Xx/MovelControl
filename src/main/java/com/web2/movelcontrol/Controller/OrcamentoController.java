/*
 * Autor: Artur Duarte
 * Responsavel: Artur Duarte
 */

package com.web2.movelcontrol.Controller;

import com.web2.movelcontrol.DTO.OrcamentoRequestDTO;
import com.web2.movelcontrol.DTO.OrcamentoResponseDTO;
import com.web2.movelcontrol.Service.OrcamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orcamentos")
@Tag(name = "Orçamentos", description = "API de orçamentos com HATEOAS habilitado")
public class OrcamentoController {
    
    private final OrcamentoService orcamentoService;
    
    public OrcamentoController(OrcamentoService orcamentoService) {
        this.orcamentoService = orcamentoService;
    }
    
    
    @PostMapping
    @Operation(summary = "Cria um novo orçamento",
            description = "Este endpoint permite a criação de um novo orçamento no sistema. É necessário fornecer os detalhes do orçamento, incluindo o ID do cliente e os itens desejados com suas quantidades.", // Descrição mais detalhada
            tags = {"Orçamentos"},
            responses = {
                    @ApiResponse(description = "Orçamento Criado com Sucesso", responseCode = "201",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrcamentoResponseDTO.class)
                            )
                    ),
                    @ApiResponse(description = "Requisição Inválida", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Recurso Não Encontrado (ex: Cliente ou Item não existe)", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<OrcamentoResponseDTO> criarOrcamento(
           @Valid @RequestBody OrcamentoRequestDTO dto) {
        OrcamentoResponseDTO response = orcamentoService.criarOrcamento(dto);
        URI location = URI.create("/orcamentos/" + response.getId());
        return ResponseEntity.created(location).body(response);
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
    public ResponseEntity<OrcamentoResponseDTO> buscarOrcamentoPorId(
            @PathVariable Long id) {
        OrcamentoResponseDTO response = orcamentoService.buscarOrcamentoPorId(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(summary = "Lista todos os orçamentos",
            description = "Retorna uma lista de todos os orçamentos cadastrados no sistema.",
            tags = {"Orçamentos"},
            responses = {
                    @ApiResponse(description = "Lista de Orçamentos Obtida com Sucesso", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @Schema(implementation = OrcamentoResponseDTO.class))
                            )
                    ),
                    @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<List<OrcamentoResponseDTO>> listarTodosOrcamentos() {
        List<OrcamentoResponseDTO> list = orcamentoService.listarTodosOrcamentos();
        return ResponseEntity.ok(list);
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
    public ResponseEntity<OrcamentoResponseDTO> atualizarOrcamento(
            @PathVariable Long id,
            @RequestBody OrcamentoRequestDTO dto) {
        OrcamentoResponseDTO response = orcamentoService.atualizarOrcamento(id, dto);
        return ResponseEntity.ok(response);
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
    public ResponseEntity<Void> deletarOrcamento(
            @PathVariable Long id) {
        orcamentoService.deletarOrcamento(id);
        return ResponseEntity.noContent().build();
    }
}
