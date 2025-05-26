package com.web2.movelcontrol.Controller;


import com.web2.movelcontrol.Model.NotaFiscal;
import com.web2.movelcontrol.Service.NotaFiscalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nota-fiscal")
@Tag(name = "Nota Fiscal", description = "Operações relacionadas as Notas Fiscais")
public class NotaFiscalController {

    @Autowired
    private NotaFiscalService service;

    @PostMapping(value = "/criar"
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public NotaFiscal criarNotaFiscal(@RequestBody NotaFiscal notaFiscal) {
        return service.create(notaFiscal);
    }

    @GetMapping("/{id}")
    public NotaFiscal findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping(value = "/atualizar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void atualizarNotaFiscal(@PathVariable Long id, @RequestBody NotaFiscal notaFiscal) {
        service.update(notaFiscal.getId(), notaFiscal);
    }

    @DeleteMapping(value = "/deletar/{id}")
    public void deletarNotaFiscal(@PathVariable Long id) {
        service.delete(id);
    }
}
