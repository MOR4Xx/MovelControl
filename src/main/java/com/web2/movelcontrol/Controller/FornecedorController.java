package com.web2.movelcontrol.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.web2.movelcontrol.DTO.FornecedorRequestDTO;
import com.web2.movelcontrol.DTO.FornecedorResponseDTO;
import com.web2.movelcontrol.Service.FornecedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/fornecedor")
public class FornecedorController {

    @Autowired
    FornecedorService service;

    @Operation(summary = "Cria um novo fornecedor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fornecedor criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping(value = "/criar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public FornecedorResponseDTO createFornecedor(@RequestBody @Valid FornecedorRequestDTO fornecedor) {
        return service.create(fornecedor);
    }

    @Operation(summary = "Deleta um fornecedor pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Fornecedor deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado")
    })
    @DeleteMapping(value = "/deletar/{id}")
    public void deleteFornecedor(@PathVariable Long id) {
        service.delete(id);
    }

    @Operation(summary = "Atualiza um fornecedor pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fornecedor atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado")
    })
    @PutMapping("/atualizar/{id}")
    public FornecedorResponseDTO atualizarFornecedor(@PathVariable Long id, @Valid @RequestBody FornecedorRequestDTO pf){
        return service.update(id, pf);
    }

    @Operation(summary = "Busca um fornecedor pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fornecedor encontrado"),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado")
    })
    @GetMapping("/buscar/{id}")
    public FornecedorResponseDTO buscarFornecedor(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Busca todos os fornecedores")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de fornecedores retornada com sucesso")
    })
    @GetMapping("/buscartudo")
    public List<FornecedorResponseDTO> buscarFornecedorTudo() {
        return service.findAll();
    }
}
