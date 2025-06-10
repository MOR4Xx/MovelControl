package com.web2.movelcontrol.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web2.movelcontrol.DTO.FornecedorRequestDTO;
import com.web2.movelcontrol.DTO.FornecedorResponseDTO;
import com.web2.movelcontrol.Service.FornecedorService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/fornecedor")
public class FornecedorController {

    @Autowired
    FornecedorService service;

    @PostMapping(value = "/criar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public FornecedorResponseDTO createFornecedor(@RequestBody @Valid FornecedorRequestDTO fornecedor) {
        return service.create(fornecedor);
    }

    @DeleteMapping(value = "/deletar/{id}")
    public void deleteFornecedor(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/atualizar/{id}")
    public FornecedorResponseDTO atualizarFornecedor(@PathVariable Long id, @Valid @RequestBody FornecedorRequestDTO pf){
        return service.update(id, pf);
    }

    @GetMapping("/buscar/{id}")
    public FornecedorResponseDTO buscarFornecedor(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/buscartudo")
    public List<FornecedorResponseDTO> buscarFornecedorTudo() {
        return service.findAll();
    }
}
