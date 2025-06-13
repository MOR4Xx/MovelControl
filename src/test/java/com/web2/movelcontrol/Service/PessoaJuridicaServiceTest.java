package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.DTO.EnderecoRequestDTO;
import com.web2.movelcontrol.DTO.PessoaJuridicaRequestDTO;
import com.web2.movelcontrol.DTO.PessoaJuridicaResponseDTO;
import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.Endereco;
import com.web2.movelcontrol.Model.PessoaJuridica;
import com.web2.movelcontrol.Repository.PessoaJuridicaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PessoaJuridicaServiceTest {

    @Mock
    private PessoaJuridicaRepository repository;

    @InjectMocks
    private PessoaJuridicaService service;

    private PessoaJuridica pessoaJuridica;
    private PessoaJuridicaRequestDTO pessoaJuridicaRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicializando objeto PessoaJuridica
        Endereco endereco = new Endereco();
        endereco.setCep("75700-000");
        endereco.setRua("Rua A");
        endereco.setBairro("Centro");
        endereco.setNumero("100");
        endereco.setComplemento("Apto 1");

        pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setId(1L);
        pessoaJuridica.setNome("Empresa Teste");
        pessoaJuridica.setCnpj("12.345.678/0001-00");
        pessoaJuridica.setEmail("empresa@teste.com");
        pessoaJuridica.setTelefone("(64)99999-9999");
        pessoaJuridica.setEndereco(endereco);

        // Inicializando DTO PessoaJuridicaRequestDTO para testes de criação/atualização
        EnderecoRequestDTO enderecoRequestDTO = new EnderecoRequestDTO();
        enderecoRequestDTO.setCep("75700-000");
        enderecoRequestDTO.setRua("Rua A");
        enderecoRequestDTO.setBairro("Centro");
        enderecoRequestDTO.setNumero("100");
        enderecoRequestDTO.setComplemento("Apto 1");

        pessoaJuridicaRequestDTO = new PessoaJuridicaRequestDTO();
        pessoaJuridicaRequestDTO.setNome("Empresa Teste");
        pessoaJuridicaRequestDTO.setCnpj("12.345.678/0001-00");
        pessoaJuridicaRequestDTO.setEmail("empresa@teste.com");
        pessoaJuridicaRequestDTO.setTelefone("(64)99999-9999");
        pessoaJuridicaRequestDTO.setEndereco(enderecoRequestDTO);
    }

    @Test
    void testCreatePessoaJuridica() {
        when(repository.save(any(PessoaJuridica.class))).thenAnswer(invocation -> {
            PessoaJuridica saved = invocation.getArgument(0);
            saved.setId(1L); // Simula geração de ID ao salvar no banco
            return saved;
        });

        PessoaJuridicaResponseDTO responseDTO = service.create(pessoaJuridicaRequestDTO);

        assertNotNull(responseDTO);
        assertEquals(pessoaJuridicaRequestDTO.getNome(), responseDTO.getNome());
        assertEquals(pessoaJuridicaRequestDTO.getCnpj(), responseDTO.getCnpj());
        verify(repository, times(1)).save(any(PessoaJuridica.class));
    }

    @Test
    void testFindByIdExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(pessoaJuridica));

        var response = service.findById(1L);

        assertNotNull(response);
        assertEquals(pessoaJuridica.getNome(), response.getContent().getNome());
        assertEquals(pessoaJuridica.getCnpj(), response.getContent().getCnpj());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotExists() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.findById(2L));

        verify(repository, times(1)).findById(2L);
    }

    @Test
    void testUpdatePessoaJuridica() {
        when(repository.findById(1L)).thenReturn(Optional.of(pessoaJuridica));
        when(repository.save(any(PessoaJuridica.class))).thenReturn(pessoaJuridica);

        PessoaJuridicaRequestDTO updatedRequestDTO = new PessoaJuridicaRequestDTO();
        updatedRequestDTO.setNome("Empresa Atualizada");
        updatedRequestDTO.setEmail("empresa@atualizada.com");
        updatedRequestDTO.setCnpj("12.345.678/0001-00");
        updatedRequestDTO.setTelefone("(64)99999-8888");

        PessoaJuridicaResponseDTO responseDTO = service.update(1L, updatedRequestDTO);

        assertNotNull(responseDTO);
        assertEquals("Empresa Atualizada", responseDTO.getNome());
        assertEquals("empresa@atualizada.com", responseDTO.getEmail());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(PessoaJuridica.class));
    }

    @Test
    void testUpdatePessoaJuridicaNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        PessoaJuridicaRequestDTO updatedRequestDTO = new PessoaJuridicaRequestDTO();
        updatedRequestDTO.setNome("Empresa Atualizada");

        assertThrows(NotFoundException.class, () -> service.update(2L, updatedRequestDTO));

        verify(repository, times(1)).findById(2L);
        verify(repository, never()).save(any(PessoaJuridica.class));
    }

    @Test
    void testDeletePessoaJuridica() {
        when(repository.findById(1L)).thenReturn(Optional.of(pessoaJuridica));
        doNothing().when(repository).deleteById(1L);

        service.delete(1L);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeletePessoaJuridicaNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.delete(2L));

        verify(repository, times(1)).findById(2L);
        verify(repository, never()).deleteById(anyLong());
    }
}