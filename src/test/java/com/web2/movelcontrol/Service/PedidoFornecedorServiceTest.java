package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.DTO.ItemPedidoFornecedorRequestDTO;
import com.web2.movelcontrol.DTO.PedidoFornecedorRequestDTO;
import com.web2.movelcontrol.DTO.PedidoFornecedorResponseDTO;
import com.web2.movelcontrol.Model.*;
import com.web2.movelcontrol.Repository.FornecedorRepository;
import com.web2.movelcontrol.Repository.ItemRepository;
import com.web2.movelcontrol.Repository.PedidoFornecedorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PedidoFornecedorServiceTest {

    @InjectMocks
    private PedidoFornecedorService service;

    @Mock
    private PedidoFornecedorRepository pedidoRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private FornecedorRepository fornecedorRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private PedidoFornecedorRequestDTO criarDTO() {
        PedidoFornecedorRequestDTO dto = new PedidoFornecedorRequestDTO();
        dto.setDataPedido(new Date());
        dto.setStatus("EM_ANDAMENTO");
        dto.setFornecedorId(1L);

        ItemPedidoFornecedorRequestDTO item = new ItemPedidoFornecedorRequestDTO();
        item.setItemId(1L);
        item.setQuantidade(10);

        dto.setItens(List.of(item));
        return dto;
    }

    private Fornecedor criarFornecedor() {
        Fornecedor f = new Fornecedor();
        f.setId(1L);
        f.setNome("Fornecedor A");
        return f;
    }

    private Item criarItem() {
        Item item = new Item();
        item.setId(1L);
        item.setNome("Parafuso");
        item.setDescricao("Parafuso 5mm");
        return item;
    }

   private PedidoFornecedor criarPedidoMock() {
    PedidoFornecedor pedido = new PedidoFornecedor();
    pedido.setId(1L);
    pedido.setDataPedido(new Date());
    pedido.setStatus("EM_ANDAMENTO");

    Fornecedor fornecedor = new Fornecedor();
    fornecedor.setId(1L);
    fornecedor.setNome("Fornecedor A");
    pedido.setFornecedor(fornecedor);

    Item item = new Item();
    item.setId(1L);
    item.setNome("Parafuso");
    item.setDescricao("Parafuso 5mm");

    ItemPedidoFornecedor ipf = new ItemPedidoFornecedor();
    ipf.setItem(item);
    ipf.setQuantidade(5);

    pedido.setItens_pedido(List.of(ipf));
    return pedido;
}

    @Test
    void testCreate_ComSucesso() {
        PedidoFornecedorRequestDTO dto = criarDTO();
        Fornecedor fornecedor = criarFornecedor();
        Item item = criarItem();

        when(fornecedorRepository.findById(1L)).thenReturn(Optional.of(fornecedor));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(pedidoRepository.save(any(PedidoFornecedor.class))).thenAnswer(invocation -> {
            PedidoFornecedor p = invocation.getArgument(0);
            p.setId(1L);
            return p;
        });

        PedidoFornecedor result = service.create(dto);

        assertNotNull(result);
        assertEquals("EM_ANDAMENTO", result.getStatus());
        assertEquals("Fornecedor A", result.getFornecedor().getNome());
        assertEquals(1, result.getItens_pedido().size());
        assertEquals("Parafuso", result.getItens_pedido().get(0).getItem().getNome());

        verify(pedidoRepository).save(any());
    }

    @Test
    void testCreate_ItemNaoEncontrado_DeveLancarExcecao() {
        PedidoFornecedorRequestDTO dto = criarDTO();
        when(fornecedorRepository.findById(1L)).thenReturn(Optional.of(criarFornecedor()));
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.create(dto));
        assertTrue(ex.getMessage().contains("Item com ID 1 não encontrado."));
    }

    @Test
    void testCreate_FornecedorNaoEncontrado_DeveLancarExcecao() {
        PedidoFornecedorRequestDTO dto = criarDTO();
        when(fornecedorRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.create(dto));
        assertTrue(ex.getMessage().contains("Fornecedor não encontrado."));
    }

    @Test
void testUpdate_ComSucesso() {
    PedidoFornecedor existing = criarPedidoMock();
    existing.setItens_pedido(new ArrayList<>()); // limpar para simular substituição

    PedidoFornecedorRequestDTO dto = criarDTO();
    dto.setStatus("FINALIZADO");

    Item item = criarItem();
    Fornecedor fornecedor = criarFornecedor();

    when(pedidoRepository.findById(1L)).thenReturn(Optional.of(existing));
    when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
    when(fornecedorRepository.findById(1L)).thenReturn(Optional.of(fornecedor));
    when(pedidoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    PedidoFornecedor result = service.update(1L, dto);

    assertNotNull(result);
    assertEquals("FINALIZADO", result.getStatus());
    assertEquals("Parafuso", result.getItens_pedido().get(0).getItem().getNome());
    verify(pedidoRepository).save(existing);
}

@Test
void testUpdate_PedidoNaoEncontrado_DeveLancarExcecao() {
    PedidoFornecedorRequestDTO dto = criarDTO();
    when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

    RuntimeException ex = assertThrows(RuntimeException.class, () -> service.update(1L, dto));
    assertTrue(ex.getMessage().contains("PedidoFornecedor com ID 1 não encontrado."));
}

@Test
void testDelete_ComSucesso() {
    PedidoFornecedor pedido = criarPedidoMock();
    pedido.setItens_pedido(new ArrayList<>(List.of(new ItemPedidoFornecedor())));

    when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
    when(pedidoRepository.save(any())).thenReturn(pedido);
    doNothing().when(pedidoRepository).deleteById(1L);

    service.delete(1L);

    assertTrue(pedido.getItens_pedido().isEmpty());
    verify(pedidoRepository).deleteById(1L);
}

@Test
void testDelete_PedidoNaoEncontrado() {
    when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

    RuntimeException ex = assertThrows(RuntimeException.class, () -> service.delete(1L));
    assertTrue(ex.getMessage().contains("PedidoFornecedor com ID 1 não encontrado."));
}

@Test
void testFindById_ComSucesso() {
    PedidoFornecedor pedido = criarPedidoMock();
    Item item = criarItem();

    ItemPedidoFornecedor ipf = new ItemPedidoFornecedor();
    ipf.setItem(item);
    ipf.setQuantidade(5);
    pedido.setItens_pedido(List.of(ipf));

    when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

    var dto = service.findById(1L);

    assertEquals(1, dto.getItens().size());
    assertEquals("Parafuso", dto.getItens().get(0).getNome());
    assertEquals("Fornecedor A", dto.getNomeFornecedor());
}

@Test
void testFindById_NaoEncontrado() {
    when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

    RuntimeException ex = assertThrows(RuntimeException.class, () -> service.findById(1L));
    assertTrue(ex.getMessage().contains("PedidoFornecedor com ID 1 não encontrado."));
}

@Test
void testFindAll() {
    PedidoFornecedor pedido = criarPedidoMock();
    Item item = criarItem();
    ItemPedidoFornecedor ipf = new ItemPedidoFornecedor();
    ipf.setItem(item);
    ipf.setQuantidade(3);
    pedido.setItens_pedido(List.of(ipf));

    when(pedidoRepository.findAll()).thenReturn(List.of(pedido));

    CollectionModel<EntityModel<PedidoFornecedorResponseDTO>> collection = service.findAll();

    assertNotNull(collection);
    assertEquals(1, collection.getContent().size());

    PedidoFornecedorResponseDTO dto = collection.getContent().iterator().next().getContent();
    assertNotNull(dto);
    assertEquals("Fornecedor A", dto.getNomeFornecedor());
    assertEquals("Parafuso", dto.getItens().get(0).getNome());
}


@Test
void testFindByFornecedorId_ComSucesso() {
    PedidoFornecedor pedido = criarPedidoMock();
    when(pedidoRepository.findByFornecedorId(1L)).thenReturn(List.of(pedido));

    List<PedidoFornecedorResponseDTO> result = service.findByFornecedorId(1L);

    assertFalse(result.isEmpty(), "A lista não deveria estar vazia");

    PedidoFornecedorResponseDTO dto = result.get(0);
    assertEquals("Fornecedor A", dto.getNomeFornecedor());
    assertEquals("Parafuso", dto.getItens().get(0).getNome());
}

    @Test
    void testFindByFornecedorId_NenhumEncontrado_DeveLancarExcecao() {
        when(pedidoRepository.findByFornecedorId(1L)).thenReturn(Collections.emptyList());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.findByFornecedorId(1L));
        assertEquals("Nenhum pedido encontrado para o fornecedor com ID 1", ex.getMessage());
    }

}
