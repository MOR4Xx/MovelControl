package com.web2.movelcontrol.Controller;

import com.web2.movelcontrol.DTO.*;
import com.web2.movelcontrol.Exceptions.ErrorResponseDTO;
import com.web2.movelcontrol.Model.Endereco;
import com.web2.movelcontrol.Model.PessoaFisica;
import com.web2.movelcontrol.Service.PessoaFisicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        PessoaFisica pf = new PessoaFisica();
        pf.setNome(pfDTO.getNome());
        pf.setTelefone(pfDTO.getTelefone());
        pf.setCpf(pfDTO.getCpf());
        pf.setEmail(pfDTO.getEmail());

        Endereco endereco = new Endereco();
        endereco.setCep(pfDTO.getEndereco().getCep());
        endereco.setRua(pfDTO.getEndereco().getRua());
        endereco.setBairro(pfDTO.getEndereco().getBairro());
        endereco.setNumero(pfDTO.getEndereco().getNumero());
        endereco.setComplemento(pfDTO.getEndereco().getComplemento());

        pf.setEndereco(endereco);

        service.create(pf);

        return ResponseEntity.ok(DataMapper.parseObject(pf, PessoaFisicaResponseDTO.class));
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
    public ResponseEntity<PessoaFisicaResponseDTO> findById(@PathVariable Long id) {
        PessoaFisica pessoaFisica = service.findById(id);

        return ResponseEntity.ok(DataMapper.parseObject(pessoaFisica, PessoaFisicaResponseDTO.class));
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
                    @ApiResponse(responseCode = "404", description = "Pessoa Jurídica não encontrada"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PutMapping(value = "/atualizar/{id}"
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public PessoaFisica atualizarPessoaFisica(@PathVariable Long id, @RequestBody PessoaFisicaRequestDTO pfAntigo){
        PessoaFisica pessoaFisica = DataMapper.parseObject(pfAntigo, PessoaFisica.class);

        return service.update(id, pessoaFisica);
    }


    @DeleteMapping(value = "/deletar/{id}")
    public void deletePessoaFIsica(@PathVariable Long id) {
        service.delete(id);
    }
}
