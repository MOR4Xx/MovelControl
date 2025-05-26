package com.web2.movelcontrol.Controller;

import com.web2.movelcontrol.Model.PessoaFisica;
import com.web2.movelcontrol.Service.PessoaFisicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pf")
public class PessoaFisicaController {

    @Autowired
    PessoaFisicaService service;

    @PostMapping(value = "/criar",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public PessoaFisica createPessoaFisica(@RequestBody PessoaFisica pf) {
        return service.create(pf);
    }

    @GetMapping("/{id}")
    public PessoaFisica findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping(value = "/atualizar/{id}"
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public PessoaFisica atualizarPessoaFisica(@PathVariable Long id, @RequestBody PessoaFisica pf){
        return service.update(id, pf);
    }


    @DeleteMapping(value = "/deletar/{id}")
    public void deletePessoaFIsica(@PathVariable Long id) {
        service.delete(id);
    }
}
