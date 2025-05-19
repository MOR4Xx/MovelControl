package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.Usuario;
import com.web2.movelcontrol.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository repository;

    private Logger logger = Logger.getLogger(Usuario.class.getName());

    public Usuario create(Usuario usuario) {
        logger.info("Usuario criado com sucesso");
        return repository.save(usuario);
    }

    public Usuario findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario não encontrado com ID: " + id));
    }

    public Usuario update(Long id, Usuario usuarioNovo){
        Usuario usuarioAntigo = repository.findById(id)
                .orElseThrow(()-> new NotFoundException("Usuario "+ usuarioNovo.getId()));

        usuarioAntigo.setNome(usuarioNovo.getNome());
        usuarioAntigo.setEmail(usuarioNovo.getEmail());
        usuarioAntigo.setSenha(usuarioNovo.getSenha());
        usuarioAntigo.setNivel_acesso(usuarioNovo.getNivel_acesso());

        return repository.save(usuarioAntigo);

    }

    public void delete(Long id){
        repository.deleteById(id);
        logger.info("Usuario Apagado com sucesso");
    }
}
