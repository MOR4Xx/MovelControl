package com.web2.movelcontrol.Controller;

import com.web2.movelcontrol.DTO.PedidoRequestDTO;
import com.web2.movelcontrol.DTO.PedidoResponseDTO;
import com.web2.movelcontrol.DTO.DataMapper;
import com.web2.movelcontrol.Model.Pedido;
import com.web2.movelcontrol.Service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos", description = "Operações relacionadas a Pedidos de Clientes")
public class PedidoController {
    
    @Autowired
    private PedidoService pedidoService;
    
    @Operation(summary = "Cria um novo pedido", responses = {
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Orçamento não encontrado")
    })
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@RequestBody @Valid PedidoRequestDTO pedidoDTO) {
        Pedido pedidoSalvo = pedidoService.criarPedido(pedidoDTO);
        PedidoResponseDTO responseDTO = mapToPedidoResponseDTO(pedidoSalvo);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Busca um pedido por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPedidoPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPedidoPorId(id);
        PedidoResponseDTO responseDTO = mapToPedidoResponseDTO(pedido);
        return ResponseEntity.ok(responseDTO);
    }
    
    @Operation(summary = "Lista todos os pedidos", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarTodosPedidos() {
        List<Pedido> pedidos = pedidoService.listarTodosPedidos();
        List<PedidoResponseDTO> responseDTOs = pedidos.stream()
                .map(this::mapToPedidoResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }
    
    @Operation(summary = "Atualiza um pedido existente", responses = {
            @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> atualizarPedido(@PathVariable Long id, @RequestBody @Valid PedidoRequestDTO pedidoDTO) {
        Pedido pedidoAtualizado = pedidoService.atualizarPedido(id, pedidoDTO);
        PedidoResponseDTO responseDTO = mapToPedidoResponseDTO(pedidoAtualizado);
        return ResponseEntity.ok(responseDTO);
    }
    
    @Operation(summary = "Deleta um pedido por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Pedido deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito - Pedido possui nota fiscal vinculada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        pedidoService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }
    
    // Método auxiliar para mapear Pedido para PedidoResponseDTO
    private PedidoResponseDTO mapToPedidoResponseDTO(Pedido pedido) {
        if (pedido == null) {
            return null;
        }
        PedidoResponseDTO dto = DataMapper.parseObject(pedido, PedidoResponseDTO.class);
        
        // O ModelMapper dentro do DataMapper pode não mapear data_pedido para dataPedido automaticamente.
        // Se isso acontecer, sete manualmente:
        if (dto.getDataPedido() == null && pedido.getData_pedido() != null) {
            dto.setDataPedido(pedido.getData_pedido());
        }
        
        // Mapear o orcamentoId
        if (pedido.getOrcamento() != null) {
            dto.setOrcamentoId(pedido.getOrcamento().getId());
        }
        return dto;
    }
}