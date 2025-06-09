/*
 * Autor: Jorge Afonso
 * Responsavel: Jorge Afonso
 */

package com.web2.movelcontrol.Controller;

import com.web2.movelcontrol.DTO.DataMapper;
import com.web2.movelcontrol.DTO.UsuarioRequestDTO;
import com.web2.movelcontrol.DTO.UsuarioResponseDTO;
import com.web2.movelcontrol.Exceptions.ErrorResponseDTO;
import com.web2.movelcontrol.Model.Usuario;
import com.web2.movelcontrol.Service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuários", description = "Operações relacionadas aos usuários")
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @Operation(
            summary = "Cadastrar um novo usuário",
            description = "Cria um usuário no sistema com os dados informados",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida devido a dados de entrada incorretos ou incompletos.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UsuarioRequestDTO.class))
                    )
            }
    )
    @PostMapping(value = "/criar",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioRequestDTO> criarUsuario(@RequestBody @Valid UsuarioRequestDTO RequestDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(RequestDTO.getNome());
        usuario.setEmail(RequestDTO.getEmail());
        usuario.setSenha(RequestDTO.getSenha());
        usuario.setNivel_acesso(RequestDTO.getNivel_acesso());

        service.create(usuario);

        UsuarioRequestDTO usuarioResponseDTO = DataMapper.parseObject(usuario, UsuarioRequestDTO.class);
        return ResponseEntity.ok(usuarioResponseDTO);
    }

    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna os dados de um usuário específico pelo seu ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> findById(@PathVariable Long id) {
        Usuario usuario = service.findById(id);
        UsuarioResponseDTO dto = DataMapper.parseObject(usuario, UsuarioResponseDTO.class);

        EntityModel<UsuarioResponseDTO> resource = EntityModel.of(dto,
                linkTo(methodOn(UsuarioController.class).findById(id)).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).findByNome(dto.getNome())).withRel("buscarPorNome"),
                linkTo(methodOn(UsuarioController.class).listarUsuarios()).withRel("listarUsuarios"),
                linkTo(methodOn(UsuarioController.class).atualizarUsuario(id, null)).withRel("update"),
                linkTo(methodOn(UsuarioController.class).deletarUsuario(id)).withRel("delete")
        );

        return ResponseEntity.ok(resource);
    }

    @Operation(
            summary = "Buscar usuário por Nome",
            description = "Retorna os dados de um usuário específico pelo seu ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    )
            }
    )
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<UsuarioResponseDTO>> findByNome(@PathVariable String nome) {
        List<Usuario> dtos = service.findByNome(nome);
        return ResponseEntity.ok(DataMapper.parseListObjects(dtos, UsuarioResponseDTO.class));
    }

    @Operation(
            summary = "Lista todos os Usuarios",
            description = "Retorna uma lista com todos os usuarios do sistema",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuários Encontrados",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Usuários não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    )
            }
    )
    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        return ResponseEntity.ok(DataMapper.parseListObjects(service.findAll(), UsuarioResponseDTO.class));
    }

    @Operation(
            summary = "Atualizar um usuário",
            description = "Atualiza os dados de um usuário existente pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Usuario.class))),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            }
    )
    @PutMapping(value = "/atualizar/{id}"
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public Usuario atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioRequestDTO usuarioDTO) {
        Usuario usuario = DataMapper.parseObject(usuarioDTO, Usuario.class);

        return service.update(id, usuario);
    }

    @Operation(
            summary = "Deletar um usuário",
            description = "Remove um usuário do sistema pelo seu ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            }
    )
    @DeleteMapping(value = "/deletar/{id}")
    public Usuario deletarUsuario(@PathVariable Long id) {
        service.delete(id);
        return null;
    }

}
