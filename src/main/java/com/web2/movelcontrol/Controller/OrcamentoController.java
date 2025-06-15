package com.web2.movelcontrol.Controller;

import com.web2.movelcontrol.DTO.OrcamentoRequestDTO;
import com.web2.movelcontrol.DTO.OrcamentoResponseDTO;
import com.web2.movelcontrol.Service.OrcamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    
    @Operation(summary = "Cria um novo orçamento")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Orçamento criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente ou item não encontrado")
    })
    @PostMapping
    public ResponseEntity<OrcamentoResponseDTO> criarOrcamento(
            @RequestBody OrcamentoRequestDTO dto) {
        OrcamentoResponseDTO response = orcamentoService.criarOrcamento(dto);
        URI location = URI.create("/orcamentos/" + response.getId());
        return ResponseEntity.created(location).body(response);
    }
    
    @Operation(summary = "Retorna um orçamento por ID")
    @ApiResponse(responseCode = "200", description = "Orçamento encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<OrcamentoResponseDTO> buscarOrcamentoPorId(
            @PathVariable Long id) {
        OrcamentoResponseDTO response = orcamentoService.buscarOrcamentoPorId(id);
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Lista todos os orçamentos")
    @ApiResponse(responseCode = "200", description = "Lista de orçamentos retornada")
    @GetMapping
    public ResponseEntity<List<OrcamentoResponseDTO>> listarTodosOrcamentos() {
        List<OrcamentoResponseDTO> list = orcamentoService.listarTodosOrcamentos();
        return ResponseEntity.ok(list);
    }
    
    @Operation(summary = "Atualiza um orçamento existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orçamento atualizado"),
            @ApiResponse(responseCode = "404", description = "Orçamento não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<OrcamentoResponseDTO> atualizarOrcamento(
            @PathVariable Long id,
            @RequestBody OrcamentoRequestDTO dto) {
        OrcamentoResponseDTO response = orcamentoService.atualizarOrcamento(id, dto);
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Exclui um orçamento por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Orçamento excluído"),
            @ApiResponse(responseCode = "404", description = "Orçamento não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarOrcamento(
            @PathVariable Long id) {
        orcamentoService.deletarOrcamento(id);
        return ResponseEntity.noContent().build();
    }
}
