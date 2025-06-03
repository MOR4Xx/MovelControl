package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.DTO.OrcamentoRequestDTO;
import com.web2.movelcontrol.DTO.ItemOrcamentoRequestDTO;
import com.web2.movelcontrol.Model.Orcamento;
import com.web2.movelcontrol.Model.Item;
import com.web2.movelcontrol.Model.OrcamentoItem;
import com.web2.movelcontrol.Model.Pessoa;
import com.web2.movelcontrol.Repository.OrcamentoRepository;
import com.web2.movelcontrol.Repository.ItemRepository;
import com.web2.movelcontrol.Repository.PessoaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*; // Para os asserts
import static org.mockito.Mockito.*; // Para funcionalidades do Mockito como when, verify

@ExtendWith(MockitoExtension.class) // Habilita a integração do Mockito com JUnit 5
public class OrcamentoServiceTest {
	
	@Mock // Cria um mock para OrcamentoRepository
	private OrcamentoRepository orcamentoRepository;
	
	@Mock // Cria um mock para ItemRepository
	private ItemRepository itemRepository;
	
	@Mock // Cria um mock para PessoaRepository
	private PessoaRepository pessoaRepository;
	
	@InjectMocks // Cria uma instância de OrcamentoService e injeta os mocks acima
	private OrcamentoService orcamentoService;
	
	
	
	// metodos de testes
	
	private OrcamentoRequestDTO requestDTO;
	private Pessoa clienteMock;
	private Item itemMock;
	
	@BeforeEach // Este método será executado ANTES de cada @Test
	void setUp() {
		// Configuração comum para os testes
		requestDTO = new OrcamentoRequestDTO();
		requestDTO.setClienteId(1L);
		requestDTO.setStatus("PENDENTE_TESTE");
		requestDTO.setDataCriacao(new Date());
		
		ItemOrcamentoRequestDTO itemDTO = new ItemOrcamentoRequestDTO(10L, 2); // itemId 10, quantidade 2
		Set<ItemOrcamentoRequestDTO> itensDTO = new HashSet<>();
		itensDTO.add(itemDTO);
		requestDTO.setItens(itensDTO);
		
		// Mocks de entidades que os repositórios retornariam
		clienteMock = mock(Pessoa.class); // Usando mock(Pessoa.class) em vez de um subtipo específico por enquanto
		when(clienteMock.getId()).thenReturn(1L); // Garante que o cliente mockado tenha o ID esperado
		
		itemMock = new Item();
		itemMock.setId(10L);
		itemMock.setNome("Madeira");
		itemMock.setPrecoUnitario(500.00);
		itemMock.setDescricao("Madeira top");
		itemMock.setUnidadeMedida("UN");
		itemMock.setQuantidade_estoque(10);
	}
	
	@Test
	@DisplayName("Deve criar um orçamento com sucesso") // Nome descritivo para o teste
	void testCriarOrcamento_ComDadosValidos_DeveRetornarOrcamentoSalvo() {
		// ------------ ARRANGE (Organizar/Preparar) ------------
		
		//Configurar o mock do pessoaRepository para retornar nosso clienteMock quando findById for chamado com o ID do DTO
		when(pessoaRepository.findById(requestDTO.getClienteId())).thenReturn(Optional.of(clienteMock));
		
		//Configurar o mock do itemRepository para retornar nosso itemMock para cada item no DTO
		for (ItemOrcamentoRequestDTO itemReqDTO : requestDTO.getItens()) {
			when(itemRepository.findById(itemReqDTO.getItemId())).thenReturn(Optional.of(itemMock));
		}
		
		//Configurar o mock do orcamentoRepository para simula o comportamento de salvar e retornar a entidade salva
		when(orcamentoRepository.save(any(Orcamento.class))).thenAnswer(invocation -> {
			Orcamento orcamentoSalvo = invocation.getArgument(0);
			return orcamentoSalvo;
		});
		
		
		// ------------ ACT (Agir) ------------
		// Chamamos o método do serviço que queremos testar
		Orcamento orcamentoCriado = orcamentoService.criarOrcamento(requestDTO);
		
		
		// ------------ ASSERT (Verificar) ------------
		// Verificamos se o resultado é o esperado
		
		assertNotNull(orcamentoCriado, "O orçamento criado não deveria ser nulo.");
		assertEquals(requestDTO.getStatus(), orcamentoCriado.getStatus(), "O status do orçamento deveria ser o mesmo do DTO.");
		assertNotNull(orcamentoCriado.getDataCriacao(), "A data de criação do orçamento não deveria ser nula.");
		assertNotNull(orcamentoCriado.getCliente(), "O cliente no orçamento não deveria ser nulo.");
		assertEquals(clienteMock.getId(), orcamentoCriado.getCliente().getId(), "O ID do cliente no orçamento deveria ser o esperado.");
		
		assertNotNull(orcamentoCriado.getItensOrcamento(), "A lista de itens do orçamento não deveria ser nula.");
		assertEquals(1, orcamentoCriado.getItensOrcamento().size(), "Deveria haver 1 item no orçamento.");
		
		// Verificando o primeiro item do orçamento
		OrcamentoItem primeiroItemOrcamento = orcamentoCriado.getItensOrcamento().iterator().next();
		assertNotNull(primeiroItemOrcamento.getItem(), "O item dentro de OrcamentoItem não deveria ser nulo.");
		assertEquals(itemMock.getId(), primeiroItemOrcamento.getItem().getId(), "O ID do item deveria ser o esperado.");
		assertEquals(2, primeiroItemOrcamento.getQuantity(), "A quantidade do item deveria ser 2.");
		
		// Verificar o valor total calculado
		// 500.00 * 2 = 1000.00
		assertEquals(1000.00, orcamentoCriado.getValorTotal(), 0.001, "O valor total do orçamento deveria ser 1000.00.");
		
		// Verifica se os métodos dos repositórios mockados foram chamados
		verify(pessoaRepository, times(1)).findById(requestDTO.getClienteId()); // Verifica se findById foi chamado 1 vez com o clienteId
		verify(itemRepository, times(requestDTO.getItens().size())).findById(anyLong()); // Verifica para cada item
		verify(orcamentoRepository, times(1)).save(any(Orcamento.class)); // Verifica se save foi chamado 1 vez
	}
}