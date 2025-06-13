package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.DTO.DataMapper;
import com.web2.movelcontrol.DTO.UsuarioRequestDTO;
import com.web2.movelcontrol.DTO.UsuarioResponseDTO;
import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.Usuario;
import com.web2.movelcontrol.Repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService service;

    private Usuario usuario;
    private UsuarioRequestDTO usuarioRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configurando objeto de exemplo
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João Controller");
        usuario.setEmail("joao@test.com");
        usuario.setSenha("senha123");
        usuario.setNivel_acesso("ADMIN");

        // Configurando DTO de requisição
        usuarioRequestDTO = new UsuarioRequestDTO();
        usuarioRequestDTO.setNome("João Controller");
        usuarioRequestDTO.setEmail("joao@test.com");
        usuarioRequestDTO.setSenha("senha123");
        usuarioRequestDTO.setNivel_acesso("ADMIN");
    }

    @Test
    void testCreateUsuario() {
        when(repository.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario saved = invocation.getArgument(0);
            saved.setId(1L); // Simula a geração de ID ao salvar no banco
            return saved;
        });

        UsuarioResponseDTO responseDTO = service.create(usuarioRequestDTO);

        assertNotNull(responseDTO);
        assertEquals(usuarioRequestDTO.getNome(), responseDTO.getNome());
        assertEquals(usuarioRequestDTO.getEmail(), responseDTO.getEmail());
        verify(repository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testFindByIdExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));

        var response = service.findById(1L);

        assertNotNull(response);
        assertEquals(usuario.getNome(), response.getContent().getNome());
        assertEquals(usuario.getEmail(), response.getContent().getEmail());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotExists() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.findById(2L));

        verify(repository, times(1)).findById(2L);
    }

    @Test
    void testUpdateUsuario() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioRequestDTO updatedRequestDTO = new UsuarioRequestDTO();
        updatedRequestDTO.setNome("João Atualizado");
        updatedRequestDTO.setEmail("joao.atualizado@test.com");
        updatedRequestDTO.setSenha("novaSenha123");
        updatedRequestDTO.setNivel_acesso("USER");

        UsuarioResponseDTO responseDTO = service.update(1L, updatedRequestDTO);

        assertNotNull(responseDTO);
        assertEquals("João Atualizado", responseDTO.getNome());
        assertEquals("joao.atualizado@test.com", responseDTO.getEmail());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testUpdateUsuarioNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        UsuarioRequestDTO updatedRequestDTO = new UsuarioRequestDTO();
        updatedRequestDTO.setNome("João Atualizado");

        assertThrows(NotFoundException.class, () -> service.update(2L, updatedRequestDTO));

        verify(repository, times(1)).findById(2L);
        verify(repository, never()).save(any(Usuario.class));
    }

    @Test
    void testDeleteUsuario() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        doNothing().when(repository).deleteById(1L);

        service.delete(1L);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUsuarioNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.delete(2L));

        verify(repository, times(1)).findById(2L);
        verify(repository, never()).deleteById(anyLong());
    }
}