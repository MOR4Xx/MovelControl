package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.Model.Item;
import com.web2.movelcontrol.Model.PedidoFornecedor;
import com.web2.movelcontrol.Repository.ItemRepository;
import com.web2.movelcontrol.Repository.PedidoFornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoFornecedorService {

    @Autowired
    private PedidoFornecedorRepository pedidoFornecedorRepository;

    @Autowired
    private ItemRepository itemRepository;

    public PedidoFornecedor create(PedidoFornecedor pedido) {
        List<Item> itensCompletos = new ArrayList<>();

        // Valida e busca todos os itens do banco com base no ID recebido
        for (Item itemParcial : pedido.getItens_pedido()) {
            Item itemCompleto = itemRepository.findById(itemParcial.getId())
                    .orElseThrow(() -> new RuntimeException("Item com ID " + itemParcial.getId() + " não encontrado."));
            itensCompletos.add(itemCompleto);
        }

        // Substitui os itens incompletos pela lista completa
        pedido.setItens_pedido(itensCompletos);

        // Salva o pedido com os itens vinculados
        return pedidoFornecedorRepository.save(pedido);
    }
}
