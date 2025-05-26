package com.web2.movelcontrol.Controller;

import com.web2.movelcontrol.DTO.DataMapper;
import com.web2.movelcontrol.DTO.UsuarioRequestDTO;
import com.web2.movelcontrol.DTO.UsuarioResponseDTO;
import com.web2.movelcontrol.Model.Usuario;
import com.web2.movelcontrol.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @PostMapping(value = "/criar",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioRequestDTO> criarUsuario(@RequestBody @Valid UsuarioRequestDTO RequestDTO){
        Usuario usuario = new Usuario();
        usuario.setNome(RequestDTO.getNome());
        usuario.setEmail(RequestDTO.getEmail());
        usuario.setSenha(RequestDTO.getSenha());
        usuario.setNivel_acesso(RequestDTO.getNivel_acesso());

        service.create(usuario);

        UsuarioRequestDTO usuarioResponseDTO = DataMapper.parseObject(usuario, UsuarioRequestDTO.class);
        return ResponseEntity.ok(usuarioResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Long id){
        Usuario usuario = service.findById(id);
        return ResponseEntity.ok(DataMapper.parseObject(usuario, UsuarioResponseDTO.class));
    }

    @GetMapping("/nome/{nome}")
    public  ResponseEntity<List<UsuarioResponseDTO>> findByNome(@PathVariable String nome){
        List<Usuario> dtos = service.findByNome(nome);
        return ResponseEntity.ok(DataMapper.parseListObjects(dtos, UsuarioResponseDTO.class));
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
