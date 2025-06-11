package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.Item;
import com.web2.movelcontrol.Repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    @InjectMocks
    private ItemService service;

    @Mock
    private ItemRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Item criarItem(Long id) {
        Item item = new Item();
        item.setId(id);
        item.setNome("Parafuso Philips");
        item.setDescricao("Parafuso de aço galvanizado, 5mm");
        item.setPrecoUnitario(0.25);
        return item;
    }

    @Test
    void testCreate() {
        Item item = criarItem(1L);

        when(repository.save(any(Item.class))).thenReturn(item);

        Item result = service.create(item);

        assertNotNull(result);
        assertEquals("Parafuso Philips", result.getNome());
        verify(repository, times(1)).save(item);
    }

    @Test
    void testFindById_ComSucesso() {
        Item item = criarItem(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(item));

        Item result = service.findById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Parafuso Philips", result.getNome());
        verify(repository).findById(1L);
    }

    @Test
    void testFindById_NaoEncontrado() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.findById(1L));
        verify(repository).findById(1L);
    }

    @Test
    void testUpdate_ComSucesso() {
        Item itemAntigo = criarItem(1L);
        Item itemNovo = criarItem(1L);
        itemNovo.setNome("Parafuso Allen");

        when(repository.findById(1L)).thenReturn(Optional.of(itemAntigo));
        when(repository.save(any(Item.class))).thenReturn(itemNovo);

        Item result = service.update(1L, itemNovo);

        assertEquals("Parafuso Allen", result.getNome());
        assertEquals(itemNovo.getDescricao(), result.getDescricao());
        verify(repository).save(itemAntigo);
    }

    @Test
    void testUpdate_NaoEncontrado() {
        Item itemNovo = criarItem(1L);

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.update(1L, itemNovo));
        verify(repository, never()).save(any());
    }

    @Test
    void testDelete() {
        doNothing().when(repository).deleteById(1L);

        service.delete(1L);

        verify(repository).deleteById(1L);
    }
}
