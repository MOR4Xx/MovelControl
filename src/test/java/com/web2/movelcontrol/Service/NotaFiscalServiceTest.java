package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.NotaFiscal;
import com.web2.movelcontrol.Model.Pedido;
import com.web2.movelcontrol.Repository.NotaFiscalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotaFiscalServiceTest {

    @Mock
    private NotaFiscalRepository repository;

    @InjectMocks
    private NotaFiscalService service;

    private NotaFiscal notaFiscal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Pedido pedido = new Pedido();
        pedido.setId(1L);

        notaFiscal = new NotaFiscal();
        notaFiscal.setId(1L);
        notaFiscal.setCodigo("NF-0001");
        notaFiscal.setData_emissao(new Date());
        notaFiscal.setValor(150.50);
        notaFiscal.setPedido(pedido);
    }

    @Test
    void createNotaFiscal() {
        when(repository.save(notaFiscal)).thenReturn(notaFiscal);

        NotaFiscal created = service.create(notaFiscal);

        assertNotNull(created);
        assertEquals("NF-0001", created.getCodigo());
        assertEquals(150.50, created.getValor());
        verify(repository, times(1)).save(notaFiscal);
    }

    @Test
    void findNotaFiscalByIdExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(notaFiscal));

        NotaFiscal found = service.findById(1L);

        assertNotNull(found);
        assertEquals("NF-0001", found.getCodigo());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void findNotaFiscalByIdNotExists() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.findById(2L));
        verify(repository, times(1)).findById(2L);
    }

    @Test
    void updateNotaFiscalSuccess() {
        NotaFiscal notaAtualizada = new NotaFiscal();
        notaAtualizada.setCodigo("NF-UPDATED");
        notaAtualizada.setValor(200.00);

        when(repository.findById(1L)).thenReturn(Optional.of(notaFiscal));
        when(repository.save(any(NotaFiscal.class))).thenReturn(notaFiscal);

        NotaFiscal updated = service.update(1L, notaAtualizada);

        assertNotNull(updated);
        assertEquals("NF-UPDATED", updated.getCodigo());
        assertEquals(200.00, updated.getValor());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(NotaFiscal.class));
    }

    @Test
    void updateNotaFiscalNotFound() {
        NotaFiscal notaAtualizada = new NotaFiscal();
        notaAtualizada.setCodigo("NF-UPDATED");

        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.update(99L, notaAtualizada));
        verify(repository, times(1)).findById(99L);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteNotaFiscalSuccess() {
        doNothing().when(repository).deleteById(1L);

        service.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deleteNotaFiscalNotFound() {
        doThrow(new NotFoundException("Nota fiscal não encontrada")).when(repository).deleteById(99L);

        assertThrows(NotFoundException.class, () -> service.delete(99L));
        verify(repository, times(1)).deleteById(99L);
    }
}