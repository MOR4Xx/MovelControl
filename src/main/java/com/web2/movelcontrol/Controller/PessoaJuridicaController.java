package com.web2.movelcontrol.Controller;

import com.web2.movelcontrol.DTO.DataMapper;
import com.web2.movelcontrol.DTO.PessoaJuridicaRequestDTO;
import com.web2.movelcontrol.DTO.PessoaJuridicaResponseDTO;
import com.web2.movelcontrol.Exceptions.ErrorResponseDTO;
import com.web2.movelcontrol.Model.Endereco;
import com.web2.movelcontrol.Model.PessoaJuridica;
import com.web2.movelcontrol.Service.PessoaJuridicaService;
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
@RequestMapping("/pj")
@Tag(name = "Pessoa Juridica", description = "Operações relacionadas as Pessoas Juridicas")
public class PessoaJuridicaController {
    @Autowired
    PessoaJuridicaService service;

    @Operation(
            summary = "Cria Pessoa Jurídica",
            description = "Cria uma nova Pessoa Jurídica e retorna os dados da entidade criada.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pessoa Jurídica criada com sucesso.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PessoaJuridicaResponseDTO.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida devido a dados de entrada incorretos ou incompletos.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PessoaJuridicaRequestDTO.class))
                    ),
                    @ApiResponse(responseCode = "409", description = "Conflito. A Pessoa Jurídica com o CNPJ fornecido já existe.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PessoaJuridicaRequestDTO.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    )
            }
    )
    @PostMapping(value = "/criar",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PessoaJuridicaResponseDTO> createPessoaJuridica(@RequestBody @Valid PessoaJuridicaRequestDTO requestDTO) {
        PessoaJuridica pj = new PessoaJuridica();
        pj.setNome(requestDTO.getNome());
        pj.setTelefone(requestDTO.getTelefone());
        pj.setCnpj(requestDTO.getCnpj());
        pj.setEmail(requestDTO.getEmail());

        Endereco endereco = new Endereco();
        endereco.setCep(requestDTO.getEndereco().getCep());
        endereco.setRua(requestDTO.getEndereco().getRua());
        endereco.setBairro(requestDTO.getEndereco().getBairro());
        endereco.setNumero(requestDTO.getEndereco().getNumero());
        endereco.setComplemento(requestDTO.getEndereco().getComplemento());

        pj.setEndereco(endereco);

        service.create(pj);

        return ResponseEntity.ok(DataMapper.parseObject(pj, PessoaJuridicaResponseDTO.class));
    }

    @Operation(
            summary = "Buscar Pessoa Juridica por ID",
            description = "Retorna os dados de uma Pessoa Juridica específico pelo seu ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pessoa Juridica encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PessoaJuridicaResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Pessoa Juridica não encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PessoaJuridicaRequestDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PessoaJuridicaResponseDTO> findById(@PathVariable Long id) {
        PessoaJuridica pj = service.findById(id);

        return ResponseEntity.ok(DataMapper.parseObject(pj, PessoaJuridicaResponseDTO.class));
    }

    @Operation(
            summary = "Atualizar Pessoa Jurídica",
            description = "Atualiza os dados de uma Pessoa Jurídica existente pelo ID",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados da Pessoa Jurídica",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PessoaJuridicaResponseDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pessoa Jurídica atualizada com sucesso",
                            content = @Content(schema = @Schema(implementation = PessoaJuridicaResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Pessoa Jurídica não encontrada"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida devido a dados de entrada incorretos ou incompletos.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PessoaJuridicaRequestDTO.class))
                    )
            }
    )
    @PutMapping(value = "/atualizar/{id}"
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public PessoaJuridica atualizarPessoaJuridica(@PathVariable Long id, @RequestBody PessoaJuridicaRequestDTO pjDTO) {
        PessoaJuridica pessoaJuridica = DataMapper.parseObject(pjDTO, PessoaJuridica.class);

        return service.update(id, pessoaJuridica);
    }

    @Operation(
            summary = "Deletar Pessoa Jurídica",
            description = "Remove uma Pessoa Jurídica do sistema pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Pessoa Jurídica deletada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Pessoa Jurídica não encontrada")
            }
    )
    @DeleteMapping(value = "/deletar/{id}")
    public void deletePessoaJuridica(@PathVariable Long id) {
        service.delete(id);
    }
}
