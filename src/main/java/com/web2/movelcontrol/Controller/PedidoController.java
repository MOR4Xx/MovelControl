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
@Tag(name = "Pedidos", description = "Endpoints para gerenciar Pedidos")
public class PedidoController {
    
    @Autowired
    private PedidoService pedidoService;
    
    
    @PostMapping
    @Operation(summary = "Cria um novo pedido",
            description = "Este endpoint permite a criação de um novo pedido.",
            tags = {"Pedidos"},
            responses = {
                    @ApiResponse(description = "Pedido Criado com Sucesso", responseCode = "201",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PedidoResponseDTO.class) // Usando a entidade Pedido diretamente
                            )
                    ),
                    @ApiResponse(description = "Requisição Inválida (ex: orçamento não informado ou inválido)", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Recurso Não Encontrado (ex: Orçamento associado não existe)", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<PedidoResponseDTO> criarPedido(@RequestBody @Valid PedidoRequestDTO pedidoDTO) {
        Pedido pedidoSalvo = pedidoService.criarPedido(pedidoDTO);
        PedidoResponseDTO responseDTO = mapToPedidoResponseDTO(pedidoSalvo);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
    
    
    @GetMapping("/{id}")
    @Operation(summary = "Busca um pedido por ID",
            description = "Retorna os detalhes de um pedido específico com base no seu ID.",
            tags = {"Pedidos"},
            responses = {
                    @ApiResponse(description = "Pedido Encontrado", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PedidoResponseDTO.class)
                            )
                    ),
                    @ApiResponse(description = "Pedido Não Encontrado", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Requisição Inválida", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<PedidoResponseDTO> buscarPedidoPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPedidoPorId(id);
        PedidoResponseDTO responseDTO = mapToPedidoResponseDTO(pedido);
        return ResponseEntity.ok(responseDTO);
    }
    
    
    @GetMapping
    @Operation(summary = "Lista todos os pedidos",
            description = "Retorna uma lista de todos os pedidos cadastrados no sistema.",
            tags = {"Pedidos"},
            responses = {
                    @ApiResponse(description = "Lista de Pedidos Obtida com Sucesso", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    // Como é uma lista, usamos arraySchema
                                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @Schema(implementation = PedidoResponseDTO.class))
                            )
                    ),
                    @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<List<PedidoResponseDTO>> listarTodosPedidos() {
        List<Pedido> pedidos = pedidoService.listarTodosPedidos();
        List<PedidoResponseDTO> responseDTOs = pedidos.stream()
                .map(this::mapToPedidoResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }
    
  
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um pedido existente",
            description = "Permite a atualização dos dados de um pedido existente (ex: status, descrição), identificado pelo seu ID.",
            tags = {"Pedidos"},
            responses = {
                    @ApiResponse(description = "Pedido Atualizado com Sucesso", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PedidoResponseDTO.class)
                            )
                    ),
                    @ApiResponse(description = "Requisição Inválida (dados do pedido)", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Pedido Não Encontrado", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<PedidoResponseDTO> atualizarPedido(@PathVariable Long id, @RequestBody @Valid PedidoRequestDTO pedidoDTO) {
        Pedido pedidoAtualizado = pedidoService.atualizarPedido(id, pedidoDTO);
        PedidoResponseDTO responseDTO = mapToPedidoResponseDTO(pedidoAtualizado);
        return ResponseEntity.ok(responseDTO);
    }
    
   
    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um pedido por ID",
            description = "Remove um pedido do sistema com base no seu ID.",
            tags = {"Pedidos"},
            responses = {
                    @ApiResponse(description = "Pedido Deletado com Sucesso", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Requisição Inválida", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Pedido Não Encontrado", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
            }
    )
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