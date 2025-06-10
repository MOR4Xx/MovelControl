package com.web2.movelcontrol.Service;

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

    // Cria um mock do repositório PessoaJuridicaRepository
    @Mock
    private PessoaJuridicaRepository repository;

    // Injeta o mock do repositório no service que será testado
    @InjectMocks
    private PessoaJuridicaService service;

    // Objeto de PessoaJuridica usado nos testes
    private PessoaJuridica pessoaJuridica;

    // Método executado antes de cada teste, inicializa objetos e mocks
    @BeforeEach
    void setUp() {
        // Inicializa os mocks criados com as anotações @Mock e @InjectMocks
        MockitoAnnotations.openMocks(this);

        // Instancia um objeto PessoaJuridica com dados simulados
        pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setId(1L);
        pessoaJuridica.setNome("Empresa Teste");
        pessoaJuridica.setCnpj("11.111.111/0001-89");
        pessoaJuridica.setEmail("empresa@teste.com");
        pessoaJuridica.setTelefone("(64)99999-9999");
        pessoaJuridica.setEndereco(new Endereco(1L,"Rua 1", "Centro", "12345-678", "100", "Apto 1"));
    }

    // Teste do método create() do service
    @Test
    void createPessoaJuridica() {
        // Configura o mock para retornar pessoaJuridica quando o método save for chamado
        when(repository.save(pessoaJuridica)).thenReturn(pessoaJuridica);

        // Executa o método que será testado
        PessoaJuridica created = service.create(pessoaJuridica);

        // Verifica se o retorno não é nulo e se os dados estão corretos
        assertNotNull(created);
        assertEquals("Empresa Teste", created.getNome());

        // Verifica se o método save foi chamado exatamente uma vez
        verify(repository, times(1)).save(pessoaJuridica);
    }

    // Teste do método findById() quando o ID existe
    @Test
    void findPessoaJuridicaByIdExists() {
        // Configura o mock para retornar um Optional com pessoaJuridica quando findById for chamado
        when(repository.findById(1L)).thenReturn(Optional.of(pessoaJuridica));

        // Executa o método que será testado
        PessoaJuridica found = service.findById(1L);

        // Verifica se o retorno não é nulo e se o ID é o esperado
        assertNotNull(found);
        assertEquals(1L, found.getId());

        // Verifica se findById foi chamado uma vez com o ID 1L
        verify(repository, times(1)).findById(1L);
    }

    // Teste do método findById() quando o ID não existe
    @Test
    void findPessoaJuridicaByIdNotExists() {
        // Configura o mock para retornar Optional.empty() simulando dado não encontrado
        when(repository.findById(2L)).thenReturn(Optional.empty());

        // Verifica se ao buscar um ID inexistente lança a exceção NotFoundException
        assertThrows(NotFoundException.class, () -> service.findById(2L));

        // Verifica se findById foi chamado uma vez com o ID 2L
        verify(repository, times(1)).findById(2L);
    }

    // Teste do método update() do service
    @Test
    void updatePessoaJuridica() {
        // Cria um objeto com os dados atualizados
        PessoaJuridica updatedData = new PessoaJuridica();
        updatedData.setNome("Empresa Atualizada");
        updatedData.setEmail("atualizado@teste.com");

        // Configura o mock para simular que encontrou o objeto no banco
        when(repository.findById(1L)).thenReturn(Optional.of(pessoaJuridica));
        // Configura o mock para retornar a própria pessoa ao salvar
        when(repository.save(any(PessoaJuridica.class))).thenReturn(pessoaJuridica);

        // Executa o método update passando o ID e os novos dados
        PessoaJuridica updated = service.update(1L, updatedData);

        // Verifica se o retorno não é nulo e se os dados foram atualizados corretamente
        assertNotNull(updated);
        assertEquals("Empresa Atualizada", updated.getNome());
        assertEquals("atualizado@teste.com", updated.getEmail());

        // Verifica se os métodos findById e save foram chamados corretamente
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(PessoaJuridica.class));
    }

    // Teste do método delete() do service
    @Test
    void deletePessoaJuridica() {
        // Configura o mock para não fazer nada quando deleteById for chamado
        doNothing().when(repository).deleteById(1L);

        // Executa o método delete
        service.delete(1L);

        // Verifica se deleteById foi chamado uma vez com o ID 1L
        verify(repository, times(1)).deleteById(1L);
    }
}
