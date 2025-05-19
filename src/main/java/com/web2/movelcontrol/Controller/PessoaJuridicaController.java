package com.web2.movelcontrol.Controller;

import com.web2.movelcontrol.Model.PessoaJuridica;
import com.web2.movelcontrol.Service.PessoaJuridicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    public PessoaJuridica atualizarPessoaJuridica(@PathVariable Long id, @RequestBody PessoaJuridica pj){
        return service.update(id, pj);
    }

    @DeleteMapping(value = "/deletar/{id}")
    public void deletePessoaJuridica(@PathVariable Long id) {
        service.delete(id);
    }
}
