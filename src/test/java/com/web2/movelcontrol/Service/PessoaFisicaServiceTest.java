package com.web2.movelcontrol.Service;

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

    // Mock do repositório, simula acesso ao banco de dados
    @Mock
    private PessoaFisicaRepository repository;

    // Injeta o mock do repositório na classe de serviço que será testada
    @InjectMocks
    private PessoaFisicaService service;

    // Objeto de pessoa física usado nos testes
    private PessoaFisica pessoaFisica;

    // Configuração executada antes de cada teste
    @BeforeEach
    void setUp() {
        // Inicializa os mocks
        MockitoAnnotations.openMocks(this);

        // Criação de um objeto PessoaFisica com dados de exemplo
        pessoaFisica = new PessoaFisica();
        pessoaFisica.setId(1L);
        pessoaFisica.setNome("João da Silva");
        pessoaFisica.setCpf("123.456.789-00");
        pessoaFisica.setEmail("joao@teste.com");
        pessoaFisica.setTelefone("(64)98888-7777");
        pessoaFisica.setEndereco(new Endereco(1L, "Rua A", "Centro", "75700-000", "100", "Apto 1"));
    }

    // Teste do método create()
    @Test
    void createPessoaFisica() {
        // Simula que ao salvar, o repositório retorna a própria pessoa física
        when(repository.save(pessoaFisica)).thenReturn(pessoaFisica);

        // Chama o método que está sendo testado
        PessoaFisica created = service.create(pessoaFisica);

        // Verifica se o objeto retornado não é nulo e os dados estão corretos
        assertNotNull(created);
        assertEquals("João da Silva", created.getNome());
        assertEquals("123.456.789-00", created.getCpf());

        // Verifica se o método save do repositório foi chamado exatamente uma vez
        verify(repository, times(1)).save(pessoaFisica);
    }

    // Teste do método findById() quando encontra a pessoa
    @Test
    void findPessoaFisicaByIdExists() {
        // Simula retorno do Optional com a pessoa quando busca pelo ID
        when(repository.findById(1L)).thenReturn(Optional.of(pessoaFisica));

        // Chama o método que está sendo testado
        PessoaFisica found = service.findById(1L);

        // Valida se o objeto não é nulo e os dados estão corretos
        assertNotNull(found);
        assertEquals(1L, found.getId());

        // Verifica se findById foi chamado uma vez com ID 1L
        verify(repository, times(1)).findById(1L);
    }

    // Teste do método findById() quando não encontra a pessoa
    @Test
    void findPessoaFisicaByIdNotExists() {
        // Simula que o repositório retorna vazio
        when(repository.findById(2L)).thenReturn(Optional.empty());

        // Verifica se o método lança NotFoundException ao não encontrar o ID
        assertThrows(NotFoundException.class, () -> service.findById(2L));

        // Verifica se findById foi chamado corretamente
        verify(repository, times(1)).findById(2L);
    }

    // Teste do método update()
    @Test
    void updatePessoaFisica() {
        // Dados atualizados que serão passados
        PessoaFisica updatedData = new PessoaFisica();
        updatedData.setNome("João Atualizado");
        updatedData.setEmail("joaoatualizado@teste.com");

        // Simula encontrar a pessoa existente
        when(repository.findById(1L)).thenReturn(Optional.of(pessoaFisica));
        // Simula o salvamento da pessoa atualizada
        when(repository.save(any(PessoaFisica.class))).thenReturn(pessoaFisica);

        // Chama o método que está sendo testado
        PessoaFisica updated = service.update(1L, updatedData);

        // Verificações dos dados atualizados
        assertNotNull(updated);
        assertEquals("João Atualizado", updated.getNome());
        assertEquals("joaoatualizado@teste.com", updated.getEmail());

        // Verifica se os métodos foram chamados corretamente
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(PessoaFisica.class));
    }

    // Teste do método delete()
    @Test
    void deletePessoaFisica() {
        // Simula que o método deleteById não faz nada (void)
        doNothing().when(repository).deleteById(1L);

        // Executa o método delete
        service.delete(1L);

        // Verifica se deleteById foi chamado uma vez
        verify(repository, times(1)).deleteById(1L);
    }
}
