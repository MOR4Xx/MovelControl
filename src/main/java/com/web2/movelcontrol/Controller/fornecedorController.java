package com.web2.movelcontrol.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web2.movelcontrol.Model.Fornecedor;
import com.web2.movelcontrol.Service.FornecedorService;


@RestController
@RequestMapping("/fornecedor")
public class fornecedorController {

    @Autowired
    FornecedorService service;

@PostMapping(value = "/criar",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Fornecedor createFornecedor(@RequestBody Fornecedor fornecedor) {
        return service.create(fornecedor);
    }

    @DeleteMapping(value = "/deletar/{id}")
    public void deleteFornecedor(@PathVariable Long id) {
        service.delete(id);
    }

        @PutMapping("/atualizar/{id}")
    public Fornecedor atualizarFornecedor(@PathVariable Long id, @RequestBody Fornecedor pf){
        return service.update(id, pf);
    }



}
