package com.web2.movelcontrol.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.web2.movelcontrol.DTO.PedidoRequestDTO;
import com.web2.movelcontrol.DTO.PedidoResponseDTO;
import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.Orcamento;
import com.web2.movelcontrol.Model.Pedido;
import com.web2.movelcontrol.Repository.OrcamentoRepository;
import com.web2.movelcontrol.Repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {
	
	@InjectMocks
	private PedidoService service;
	
	@Mock
	private PedidoRepository pedidoRepository;
	
	@Mock
	private OrcamentoRepository orcamentoRepository;
	
	private PedidoRequestDTO validDto;
	private Orcamento orcamentoEntity;
	
	@BeforeEach
	void setUp() {
		validDto = new PedidoRequestDTO();
		validDto.setOrcamentoId(1L);
		validDto.setDataPedido(new Date());
		validDto.setStatus("EM_PROCESSAMENTO");
		validDto.setDescricao("Descrição de teste");
		
		orcamentoEntity = new Orcamento();
		orcamentoEntity.setId(1L);
	}
	
	@Test
	void criarPedido_Sucesso() {
		// dado que não existe pedido para esse orçamento
		when(pedidoRepository.existsByOrcamentoId(validDto.getOrcamentoId())).thenReturn(false);
		when(orcamentoRepository.findById(validDto.getOrcamentoId()))
				.thenReturn(Optional.of(orcamentoEntity));
		
		// simula o save retornando uma entidade já populada
		Pedido saved = new Pedido();
		saved.setId(100L);
		saved.setData_pedido(validDto.getDataPedido());
		saved.setStatus(validDto.getStatus());
		saved.setDescricao(validDto.getDescricao());
		saved.setOrcamento(orcamentoEntity);
		when(pedidoRepository.save(any(Pedido.class))).thenReturn(saved);
		
		// quando
		PedidoResponseDTO result = service.criarPedido(validDto);
		
		// então
		assertEquals(saved.getId(), result.getId());
		assertEquals(validDto.getDataPedido(), result.getDataPedido());
		assertEquals(validDto.getStatus(), result.getStatus());
		assertEquals(validDto.getDescricao(), result.getDescricao());
		assertEquals(validDto.getOrcamentoId(), result.getOrcamentoId());
		verify(pedidoRepository).save(any(Pedido.class));
	}
	
	@Test
	void criarPedido_OrcamentoNaoExiste_DeveLancarNotFound() {
		when(pedidoRepository.existsByOrcamentoId(validDto.getOrcamentoId())).thenReturn(false);
		when(orcamentoRepository.findById(validDto.getOrcamentoId()))
				.thenReturn(Optional.empty());
		
		assertThrows(NotFoundException.class, () -> service.criarPedido(validDto));
	}
	
	@Test
	void buscarPedidoPorId_Sucesso() {
		Pedido entity = new Pedido();
		entity.setId(50L);
		entity.setData_pedido(validDto.getDataPedido());
		entity.setStatus(validDto.getStatus());
		entity.setDescricao(validDto.getDescricao());
		entity.setOrcamento(orcamentoEntity);
		
		when(pedidoRepository.findById(50L)).thenReturn(Optional.of(entity));
		
		PedidoResponseDTO dto = service.buscarPedidoPorId(50L);
		
		assertEquals(50L, dto.getId());
		assertEquals(validDto.getDataPedido(), dto.getDataPedido());
		assertEquals(validDto.getStatus(), dto.getStatus());
		assertEquals(validDto.getDescricao(), dto.getDescricao());
		assertEquals(validDto.getOrcamentoId(), dto.getOrcamentoId());
	}
	
	@Test
	void buscarPedidoPorId_NaoEncontrado_DeveLancarNotFound() {
		when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> service.buscarPedidoPorId(99L));
	}
	
	@Test
	void listarTodosPedidos_RetornaListaDTO() {
		Pedido p1 = new Pedido();
		p1.setId(1L);
		p1.setData_pedido(validDto.getDataPedido());
		p1.setStatus(validDto.getStatus());
		p1.setDescricao(validDto.getDescricao());
		p1.setOrcamento(orcamentoEntity);
		
		Pedido p2 = new Pedido();
		p2.setId(2L);
		p2.setData_pedido(validDto.getDataPedido());
		p2.setStatus("CONCLUIDO");
		p2.setDescricao("Outra descrição");
		p2.setOrcamento(orcamentoEntity);
		
		when(pedidoRepository.findAll()).thenReturn(List.of(p1, p2));
		
		List<PedidoResponseDTO> dtos = service.listarTodosPedidos();
		
		assertEquals(2, dtos.size());
		assertTrue(dtos.stream().anyMatch(d -> d.getId().equals(1L)));
		assertTrue(dtos.stream().anyMatch(d -> d.getStatus().equals("CONCLUIDO")));
	}
	
	@Test
	void deletarPedido_ComSucesso() {
		when(pedidoRepository.existsById(1L)).thenReturn(true);
		
		// não deve lançar
		assertDoesNotThrow(() -> service.deletarPedido(1L));
		verify(pedidoRepository).deleteById(1L);
	}
	
	@Test
	void deletarPedido_NaoExiste_DeveLancarNotFound() {
		when(pedidoRepository.existsById(42L)).thenReturn(false);
		assertThrows(NotFoundException.class, () -> service.deletarPedido(42L));
	}
	
	@Test
	void atualizarPedido_Sucesso() {
		// cenário inicial
		Pedido original = new Pedido();
		original.setId(5L);
		original.setData_pedido(validDto.getDataPedido());
		original.setStatus("ANTIGO");
		original.setDescricao("Antiga descrição");
		original.setOrcamento(orcamentoEntity);
		
		when(pedidoRepository.findById(5L)).thenReturn(Optional.of(original));
		
		// simula update do orçamento vinculado
		Orcamento newOrc = new Orcamento();
		newOrc.setId(2L);
		validDto.setOrcamentoId(2L);
		when(orcamentoRepository.findById(2L)).thenReturn(Optional.of(newOrc));
		
		// simula o save retornando entidade atualizada
		Pedido updated = new Pedido();
		updated.setId(5L);
		updated.setData_pedido(validDto.getDataPedido());
		updated.setStatus(validDto.getStatus());
		updated.setDescricao(validDto.getDescricao());
		updated.setOrcamento(newOrc);
		when(pedidoRepository.save(any(Pedido.class))).thenReturn(updated);
		
		// quando
		PedidoResponseDTO dto = service.atualizarPedido(5L, validDto);
		
		// então
		assertEquals(5L, dto.getId());
		assertEquals(validDto.getStatus(), dto.getStatus());
		assertEquals(validDto.getDescricao(), dto.getDescricao());
		assertEquals(2L, dto.getOrcamentoId());
	}
}
