package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.DTO.FornecedorRequestDTO;
import com.web2.movelcontrol.DTO.FornecedorResponseDTO;
import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.Endereco;
import com.web2.movelcontrol.Model.Fornecedor;
import com.web2.movelcontrol.Repository.FornecedorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FornecedorServiceTest {

    @InjectMocks
    private FornecedorService service;

    @Mock
    private FornecedorRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private FornecedorRequestDTO createRequestDTO() {
        FornecedorRequestDTO dto = new FornecedorRequestDTO();
        dto.setNome("Fornecedor Teste");
        dto.setCnpj("12345678000199");
        dto.setTelefone("62999998888");
        dto.setEmail("teste@fornecedor.com");

        Endereco endereco = new Endereco();
        endereco.setCep("74000000");
        endereco.setRua("Rua Teste");
        endereco.setNumero("123");
        endereco.setComplemento("Sala 1");
        endereco.setBairro("Centro");
        dto.setEndereco(endereco);

        return dto;
    }

    private Fornecedor createFornecedor(Long id) {
        Fornecedor f = new Fornecedor();
        f.setId(id);
        f.setNome("Fornecedor Teste");
        f.setCnpj("12345678000199");
        f.setTelefone("62999998888");
        f.setEmail("teste@fornecedor.com");

        Endereco e = new Endereco();
        e.setCep("74000000");
        e.setRua("Rua Teste");
        e.setNumero("123");
        e.setComplemento("Sala 1");
        e.setBairro("Centro");

        f.setEndereco(e);
        return f;
    }

    @Test
    void testCreateFornecedor() {
        FornecedorRequestDTO dto = createRequestDTO();
        Fornecedor fornecedor = createFornecedor(1L);

        when(repository.save(any(Fornecedor.class))).thenReturn(fornecedor);

        FornecedorResponseDTO result = service.create(dto);

        assertNotNull(result);
        assertEquals("Fornecedor Teste", result.getNome());
        verify(repository, times(1)).save(any(Fornecedor.class));
    }

    @Test
    void testDeleteFornecedorComSucesso() {
        when(repository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteFornecedorNaoEncontrado() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> service.delete(1L));
    }

    @Test
    void testUpdateFornecedor() {
        Fornecedor fornecedor = createFornecedor(1L);
        FornecedorRequestDTO dto = createRequestDTO();

        when(repository.findById(1L)).thenReturn(Optional.of(fornecedor));
        when(repository.save(any())).thenReturn(fornecedor);

        FornecedorResponseDTO result = service.update(1L, dto);

        assertEquals(dto.getNome(), result.getNome());
        verify(repository).save(fornecedor);
    }

    @Test
    void testFindByIdComSucesso() {
        Fornecedor fornecedor = createFornecedor(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(fornecedor));

        FornecedorResponseDTO result = service.findById(1L);

        assertEquals(fornecedor.getNome(), result.getNome());
        verify(repository).findById(1L);
    }

    @Test
    void testFindByIdNaoEncontrado() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.findById(1L));
    }

    
@Test
void testFindAll() {
    Fornecedor fornecedor = createFornecedor(1L);
    when(repository.findAll()).thenReturn(List.of(fornecedor));

    CollectionModel<EntityModel<FornecedorResponseDTO>> result = service.findAll();

    assertNotNull(result);
    assertEquals(1, result.getContent().size());

    EntityModel<FornecedorResponseDTO> model = result.getContent().iterator().next();
    FornecedorResponseDTO dto = model.getContent();

    assertNotNull(dto);
    assertEquals(fornecedor.getNome(), dto.getNome());

    // Verifica se o link HATEOAS foi adicionado corretamente
    assertTrue(model.hasLink("pedidos-fornecedor"));
    assertEquals("/pFornecedor/buscar/fornecedor/1", 
        model.getLink("pedidos-fornecedor").get().getHref());

    verify(repository).findAll();
}
}
