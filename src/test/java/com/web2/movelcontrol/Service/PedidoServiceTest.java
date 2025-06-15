package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.DTO.PedidoRequestDTO;
import com.web2.movelcontrol.Model.Pedido;
import com.web2.movelcontrol.Model.Orcamento;
import com.web2.movelcontrol.Repository.PedidoRepository;
import com.web2.movelcontrol.Repository.OrcamentoRepository;
import com.web2.movelcontrol.Repository.NotaFiscalRepository;
import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Exceptions.ConflictException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoServiceTest {
	
	@InjectMocks
	private PedidoService service;
	
	@Mock
	private PedidoRepository pedidoRepository;
	
	@Mock
	private OrcamentoRepository orcamentoRepository;
	
	@Mock
	private NotaFiscalRepository notaFiscalRepository;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	private PedidoRequestDTO criarDto() {
		PedidoRequestDTO dto = new PedidoRequestDTO();
		dto.setDataPedido(new Date());
		dto.setStatus("AGUARDANDO_PAGAMENTO");
		dto.setDescricao("Teste");
		dto.setOrcamentoId(1L);
		return dto;
	}
	
	private Orcamento criarOrcamento(Long id) {
		Orcamento o = new Orcamento();
		o.setId(id);
		return o;
	}
	
	private Pedido criarPedidoEntidade(Long id) {
		Pedido p = new Pedido();
		p.setId(id);
		p.setData_pedido(new Date());
		p.setStatus("AGUARDANDO_PAGAMENTO");
		p.setDescricao("Teste");
		p.setOrcamento(criarOrcamento(1L));
		return p;
	}
	
	@Test
	void testCriarPedido_Sucesso() {
		PedidoRequestDTO dto = criarDto();
		Orcamento orc = criarOrcamento(dto.getOrcamentoId());
		
		when(pedidoRepository.existsByOrcamentoId(1L)).thenReturn(false);
		when(orcamentoRepository.findById(1L)).thenReturn(Optional.of(orc));
		when(pedidoRepository.save(any(Pedido.class)))
				.thenAnswer(inv -> {
					Pedido p = inv.getArgument(0);
					p.setId(1L);
					return p;
				});
		
		Pedido result = service.criarPedido(dto);
		
		assertNotNull(result.getId());
		assertEquals(dto.getStatus(), result.getStatus());
		assertEquals(dto.getDescricao(), result.getDescricao());
		assertEquals(orc, result.getOrcamento());
		verify(pedidoRepository).existsByOrcamentoId(1L);
		verify(orcamentoRepository).findById(1L);
		verify(pedidoRepository).save(any(Pedido.class));
	}
	
	@Test
	void testCriarPedido_Conflict() {
		PedidoRequestDTO dto = criarDto();
		Pedido exist = criarPedidoEntidade(2L);
		
		when(pedidoRepository.existsByOrcamentoId(1L)).thenReturn(true);
		when(pedidoRepository.findByOrcamentoId(1L)).thenReturn(Optional.of(exist));
		
		ConflictException ex = assertThrows(ConflictException.class,
				() -> service.criarPedido(dto)
		);
		assertTrue(ex.getMessage().contains("já está vinculado"));
		verify(pedidoRepository).existsByOrcamentoId(1L);
		verify(pedidoRepository).findByOrcamentoId(1L);
		verifyNoInteractions(orcamentoRepository);
	}
	
	@Test
	void testCriarPedido_OrcamentoNaoEncontrado() {
		PedidoRequestDTO dto = criarDto();
		
		when(pedidoRepository.existsByOrcamentoId(1L)).thenReturn(false);
		when(orcamentoRepository.findById(1L)).thenReturn(Optional.empty());
		
		assertThrows(NotFoundException.class,
				() -> service.criarPedido(dto)
		);
		verify(pedidoRepository).existsByOrcamentoId(1L);
		verify(orcamentoRepository).findById(1L);
		verify(pedidoRepository, never()).save(any());
	}
	
	@Test
	void testBuscarPedidoPorId_Sucesso() {
		Pedido p = criarPedidoEntidade(1L);
		when(pedidoRepository.findById(1L)).thenReturn(Optional.of(p));
		
		Pedido result = service.buscarPedidoPorId(1L);
		
		assertEquals(1L, result.getId());
		verify(pedidoRepository).findById(1L);
	}
	
	@Test
	void testBuscarPedidoPorId_NaoEncontrado() {
		when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());
		
		assertThrows(NotFoundException.class,
				() -> service.buscarPedidoPorId(99L)
		);
		verify(pedidoRepository).findById(99L);
	}
	
	@Test
	void testListarTodosPedidos() {
		Pedido p1 = criarPedidoEntidade(1L);
		Pedido p2 = criarPedidoEntidade(2L);
		when(pedidoRepository.findAll()).thenReturn(List.of(p1, p2));
		
		List<Pedido> all = service.listarTodosPedidos();
		assertEquals(2, all.size());
		assertTrue(all.contains(p1));
		assertTrue(all.contains(p2));
		verify(pedidoRepository).findAll();
	}
	
	@Test
	void testAtualizarPedido_Sucesso() {
		Pedido existing = criarPedidoEntidade(1L);
		when(pedidoRepository.findById(1L)).thenReturn(Optional.of(existing));
		when(pedidoRepository.save(any(Pedido.class))).thenAnswer(inv -> inv.getArgument(0));
		
		PedidoRequestDTO dto = new PedidoRequestDTO();
		dto.setStatus("ATUALIZADO");
		
		Pedido updated = service.atualizarPedido(1L, dto);
		assertEquals("ATUALIZADO", updated.getStatus());
		verify(pedidoRepository).findById(1L);
		verify(pedidoRepository).save(existing);
	}
	
	@Test
	void testAtualizarPedido_NaoEncontrado() {
		when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());
		
		assertThrows(NotFoundException.class,
				() -> service.atualizarPedido(99L, new PedidoRequestDTO())
		);
		verify(pedidoRepository).findById(99L);
	}
	
	@Test
	void testDeletarPedido_Sucesso() {
		Pedido existing = criarPedidoEntidade(1L);
		when(pedidoRepository.findById(1L)).thenReturn(Optional.of(existing));
		when(notaFiscalRepository.existsByPedido_Id(1L)).thenReturn(false);
		
		service.deletarPedido(1L);
		verify(pedidoRepository).findById(1L);
		verify(notaFiscalRepository).existsByPedido_Id(1L);
		verify(pedidoRepository).delete(existing);
	}
	
	@Test
	void testDeletarPedido_NaoEncontrado() {
		when(pedidoRepository.findById(42L)).thenReturn(Optional.empty());
		
		assertThrows(NotFoundException.class,
				() -> service.deletarPedido(42L)
		);
		verify(pedidoRepository).findById(42L);
		verifyNoInteractions(notaFiscalRepository);
	}
	
	@Test
	void testDeletarPedido_Conflict() {
		Pedido existing = criarPedidoEntidade(1L);
		when(pedidoRepository.findById(1L)).thenReturn(Optional.of(existing));
		when(notaFiscalRepository.existsByPedido_Id(1L)).thenReturn(true);
		
		assertThrows(ConflictException.class,
				() -> service.deletarPedido(1L)
		);
		verify(pedidoRepository).findById(1L);
		verify(notaFiscalRepository).existsByPedido_Id(1L);
		verify(pedidoRepository, never()).delete(any());
	}
}
