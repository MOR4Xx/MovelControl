package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.DTO.DataMapper;
import com.web2.movelcontrol.DTO.NotaFiscalRequestDTO;
import com.web2.movelcontrol.DTO.NotaFiscalResponseDTO;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotaFiscalServiceTest {

    @Mock
    private NotaFiscalRepository repository;

    @InjectMocks
    private NotaFiscalService service;

    private NotaFiscal notaFiscal;
    private NotaFiscalRequestDTO notaFiscalRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Pedido pedido = new Pedido();
        pedido.setId(1L);

        notaFiscal = new NotaFiscal();
        notaFiscal.setId(1L);
        notaFiscal.setCodigo("N°000.000.000");
        notaFiscal.setData_emissao(new Date());
        notaFiscal.setValor(500.0);
        notaFiscal.setPedido(pedido);

        notaFiscalRequestDTO = new NotaFiscalRequestDTO();
        notaFiscalRequestDTO.setCodigo("N°000.000.000");
        notaFiscalRequestDTO.setDataEmissao(new Date());
        notaFiscalRequestDTO.setIdPedido(1L);
        notaFiscalRequestDTO.setValor(500.0);
    }

    @Test
    void testCreateNotaFiscal() {
        when(repository.save(any(NotaFiscal.class))).thenReturn(notaFiscal);

        NotaFiscalResponseDTO responseDTO = service.create(notaFiscalRequestDTO);

        assertNotNull(responseDTO);
        assertEquals(notaFiscal.getCodigo(), responseDTO.getCodigo());
        assertEquals(notaFiscal.getValor(), responseDTO.getValor());

        verify(repository, times(1)).save(any(NotaFiscal.class));
    }

    @Test
    void testFindByIdExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(notaFiscal));

        var result = service.findById(1L);

        assertNotNull(result);
        assertEquals(notaFiscal.getId(), result.getContent().getIdPedido());

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotExistsThrowsNotFoundException() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.findById(2L));

        verify(repository, times(1)).findById(2L);
    }

    @Test
    void testFindAllNotasFiscais() {
        when(repository.findAll()).thenReturn(List.of(notaFiscal));

        var result = service.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(notaFiscal.getCodigo(), result.get(0).getContent().getCodigo());

        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindByCodigo() {
        when(repository.findByCodigo("N°000.000.000")).thenReturn(notaFiscal);

        NotaFiscalResponseDTO responseDTO = service.findByCodigo("N°000.000.000");

        assertNotNull(responseDTO);
        assertEquals(notaFiscal.getCodigo(), responseDTO.getCodigo());

        verify(repository, times(1)).findByCodigo("N°000.000.000");
    }

    @Test
    void testDeleteNotaFiscal() {
        doNothing().when(repository).deleteById(1L);

        service.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}