package com.web2.movelcontrol.Controller;

import com.web2.movelcontrol.Model.Item;
import com.web2.movelcontrol.Service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService service;

    @Operation(summary = "Cria um novo item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping(value = "/criar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Item criarItem(@RequestBody Item item) {
        return service.create(item);
    }

    @Operation(summary = "Busca um item pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item encontrado"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @GetMapping("/{id}")
    public Item findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Atualiza um item pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @PutMapping("/atualizar/{id}")
    public Item atualizarItem(@PathVariable Long id, @RequestBody Item itemNovo) {
        return service.update(id, itemNovo);
    }

    @Operation(summary = "Deleta um item pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @DeleteMapping("/deletar/{id}")
    public void deletarItem(@PathVariable Long id) {
        service.delete(id);
    }

    @Operation(summary = "Busca todos os itens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Itens listados com sucesso")
    })
    @GetMapping("/todos")
    public List<Item> listarTodos() {
        return service.findAll();
    }

    @Operation(summary = "Busca itens pelo nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Itens encontrados"),
            @ApiResponse(responseCode = "404", description = "Nenhum item encontrado com o nome informado")
    })
    @PostMapping(value = "/buscar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Item> buscarPorNome(@RequestBody Map<String, String> payload) {
        String nome = payload.get("nome");
        return service.findByNome(nome);
    }
}
