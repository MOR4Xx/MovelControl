package com.web2.movelcontrol.Controller;

import com.web2.movelcontrol.Model.Pedido;
import com.web2.movelcontrol.Service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pedidos") // Define o path base para este controlador
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // Endpoint para ATUALIZAR um pedido
    // HTTP PUT para /pedidos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizarPedido(@PathVariable Long id, @RequestBody Pedido pedidoDetails) {
        Pedido pedidoAtualizado = pedidoService.atualizarPedido(id, pedidoDetails);
        return ResponseEntity.ok(pedidoAtualizado);
    }

    // Endpoint para CRIAR um novo pedido
    // HTTP POST para /pedidos
    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody Pedido pedido) {
        Pedido novoPedido = pedidoService.criarPedido(pedido);
        return new ResponseEntity<>(novoPedido, HttpStatus.CREATED);
    }

    // Endpoint para BUSCAR um pedido por ID
    // HTTP GET para /pedidos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedidoPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPedidoPorId(id);
        return ResponseEntity.ok(pedido);
    }

    //Endpoint para DELETAR um pedido
    // HTTP DELETE para /pedidos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        pedidoService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }

}