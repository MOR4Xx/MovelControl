package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.DTO.DataMapper;
import com.web2.movelcontrol.DTO.EnderecoRequestDTO;
import com.web2.movelcontrol.DTO.PessoaFisicaRequestDTO;
import com.web2.movelcontrol.DTO.PessoaFisicaResponseDTO;
import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.Endereco;
import com.web2.movelcontrol.Model.PessoaFisica;
import com.web2.movelcontrol.Repository.PessoaFisicaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PessoaFisicaServiceTest {

    @Mock
    private PessoaFisicaRepository repository;

    @InjectMocks
    private PessoaFisicaService service;

    private PessoaFisica pessoaFisica;
    private PessoaFisicaRequestDTO pessoaFisicaRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Endereco endereco = new Endereco();
        endereco.setCep("75700-000");
        endereco.setRua("Rua A");
        endereco.setBairro("Centro");
        endereco.setNumero("100");
        endereco.setComplemento("Apto 1");

        pessoaFisica = new PessoaFisica();
        pessoaFisica.setId(1L);
        pessoaFisica.setNome("João da Silva");
        pessoaFisica.setCpf("123.456.789-00");
        pessoaFisica.setEmail("joao@teste.com");
        pessoaFisica.setTelefone("(64)98888-7777");
        pessoaFisica.setEndereco(endereco);

        EnderecoRequestDTO enderecoRequestDTO = new EnderecoRequestDTO();
        enderecoRequestDTO.setCep("75700-000");
        enderecoRequestDTO.setRua("Rua A");
        enderecoRequestDTO.setBairro("Centro");
        enderecoRequestDTO.setNumero("100");
        enderecoRequestDTO.setComplemento("Apto 1");

        pessoaFisicaRequestDTO = new PessoaFisicaRequestDTO();
        pessoaFisicaRequestDTO.setNome("João da Silva");
        pessoaFisicaRequestDTO.setCpf("123.456.789-00");
        pessoaFisicaRequestDTO.setEmail("joao@teste.com");
        pessoaFisicaRequestDTO.setTelefone("(64)98888-7777");
        pessoaFisicaRequestDTO.setEndereco(enderecoRequestDTO);
    }

    @Test
    void testCreatePessoaFisica() {
        when(repository.save(any(PessoaFisica.class))).thenAnswer(invocation -> {
            PessoaFisica saved = invocation.getArgument(0);
            saved.setId(1L); // Simula o ID gerado ao salvar no banco
            return saved;
        });

        PessoaFisicaResponseDTO responseDTO = service.create(pessoaFisicaRequestDTO);

        assertNotNull(responseDTO);
        assertEquals(pessoaFisicaRequestDTO.getNome(), responseDTO.getNome());
        assertEquals(pessoaFisicaRequestDTO.getCpf(), responseDTO.getCpf());
        assertEquals(pessoaFisicaRequestDTO.getEmail(), responseDTO.getEmail());
        verify(repository, times(1)).save(any(PessoaFisica.class));
    }

    @Test
    void testFindByIdExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(pessoaFisica));

        var response = service.findById(1L);

        assertNotNull(response);
        assertEquals(pessoaFisica.getNome(), response.getContent().getNome());
        assertEquals(pessoaFisica.getCpf(), response.getContent().getCpf());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotExists() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.findById(2L));

        verify(repository, times(1)).findById(2L);
    }

    @Test
    void testUpdatePessoaFisica() {
        when(repository.findById(1L)).thenReturn(Optional.of(pessoaFisica));
        when(repository.save(any(PessoaFisica.class))).thenReturn(pessoaFisica);

        PessoaFisicaRequestDTO updatedRequestDTO = new PessoaFisicaRequestDTO();
        updatedRequestDTO.setNome("João Alterado");
        updatedRequestDTO.setEmail("joao.alterado@teste.com");
        updatedRequestDTO.setCpf("123.456.789-00");
        updatedRequestDTO.setTelefone("(64)90000-0000");

        PessoaFisicaResponseDTO responseDTO = service.update(1L, updatedRequestDTO);

        assertNotNull(responseDTO);
        assertEquals("João Alterado", responseDTO.getNome());
        assertEquals("joao.alterado@teste.com", responseDTO.getEmail());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(PessoaFisica.class));
    }

    @Test
    void testUpdatePessoaFisicaNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        PessoaFisicaRequestDTO updatedRequestDTO = new PessoaFisicaRequestDTO();
        updatedRequestDTO.setNome("João Alterado");

        assertThrows(NotFoundException.class, () -> service.update(2L, updatedRequestDTO));

        verify(repository, times(1)).findById(2L);
        verify(repository, times(0)).save(any(PessoaFisica.class));
    }

    @Test
    void testDeletePessoaFisica() {
        when(repository.findById(1L)).thenReturn(Optional.of(pessoaFisica));
        doNothing().when(repository).deleteById(1L);

        service.delete(1L);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeletePessoaFisicaNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.delete(2L));

        verify(repository, times(1)).findById(2L);
        verify(repository, times(0)).deleteById(anyLong());
    }
}