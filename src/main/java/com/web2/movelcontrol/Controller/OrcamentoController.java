package com.web2.movelcontrol.Controller;

import com.web2.movelcontrol.Model.Orcamento;
import com.web2.movelcontrol.Service.OrcamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orcamentos")
public class OrcamentoController {

    @Autowired
    private OrcamentoService orcamentoService;

    // Endpoint para CRIAR um novo orçamento
    @PostMapping
    public ResponseEntity<Orcamento> criarOrcamento(@RequestBody Orcamento orcamento) {
        Orcamento novoOrcamento = orcamentoService.criarOrcamento(orcamento);
        return new ResponseEntity<>(novoOrcamento, HttpStatus.CREATED);
    }

    // Endpoint para BUSCAR um orçamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<Orcamento> buscarOrcamentoPorId(@PathVariable Long id) {
        Orcamento orcamento = orcamentoService.buscarOrcamentoPorId(id);
        return ResponseEntity.ok(orcamento);
    }

    // Endpoint para LISTAR todos os orçamentos
    // HTTP GET para /orcamentos
    @GetMapping
    public ResponseEntity<List<Orcamento>> listarTodosOrcamentos() {
        List<Orcamento> orcamentos = orcamentoService.listarTodosOrcamentos();
        return ResponseEntity.ok(orcamentos);
    }

    // Endpoint para ATUALIZAR um orçamento
    // HTTP PUT para /orcamentos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Orcamento> atualizarOrcamento(@PathVariable Long id, @RequestBody Orcamento orcamentoDetails) {
        Orcamento orcamentoAtualizado = orcamentoService.atualizarOrcamento(id, orcamentoDetails);
        return ResponseEntity.ok(orcamentoAtualizado);
    }

    // Endpoint para DELETAR um orçamento
    // HTTP DELETE para /orcamentos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarOrcamento(@PathVariable Long id) {
        orcamentoService.deletarOrcamento(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content, indicando sucesso sem corpo de resposta
    }
}