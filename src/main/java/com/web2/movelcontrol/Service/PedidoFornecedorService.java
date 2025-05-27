package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.DTO.ItemDTO;
import com.web2.movelcontrol.DTO.PedidoFornecedorResponseDTO;
import com.web2.movelcontrol.Model.Fornecedor;
import com.web2.movelcontrol.Model.Item;
import com.web2.movelcontrol.Model.PedidoFornecedor;
import com.web2.movelcontrol.Repository.ItemRepository;
import com.web2.movelcontrol.Repository.PedidoFornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web2.movelcontrol.Repository.FornecedorRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoFornecedorService {

    @Autowired
    private PedidoFornecedorRepository pedidoRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public PedidoFornecedor create(PedidoFornecedor pedido) {
        List<Item> itensCompletos = new ArrayList<>();

        for (Item itemParcial : pedido.getItens_pedido()) {
            Item itemCompleto = itemRepository.findById(itemParcial.getId())
                    .orElseThrow(() -> new RuntimeException("Item com ID " + itemParcial.getId() + " não encontrado."));
            itensCompletos.add(itemCompleto);
        }

        pedido.setItens_pedido(itensCompletos);
        return pedidoRepository.save(pedido);
    }

    public PedidoFornecedor update(Long id, PedidoFornecedor updatedPedido) {
        PedidoFornecedor existing = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PedidoFornecedor com ID " + id + " não encontrado."));

        existing.setDataPedido(updatedPedido.getDataPedido());
        existing.setStatus(updatedPedido.getStatus());

        if (updatedPedido.getFornecedor() != null) {
            Fornecedor fornecedor = fornecedorRepository.findById(updatedPedido.getFornecedor().getId())
                    .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado."));
            existing.setFornecedor(fornecedor);
        }

        if (updatedPedido.getItens_pedido() != null) {
            List<Item> itens = new ArrayList<>();
            for (Item item : updatedPedido.getItens_pedido()) {
                Item found = itemRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Item com ID " + item.getId() + " não encontrado."));
                itens.add(found);
            }
            existing.setItens_pedido(itens);
        }

        return pedidoRepository.save(existing);
    }

    public void delete(Long id) {
        PedidoFornecedor pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PedidoFornecedor com ID " + id + " não encontrado."));

        // Remove os vínculos com itens antes de deletar
        pedido.getItens_pedido().clear();
        pedidoRepository.save(pedido); // Salva o estado desvinculado

        // Agora pode remover o pedido com segurança
        pedidoRepository.deleteById(id);
    }

    public List<PedidoFornecedorResponseDTO> findAll() {
        List<PedidoFornecedor> pedidos = pedidoRepository.findAll();

        return pedidos.stream().map(pedido -> {
            String nomeFornecedor = pedido.getFornecedor() != null ? pedido.getFornecedor().getNome() : null;

            List<ItemDTO> itens = pedido.getItens_pedido().stream()
                    .map(item -> new ItemDTO(item.getId(), item.getNome(), item.getDescricao())).toList();

            return new PedidoFornecedorResponseDTO(
                    pedido.getId(),
                    pedido.getStatus(),
                    pedido.getDataPedido(),
                    nomeFornecedor,
                    itens);
        }).toList();
    }

}
