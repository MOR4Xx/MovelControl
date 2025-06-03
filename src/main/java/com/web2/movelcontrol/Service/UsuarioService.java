/*
 * Autor: Jorge Afonso
 * Responsavel: Jorge Afonso
 */

package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.Usuario;
import com.web2.movelcontrol.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    
    public List<Usuario> findByNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        
        logger.info("Buscando usuário por nome: " + nome);
        List<Usuario> usuarios = repository.findByNome(nome);

        if (usuarios.isEmpty()) {
            logger.warning("Nenhum usuário encontrado com o nome: " + nome);
            throw new NotFoundException("Nenhum usuário encontrado com o nome: " + nome);
        }

        return usuarios;
    }

    public Usuario update(Long id, Usuario usuarioUpdate){
        Usuario usuario = repository.findById(id)
                .orElseThrow(()-> new NotFoundException("Usuario "+ usuarioUpdate.getId()));

        if(usuarioUpdate.getNome() != null) usuario.setNome(usuarioUpdate.getNome());
        if (usuarioUpdate.getEmail() != null) usuario.setEmail(usuarioUpdate.getEmail());
        if (usuarioUpdate.getSenha() != null) usuario.setSenha(usuarioUpdate.getSenha());
        if (usuarioUpdate.getNivel_acesso() != null) usuario.setNivel_acesso(usuarioUpdate.getNivel_acesso());

        return repository.save(usuario);

    }

    public void delete(Long id){
        repository.deleteById(id);
        logger.info("Usuario Apagado com sucesso");
    }
}