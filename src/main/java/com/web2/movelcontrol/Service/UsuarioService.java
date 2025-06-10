/*
 * Autor: Jorge Afonso
 * Responsavel: Jorge Afonso
 */

package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.Controller.UsuarioController;
import com.web2.movelcontrol.DTO.DataMapper;
import com.web2.movelcontrol.DTO.UsuarioRequestDTO;
import com.web2.movelcontrol.DTO.UsuarioResponseDTO;
import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.Usuario;
import com.web2.movelcontrol.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository repository;

    private Logger logger = Logger.getLogger(Usuario.class.getName());

    public UsuarioResponseDTO create(UsuarioRequestDTO usuario) {
        Usuario usuarioCriado = DataMapper.parseObject(usuario, Usuario.class);

        repository.save(usuarioCriado);

        logger.info("Usuario criado com sucesso");
        return DataMapper.parseObject(usuarioCriado, UsuarioResponseDTO.class);
    }

    public EntityModel<UsuarioResponseDTO> findById(Long id) {
        UsuarioResponseDTO usuarioDTO = DataMapper.parseObject(repository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Pessoa Juridica " + id))
                , UsuarioResponseDTO.class);

        EntityModel<UsuarioResponseDTO> response = EntityModel.of(usuarioDTO,
                linkTo(methodOn(UsuarioController.class).findById(id)).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).findByNome(usuarioDTO.getNome())).withRel("buscarPorNome"),
                linkTo(methodOn(UsuarioController.class).listarUsuarios()).withRel("listarUsuarios"),
                linkTo(methodOn(UsuarioController.class).atualizarUsuario(id, null)).withRel("update"),
                linkTo(methodOn(UsuarioController.class).deletarUsuario(id)).withRel("delete")
        );

        return response;
    }

    public List<EntityModel<UsuarioResponseDTO>> findByNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }

        logger.info("Buscando usuário por nome: " + nome);
        List<Usuario> usuarios = repository.findByNome(nome);
        List<EntityModel<UsuarioResponseDTO>> usuariosDTO = usuarios.stream().map(usuario -> {
            UsuarioResponseDTO usuarioDTO = DataMapper.parseObject(usuario, UsuarioResponseDTO.class);
            return EntityModel.of(usuarioDTO,
                    linkTo(methodOn(UsuarioController.class).findById(usuario.getId())).withSelfRel(),
                    linkTo(methodOn(UsuarioController.class).findByNome(usuarioDTO.getNome())).withRel("buscarPorNome"),
                    linkTo(methodOn(UsuarioController.class).listarUsuarios()).withRel("listarUsuarios"),
                    linkTo(methodOn(UsuarioController.class).atualizarUsuario(usuario.getId(), null)).withRel("update"),
                    linkTo(methodOn(UsuarioController.class).deletarUsuario(usuario.getId())).withRel("delete")
            );
        }).toList();

        if (usuariosDTO.isEmpty()) {
            logger.warning("Nenhum usuário encontrado com o nome: " + nome);
            throw new NotFoundException("Nenhum usuário encontrado com o nome: " + nome);
        }

        return usuariosDTO;
    }

    public List<EntityModel<UsuarioResponseDTO>> findAll() {
        logger.info("Buscando todos os usuários");
        List<Usuario> usuarios = repository.findAll();

        List<EntityModel<UsuarioResponseDTO>> usuariosDTO = usuarios.stream().map(usuario -> {
            UsuarioResponseDTO usuarioDTO = DataMapper.parseObject(usuario, UsuarioResponseDTO.class);
            return EntityModel.of(usuarioDTO,
                    linkTo(methodOn(UsuarioController.class).findById(usuario.getId())).withSelfRel()
            );
        }).toList();

        if (usuariosDTO.isEmpty()) {
            logger.warning("Nenhum usuário encontrado");
            throw new NotFoundException("Nenhum usuário encontrado");
        }

        return usuariosDTO;
    }

    public UsuarioResponseDTO update(Long id, UsuarioRequestDTO usuarioUpdate) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario " + id));

        if (usuarioUpdate.getNome() != null) usuario.setNome(usuarioUpdate.getNome());
        if (usuarioUpdate.getEmail() != null) usuario.setEmail(usuarioUpdate.getEmail());
        if (usuarioUpdate.getSenha() != null) usuario.setSenha(usuarioUpdate.getSenha());
        if (usuarioUpdate.getNivel_acesso() != null) usuario.setNivel_acesso(usuarioUpdate.getNivel_acesso());

        UsuarioResponseDTO usuarioDTO = DataMapper.parseObject(usuario, UsuarioResponseDTO.class);

        return usuarioDTO;
    }

    public void delete(Long id) {
        repository.deleteById(id);
        logger.info("Usuario Apagado com sucesso");
    }
}