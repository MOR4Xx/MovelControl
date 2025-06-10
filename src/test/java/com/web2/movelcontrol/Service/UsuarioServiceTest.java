package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.Usuario;
import com.web2.movelcontrol.Repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService service;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João Controller");
        usuario.setEmail("joao@test.com");
        usuario.setSenha("123456");
        usuario.setNivel_acesso("ADMIN");
    }

    @Test
    void createUsuario() {
        when(repository.save(usuario)).thenReturn(usuario);

        Usuario created = service.create(usuario);

        assertNotNull(created);
        assertEquals("João Controller", created.getNome());
        assertEquals("joao@test.com", created.getEmail());
        verify(repository, times(1)).save(usuario);
    }

    @Test
    void findUsuarioByIdExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario found = service.findById(1L);

        assertNotNull(found);
        assertEquals(1L, found.getId());
        assertEquals("João Controller", found.getNome());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void findUsuarioByIdNotExists() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.findById(2L));
        verify(repository, times(1)).findById(2L);
    }

    @Test
    void findUsuarioByNomeSuccess() {
        when(repository.findByNome("João Controller")).thenReturn(List.of(usuario));

        List<Usuario> usuarios = service.findByNome("João Controller");

        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
        assertEquals("João Controller", usuarios.get(0).getNome());
        verify(repository, times(1)).findByNome("João Controller");
    }

    @Test
    void findUsuarioByNomeNotFound() {
        when(repository.findByNome("Desconhecido")).thenReturn(List.of());

        assertThrows(NotFoundException.class, () -> service.findByNome("Desconhecido"));
        verify(repository, times(1)).findByNome("Desconhecido");
    }

    @Test
    void updateUsuarioSuccess() {
        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setNome("João Atualizado");
        usuarioAtualizado.setEmail("joaoatualizado@test.com");

        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario updated = service.update(1L, usuarioAtualizado);

        assertNotNull(updated);
        assertEquals("João Atualizado", updated.getNome());
        assertEquals("joaoatualizado@test.com", updated.getEmail());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Usuario.class));
    }

    @Test
    void updateUsuarioNotFound() {
        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setNome("João Atualizado");

        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.update(99L, usuarioAtualizado));
        verify(repository, times(1)).findById(99L);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteUsuarioSuccess() {
        doNothing().when(repository).deleteById(1L);

        service.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deleteUsuarioNotFound() {
        doThrow(new NotFoundException("Usuário não encontrado")).when(repository).deleteById(99L);

        assertThrows(NotFoundException.class, () -> service.delete(99L));
        verify(repository, times(1)).deleteById(99L);
    }
}
