package com.web2.movelcontrol.Controller;

import com.web2.movelcontrol.DTO.PedidoFornecedorRequestDTO;
import com.web2.movelcontrol.DTO.PedidoFornecedorResponseDTO;
import com.web2.movelcontrol.Model.PedidoFornecedor;
import com.web2.movelcontrol.Service.PedidoFornecedorService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/pFornecedor")
public class PedidoFornecedorController {

    @Autowired
    private PedidoFornecedorService service;

    @Operation(summary = "Cria um novo pedido de fornecedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping(value = "/criar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PedidoFornecedor> criarPedidoFornecedor(@Valid @RequestBody PedidoFornecedorRequestDTO dto) {
        PedidoFornecedor novoPedido = service.create(dto);
        return ResponseEntity.ok(novoPedido);
    }

    @Operation(summary = "Atualiza um pedido de fornecedor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<PedidoFornecedor> atualizarPedidoFornecedor(@PathVariable Long id,
            @Valid @RequestBody PedidoFornecedorRequestDTO dto) {
        PedidoFornecedor atualizado = service.update(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @Operation(summary = "Deleta um pedido de fornecedor pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletePedidoFornecedor(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Lista todos os pedidos de fornecedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    })
    @GetMapping("/buscartudo")
    public ResponseEntity<CollectionModel<EntityModel<PedidoFornecedorResponseDTO>>> listarTodosPedidos() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Busca um pedido de fornecedor pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @GetMapping("/buscar/{id}")
    public ResponseEntity<PedidoFornecedorResponseDTO> buscarPedidoPorId(@PathVariable Long id) {
        PedidoFornecedorResponseDTO pedido = service.findById(id);
        return ResponseEntity.ok(pedido);
    }

    @Operation(summary = "Busca pedidos de fornecedor por fornecedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
            @ApiResponse(responseCode = "404", description = "Nenhum pedido encontrado para o fornecedor")
    })
    @GetMapping("/buscar/fornecedor/{fornecedorId}")
    public ResponseEntity<List<PedidoFornecedorResponseDTO>> buscarPedidosPorFornecedor(
            @PathVariable Long fornecedorId) {
        List<PedidoFornecedorResponseDTO> pedidos = service.findByFornecedorId(fornecedorId);
        return ResponseEntity.ok(pedidos);
    }

}
