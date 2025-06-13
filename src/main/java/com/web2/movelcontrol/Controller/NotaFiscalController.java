/*
 * Autor: Jorge Afonso
 * Responsavel: Jorge Afonso
 */
package com.web2.movelcontrol.Controller;


import com.web2.movelcontrol.DTO.NotaFiscalRequestDTO;
import com.web2.movelcontrol.DTO.NotaFiscalResponseDTO;
import com.web2.movelcontrol.Exceptions.ErrorResponseDTO;
import com.web2.movelcontrol.Model.NotaFiscal;
import com.web2.movelcontrol.Service.NotaFiscalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nota-fiscal")
@Tag(name = "Nota Fiscal", description = "Operações relacionadas as Notas Fiscais")
public class NotaFiscalController {

    @Autowired
    private NotaFiscalService service;

    @Operation(summary = "Criar uma nova nota fiscal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nota fiscal criada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotaFiscalResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping(value = "/criar"
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotaFiscalResponseDTO> criarNotaFiscal(@RequestBody @Valid NotaFiscalRequestDTO nfRequestDTO) {

        return ResponseEntity.ok(service.create(nfRequestDTO));
    }

    @Operation(summary = "Buscar nota fiscal por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nota fiscal encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotaFiscalResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Nota fiscal não encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<NotaFiscalResponseDTO>> findById(@PathVariable Long id) {

        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Buscar todas as notas fiscais")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notas fiscais encontradas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotaFiscalResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Notas fiscais não encontradas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/listar")
    public ResponseEntity<List<EntityModel<NotaFiscalResponseDTO>>> findAll() {

        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Buscar todas as notas fiscais por pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notas fiscais encontradas"),
            @ApiResponse(responseCode = "404", description = "Notas fiscais não encontradas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/pedido/{id}")
    public ResponseEntity<NotaFiscalResponseDTO> findByPedido(@PathVariable Long id) {

        return ResponseEntity.ok(service.findByPedido(id));
    }

    @Operation(summary = "Buscar todas as notas fiscais por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notas fiscais encontradas"),
            @ApiResponse(responseCode = "404", description = "Notas fiscais não encontradas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/codigo/{id}")
    public ResponseEntity<NotaFiscalResponseDTO> findByCodigo(@PathVariable String id) {

        return ResponseEntity.ok(service.findByCodigo(id));
    }

    @Operation(summary = "Deletar uma nota fiscal por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nota fiscal deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nota fiscal não encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping(value = "/deletar/{id}")
    public NotaFiscal deletarNotaFiscal(@PathVariable Long id) {
        service.delete(id);
        return null;
    }
}
