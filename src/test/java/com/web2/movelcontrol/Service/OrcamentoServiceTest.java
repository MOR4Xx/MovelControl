package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.DTO.OrcamentoRequestDTO;
import com.web2.movelcontrol.DTO.OrcamentoResponseDTO;
import com.web2.movelcontrol.DTO.ItemOrcamentoRequestDTO;
import com.web2.movelcontrol.Model.Item;
import com.web2.movelcontrol.Model.PessoaFisica;
import com.web2.movelcontrol.Model.Orcamento;
import com.web2.movelcontrol.Repository.OrcamentoRepository;
import com.web2.movelcontrol.Repository.ItemRepository;
import com.web2.movelcontrol.Repository.PessoaRepository;
import com.web2.movelcontrol.Exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrcamentoServiceTest {
	
	@InjectMocks
	private OrcamentoService service;
	
	@Mock
	private OrcamentoRepository orcamentoRepository;
	
	@Mock
	private ItemRepository itemRepository;
	
	@Mock
	private PessoaRepository pessoaRepository;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	private OrcamentoRequestDTO criarDtoComUmItem() {
		OrcamentoRequestDTO dto = new OrcamentoRequestDTO();
		dto.setDataCriacao(new Date());
		dto.setStatus("EM_ANDAMENTO");
		dto.setClienteId(1L);
		
		ItemOrcamentoRequestDTO io = new ItemOrcamentoRequestDTO();
		io.setItemId(1L);
		io.setQuantity(2);
		dto.getItens().add(io);
		
		return dto;
	}
	
	private PessoaFisica criarPessoa(Long id) {
		PessoaFisica p = new PessoaFisica();
		p.setId(id);
		return p;
	}
	
	private Item criarItem(Long id, double preco) {
		Item i = new Item();
		i.setId(id);
		i.setPrecoUnitario(preco);
		return i;
	}
	
	@Test
	void testCriarOrcamento_Sucesso() {
		OrcamentoRequestDTO dto = criarDtoComUmItem();
		PessoaFisica cliente = criarPessoa(dto.getClienteId());
		Item item = criarItem(1L, 10.0);
		
		when(pessoaRepository.findById(1L)).thenReturn(Optional.of(cliente));
		when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
		when(orcamentoRepository.save(any(Orcamento.class)))
				.thenAnswer(inv -> {
					Orcamento o = inv.getArgument(0);
					o.setId(1L);
					return o;
				});
		
		OrcamentoResponseDTO resultado = service.criarOrcamento(dto);
		
		assertNotNull(resultado.getId());
		assertEquals("EM_ANDAMENTO", resultado.getStatus());
		assertEquals(cliente.getId(), resultado.getCliente().getId());
		assertEquals(1, resultado.getItens().size());
		assertEquals(20.0, resultado.getValorTotal());
		
		verify(pessoaRepository).findById(1L);
		verify(itemRepository).findById(1L);
		verify(orcamentoRepository).save(any(Orcamento.class));
	}
	
	@Test
	void testCriarOrcamento_ClienteNaoEncontrado() {
		OrcamentoRequestDTO dto = criarDtoComUmItem();
		
		when(pessoaRepository.findById(1L)).thenReturn(Optional.empty());
		
		assertThrows(NotFoundException.class, () -> service.criarOrcamento(dto));
		
		verify(pessoaRepository).findById(1L);
		verifyNoInteractions(itemRepository, orcamentoRepository);
	}
	
	@Test
	void testCriarOrcamento_ItemNaoEncontrado() {
		OrcamentoRequestDTO dto = criarDtoComUmItem();
		PessoaFisica cliente = criarPessoa(dto.getClienteId());
		
		when(pessoaRepository.findById(1L)).thenReturn(Optional.of(cliente));
		when(itemRepository.findById(1L)).thenReturn(Optional.empty());
		
		assertThrows(NotFoundException.class, () -> service.criarOrcamento(dto));
		
		verify(pessoaRepository).findById(1L);
		verify(itemRepository).findById(1L);
		verify(orcamentoRepository, never()).save(any());
	}
	
	@Test
	void testBuscarOrcamentoPorId_Sucesso() {
		Orcamento ent = new Orcamento(); ent.setId(1L); ent.calcularValorTotalOrcamento();
		when(orcamentoRepository.findById(1L)).thenReturn(Optional.of(ent));
		
		OrcamentoResponseDTO resultado = service.buscarOrcamentoPorId(1L);
		
		assertNotNull(resultado);
		assertEquals(1L, resultado.getId());
		verify(orcamentoRepository).findById(1L);
	}
	
	@Test
	void testBuscarOrcamentoPorId_NaoEncontrado() {
		when(orcamentoRepository.findById(99L)).thenReturn(Optional.empty());
		
		assertThrows(NotFoundException.class,
				() -> service.buscarOrcamentoPorId(99L)
		);
		verify(orcamentoRepository).findById(99L);
	}
	
	@Test
	void testListarTodosOrcamentos() {
		Orcamento o1 = new Orcamento(); o1.setId(1L); o1.calcularValorTotalOrcamento();
		Orcamento o2 = new Orcamento(); o2.setId(2L); o2.calcularValorTotalOrcamento();
		
		when(orcamentoRepository.findAll()).thenReturn(List.of(o1, o2));
		
		List<OrcamentoResponseDTO> all = service.listarTodosOrcamentos();
		
		assertEquals(2, all.size());
		assertTrue(all.stream().anyMatch(d -> d.getId().equals(1L)));
		assertTrue(all.stream().anyMatch(d -> d.getId().equals(2L)));
		verify(orcamentoRepository).findAll();
	}
	
	@Test
	void testAtualizarOrcamento_Sucesso() {
		Orcamento existente = new Orcamento(); existente.setId(1L); existente.calcularValorTotalOrcamento();
		when(orcamentoRepository.findById(1L)).thenReturn(Optional.of(existente));
		when(orcamentoRepository.save(any(Orcamento.class))).thenAnswer(inv -> inv.getArgument(0));
		
		OrcamentoRequestDTO dto = new OrcamentoRequestDTO();
		dto.setStatus("ATUALIZADO");
		
		OrcamentoResponseDTO updated = service.atualizarOrcamento(1L, dto);
		assertEquals("ATUALIZADO", updated.getStatus());
		verify(orcamentoRepository).findById(1L);
		verify(orcamentoRepository).save(existente);
	}
	
	@Test
	void testAtualizarOrcamento_NaoEncontrado() {
		when(orcamentoRepository.findById(99L)).thenReturn(Optional.empty());
		
		assertThrows(NotFoundException.class,
				() -> service.atualizarOrcamento(99L, new OrcamentoRequestDTO())
		);
		verify(orcamentoRepository).findById(99L);
	}
	
	@Test
	void testDeletarOrcamento_Sucesso() {
		when(orcamentoRepository.existsById(1L)).thenReturn(true);
		
		service.deletarOrcamento(1L);
		
		verify(orcamentoRepository).existsById(1L);
		verify(orcamentoRepository).deleteById(1L);
	}
	
	@Test
	void testDeletarOrcamento_NaoEncontrado() {
		when(orcamentoRepository.existsById(42L)).thenReturn(false);
		
		assertThrows(NotFoundException.class,
				() -> service.deletarOrcamento(42L)
		);
		verify(orcamentoRepository).existsById(42L);
		verify(orcamentoRepository, never()).deleteById(any());
	}
}
