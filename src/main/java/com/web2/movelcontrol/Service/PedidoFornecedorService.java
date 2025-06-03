package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.DTO.*;
import com.web2.movelcontrol.Model.*;
import com.web2.movelcontrol.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PedidoFornecedorService {

        @Autowired
        private PedidoFornecedorRepository pedidoRepository;

        @Autowired
        private ItemRepository itemRepository;

        @Autowired
        private FornecedorRepository fornecedorRepository;



        public PedidoFornecedor create(PedidoFornecedorRequestDTO dto) {
                PedidoFornecedor pedido = new PedidoFornecedor();
                pedido.setDataPedido(dto.getDataPedido() != null ? dto.getDataPedido() : new Date());
                pedido.setStatus(dto.getStatus());

                Fornecedor fornecedor = fornecedorRepository.findById(dto.getFornecedorId())
                                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado."));
                pedido.setFornecedor(fornecedor);

                List<ItemPedidoFornecedor> itensPedido = new ArrayList<>();

                for (ItemPedidoFornecedorRequestDTO itemDTO : dto.getItens()) {
                        Item item = itemRepository.findById(itemDTO.getItemId())
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Item com ID " + itemDTO.getItemId() + " não encontrado."));

                        ItemPedidoFornecedor ipf = new ItemPedidoFornecedor();
                        ipf.setItem(item);
                        ipf.setPedido(pedido);
                        ipf.setQuantidade(itemDTO.getQuantidade());

                        itensPedido.add(ipf);
                }

                pedido.setItens_pedido(itensPedido);
                return pedidoRepository.save(pedido);
        }

        public PedidoFornecedor update(Long id, PedidoFornecedorRequestDTO dto) {
                PedidoFornecedor existing = pedidoRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "PedidoFornecedor com ID " + id + " não encontrado."));

                existing.setDataPedido(dto.getDataPedido());
                existing.setStatus(dto.getStatus());

                if (dto.getFornecedorId() != null) {
                        Fornecedor fornecedor = fornecedorRepository.findById(dto.getFornecedorId())
                                        .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado."));
                        existing.setFornecedor(fornecedor);
                }

                // Remove os antigos e insere os novos
                existing.getItens_pedido().clear();

                List<ItemPedidoFornecedor> novosItens = new ArrayList<>();
                for (ItemPedidoFornecedorRequestDTO itemDTO : dto.getItens()) {
                        Item item = itemRepository.findById(itemDTO.getItemId())
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Item com ID " + itemDTO.getItemId() + " não encontrado."));

                        ItemPedidoFornecedor ipf = new ItemPedidoFornecedor();

                        ItemPedidoFornecedorId compositeId = new ItemPedidoFornecedorId(); // renomeado
                        compositeId.setPedidoId(existing.getId());
                        compositeId.setItemId(item.getId());

                        ipf.setId(compositeId);
                        ipf.setItem(item);
                        ipf.setPedido(existing);
                        ipf.setQuantidade(itemDTO.getQuantidade());

                        novosItens.add(ipf);
                }

                existing.getItens_pedido().clear();
                existing.getItens_pedido().addAll(novosItens);

                return pedidoRepository.save(existing);
        }

        public void delete(Long id) {
                PedidoFornecedor pedido = pedidoRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "PedidoFornecedor com ID " + id + " não encontrado."));

                pedido.getItens_pedido().clear();
                pedidoRepository.save(pedido);
                pedidoRepository.deleteById(id);
        }

        public List<PedidoFornecedorResponseDTO> findAll() {
                List<PedidoFornecedor> pedidos = pedidoRepository.findAll();

                return pedidos.stream().map(pedido -> {
                        String nomeFornecedor = pedido.getFornecedor() != null ? pedido.getFornecedor().getNome()
                                        : null;

                        List<ItemPedidoFornecedorResponseDTO> itens = pedido.getItens_pedido().stream()
                                        .map(ipf -> new ItemPedidoFornecedorResponseDTO(
                                                        ipf.getItem().getId(),
                                                        ipf.getItem().getNome(),
                                                        ipf.getItem().getDescricao(),
                                                        ipf.getQuantidade()))
                                        .toList();

                        return new PedidoFornecedorResponseDTO(
                                        pedido.getId(),
                                        pedido.getStatus(),
                                        pedido.getDataPedido(),
                                        nomeFornecedor,
                                        itens);
                }).toList();
        }

        public PedidoFornecedorResponseDTO findById(Long id) {
                PedidoFornecedor pedido = pedidoRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "PedidoFornecedor com ID " + id + " não encontrado."));

                String nomeFornecedor = pedido.getFornecedor() != null ? pedido.getFornecedor().getNome() : null;

                List<ItemPedidoFornecedorResponseDTO> itens = pedido.getItens_pedido().stream()
                                .map(ipf -> new ItemPedidoFornecedorResponseDTO(
                                                ipf.getItem().getId(),
                                                ipf.getItem().getNome(),
                                                ipf.getItem().getDescricao(),
                                                ipf.getQuantidade()))
                                .toList();

                return new PedidoFornecedorResponseDTO(
                                pedido.getId(),
                                pedido.getStatus(),
                                pedido.getDataPedido(),
                                nomeFornecedor,
                                itens);
        }
}
