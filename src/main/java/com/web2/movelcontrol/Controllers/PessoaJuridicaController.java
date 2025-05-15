package com.web2.movelcontrol.Controllers;

import com.web2.movelcontrol.Models.PessoaJuridica;
import com.web2.movelcontrol.Services.PessoaJuridicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/pj")
public class PessoaJuridicaController {
    @Autowired
    PessoaJuridicaService service;

    @PostMapping(value = "/criar",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public PessoaJuridica createPessoaJuridica(@RequestBody PessoaJuridica pj) {
        return service.create(pj);
    }

    @PutMapping("/atualizar/{id}")
    public PessoaJuridica atualizarPessoaJuridica(@PathVariable Integer id, @RequestBody PessoaJuridica pj){
        return service.update(id, pj);
    }


    @DeleteMapping(value = "/deletar/{id}")
    public void deletePessoaJuridica(@PathVariable Integer id) {
        service.delete(id);
    }
}
