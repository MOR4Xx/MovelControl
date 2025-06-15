/*
 * Autor: Jorge Afonso
 * Responsavel: Jorge Afonso
 */

package com.web2.movelcontrol.Controller;

import com.web2.movelcontrol.DTO.PessoaFisicaRequestDTO;
import com.web2.movelcontrol.DTO.PessoaFisicaResponseDTO;
import com.web2.movelcontrol.Exceptions.ErrorResponseDTO;
import com.web2.movelcontrol.Model.PessoaFisica;
import com.web2.movelcontrol.Service.PessoaFisicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pf")
@Tag(name = "Pessoa Fisicas", description = "Operações relacionadas as Pessoas Fisicas")
public class PessoaFisicaController {

    @Autowired
    PessoaFisicaService service;

    @Operation(
            summary = "Cria Pessoa Jurídica",
            description = "Cria uma nova Pessoa Jurídica e retorna os dados da entidade criada.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pessoa Jurídica criada com sucesso.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PessoaFisicaResponseDTO.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida devido a dados de entrada incorretos ou incompletos.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PessoaFisicaRequestDTO.class))
                    ),
                    @ApiResponse(responseCode = "409", description = "Conflito. A Pessoa Fisica com o CPF fornecido já existe.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PessoaFisicaRequestDTO.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    )
            }
    )
    @PostMapping(value = "/criar",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PessoaFisicaResponseDTO> createPessoaFisica(@RequestBody @Valid PessoaFisicaRequestDTO pfDTO) {

        return ResponseEntity.ok(service.create(pfDTO));
    }

    @Operation(
            summary = "Buscar Pessoa Fisica por ID",
            description = "Retorna os dados de uma Pessoa Juridica específico pelo seu ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pessoa Juridica encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PessoaFisicaResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Pessoa Juridica não encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PessoaFisicaResponseDTO>> findById(@PathVariable Long id) {

        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(
            summary = "Lista todas as Pessoas Fisicas",
            description = "Retorna todas as Pessoas Fisicas registrada",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pessoas Fisicas encontrado"),
                    @ApiResponse(responseCode = "404", description = "Pessoas Fisicas não encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)))
            }
    )
    @GetMapping("/listar")
    public ResponseEntity<List<EntityModel<PessoaFisicaResponseDTO>>> findAll() {

        return ResponseEntity.ok(service.findAll());
    }

    @Operation(
            summary = "Buscar Pessoa Fisica por Nome",
            description = "Retorna os dados de uma Pessoa Fisica específico pelo Nome",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pessoa Fisica encontrado"),
                    @ApiResponse(responseCode = "404", description = "Pessoa Fisica não encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)))
            }
    )
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<EntityModel<PessoaFisicaResponseDTO>>> findByNome(@PathVariable String nome) {

        return ResponseEntity.ok(service.findByNome(nome));
    }

    @Operation(
            summary = "Atualizar Pessoa Jurídica",
            description = "Atualiza os dados de uma Pessoa Jurídica existente pelo ID",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados da Pessoa Jurídica",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PessoaFisicaResponseDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pessoa Jurídica atualizada com sucesso",
                            content = @Content(schema = @Schema(implementation = PessoaFisicaResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Pessoa Jurídica não encontrada",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)))
            }
    )
    @PutMapping(value = "/atualizar/{id}"
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PessoaFisicaResponseDTO> atualizarPessoaFisica(@PathVariable Long id, @RequestBody PessoaFisicaRequestDTO pf) {

        return ResponseEntity.ok(service.update(id, pf));
    }

    @Operation(summary = "Deletar Pessoa Fisica", description = "Remove uma Pessoa Fisica do sistema pelo ID", responses = {
            @ApiResponse(responseCode = "204", description = "Pessoa Fisica deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pessoa Fisica não encontrada")
    })
    @DeleteMapping(value = "/deletar/{id}")
    public PessoaFisica deletePessoaFisica(@PathVariable Long id) {
        service.delete(id);
        return null;
    }
}
