package com.web2.movelcontrol.Controller;


import com.web2.movelcontrol.Model.NotaFiscal;
import com.web2.movelcontrol.Service.NotaFiscalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nota-fiscal")
public class NotaFiscalController {

    @Autowired
    private NotaFiscalService service;

    @PostMapping(value = "/criar"
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public NotaFiscal criarNotaFiscal(@RequestBody NotaFiscal notaFiscal){
        return service.create(notaFiscal);
    }

    @PutMapping(value = "/atualizar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void atualizarNotaFiscal(@PathVariable Long id, @RequestBody NotaFiscal notaFiscal){
        service.update(notaFiscal.getId(), notaFiscal);
    }
    @DeleteMapping(value = "/deletar/{id}")
    public void deletarNotaFiscal(@PathVariable Long id){
        service.delete(id);
    }
}
