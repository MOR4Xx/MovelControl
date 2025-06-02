package com.web2.movelcontrol.Controller;

import com.web2.movelcontrol.DTO.PedidoFornecedorRequestDTO;
import com.web2.movelcontrol.DTO.PedidoFornecedorResponseDTO;
import com.web2.movelcontrol.Model.PedidoFornecedor;
import com.web2.movelcontrol.Service.PedidoFornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pFornecedor")
public class PedidoFornecedorController {

    @Autowired
    private PedidoFornecedorService service;

    @PostMapping(value = "/criar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PedidoFornecedor> criarPedidoFornecedor(@RequestBody PedidoFornecedorRequestDTO dto) {
        PedidoFornecedor novoPedido = service.create(dto);
        return ResponseEntity.ok(novoPedido);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<PedidoFornecedor> atualizarPedidoFornecedor(@PathVariable Long id,
            @RequestBody PedidoFornecedorRequestDTO dto) {
        PedidoFornecedor atualizado = service.update(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletePedidoFornecedor(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscartodos")
    public ResponseEntity<List<PedidoFornecedorResponseDTO>> listarTodosPedidos() {
        List<PedidoFornecedorResponseDTO> pedidos = service.findAll();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<PedidoFornecedorResponseDTO> buscarPedidoPorId(@PathVariable Long id) {
        PedidoFornecedorResponseDTO pedido = service.findById(id);
        return ResponseEntity.ok(pedido);
    }
}
