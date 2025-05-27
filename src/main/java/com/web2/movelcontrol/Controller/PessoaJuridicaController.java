package com.web2.movelcontrol.Controller;

import com.web2.movelcontrol.DTO.*;
import com.web2.movelcontrol.Model.Endereco;
import com.web2.movelcontrol.Model.Pessoa;
import com.web2.movelcontrol.Model.PessoaJuridica;
import com.web2.movelcontrol.Model.Usuario;
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
            summary = "Buscar Pessoa Juridica por ID",
            description = "Retorna os dados de uma Pessoa Juridica específico pelo seu ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pessoa Juridica encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PessoaJuridicaResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Pessoa Juridica não encontrado")
            }
    )
    @PostMapping(value = "/criar",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PessoaJuridicaResponseDTO> createPessoaJuridica(@RequestBody @Valid PessoaJuridicaRequestDTO requestDTO) {
        PessoaJuridica pjSalva = new PessoaJuridica();
        pjSalva.setNome(requestDTO.getNome());
        pjSalva.setTelefone(requestDTO.getTelefone());
        pjSalva.setCnpj(requestDTO.getCnpj());
        pjSalva.setEmail(requestDTO.getEmail());

        Endereco endereco = new Endereco();
        endereco.setCep(requestDTO.getEndereco().getCep());
        endereco.setRua(requestDTO.getEndereco().getRua());
        endereco.setBairro(requestDTO.getEndereco().getBairro());
        endereco.setNumero(requestDTO.getEndereco().getNumero());
        endereco.setComplemento(requestDTO.getEndereco().getComplemento());

        pjSalva.setEndereco(endereco);

        service.create(pjSalva);

        PessoaJuridicaResponseDTO responseDTO = DataMapper.parseObject(pjSalva, PessoaJuridicaResponseDTO.class);

        return ResponseEntity.ok(responseDTO);
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
                    content = @Content(schema = @Schema(implementation = PessoaJuridica.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pessoa Jurídica atualizada com sucesso",
                            content = @Content(schema = @Schema(implementation = PessoaJuridica.class))),
                    @ApiResponse(responseCode = "404", description = "Pessoa Jurídica não encontrada"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PutMapping(value = "/atualizar/{id}"
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public PessoaJuridica atualizarPessoaJuridica(@PathVariable Long id, @RequestBody PessoaJuridica pj) {
        return service.update(id, pj);
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
