package com.web2.movelcontrol.Controller;

import com.web2.movelcontrol.Model.Usuario;
import com.web2.movelcontrol.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @PostMapping(value = "/criar",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Usuario criarUsuario(@RequestBody Usuario usuario){
        return service.create(usuario);
    }

    @PutMapping(value = "/atualizar/{id}"
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public Usuario atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        return service.update(id, usuario);
    }

    @DeleteMapping(value = "/deletar/{id}")
    public void deletarUsuario(@PathVariable Long id){
        service.delete(id);
    }

}
