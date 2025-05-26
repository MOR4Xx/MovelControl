package com.web2.movelcontrol.Controller;

import com.web2.movelcontrol.DTO.PessoaJuridicaResponseDTO;
import com.web2.movelcontrol.DTO.UsuarioResponseDTO;
import com.web2.movelcontrol.Model.PessoaJuridica;
import com.web2.movelcontrol.Service.PessoaJuridicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pj")
@Tag(name = "Pessoa Juridica", description = "Operações relacionadas as Pessoas Juridicas")
public class PessoaJuridicaController {
    @Autowired
    PessoaJuridicaService service;

    @Operation(
            summary = "Cadastrar uma nova Pessoa Juridica",
            description = "Cria uma Pessoa Juridica no sistema com os dados informados",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pessoa Juridica criado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PessoaJuridicaResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PostMapping(value = "/criar",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public PessoaJuridica createPessoaJuridica(@RequestBody PessoaJuridica pj) {
        return service.create(pj);
    }

    @Operation(
            summary = "Buscar Pessoa Juridica por ID",
            description = "Retorna os dados de uma Pessoa Juridica específico pelo seu ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pessoa Juridica encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Pessoa Juridica não encontrado")
            }
    )
    @GetMapping("/{id}")
    public PessoaJuridica findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping(value = "/atualizar/{id}"
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public PessoaJuridica atualizarPessoaJuridica(@PathVariable Long id, @RequestBody PessoaJuridica pj) {
        return service.update(id, pj);
    }

    @DeleteMapping(value = "/deletar/{id}")
    public void deletePessoaJuridica(@PathVariable Long id) {
        service.delete(id);
    }
}
