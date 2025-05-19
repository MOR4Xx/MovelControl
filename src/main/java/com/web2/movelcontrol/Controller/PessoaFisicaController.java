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

    @PutMapping("/atualizar/{id}")
    public PessoaFisica atualizarPessoaFisica(@PathVariable Integer id, @RequestBody PessoaFisica pf){
        return service.update(id, pf);
    }


    @DeleteMapping(value = "/deletar/{id}")
    public void deletePessoaFIsica(@PathVariable Integer id) {
        service.delete(id);
    }
}
