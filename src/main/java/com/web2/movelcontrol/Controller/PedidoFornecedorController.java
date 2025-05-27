package com.web2.movelcontrol.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.web2.movelcontrol.DTO.PedidoFornecedorResponseDTO;
import com.web2.movelcontrol.Model.PedidoFornecedor;
import com.web2.movelcontrol.Service.PedidoFornecedorService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/pFornecedor")
public class PedidoFornecedorController {

    @Autowired
    PedidoFornecedorService service;

    @PostMapping(value = "/criar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public PedidoFornecedor creatPedidoFornecedor(@RequestBody PedidoFornecedor pedidoFornecedor) {
        return service.create(pedidoFornecedor);
    }

    @PutMapping("/atualizar/{id}")
    public PedidoFornecedor atualizarPedidoFornecedor(@PathVariable Long id, @RequestBody PedidoFornecedor pf) {
        return service.update(id, pf);
    }

    @DeleteMapping(value = "/deletar/{id}")
    public void deletePedidoFornecedor(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/buscartodos")
    public ResponseEntity<List<PedidoFornecedorResponseDTO>> listarTodosPedidos() {
        List<PedidoFornecedorResponseDTO> pedidos = service.findAll();
        return ResponseEntity.ok(pedidos);
    }
}
