package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.Model.Pedido;
import com.web2.movelcontrol.Model.Orcamento; // Necessário para buscar o orçamento
import com.web2.movelcontrol.Repository.PedidoRepository;
import com.web2.movelcontrol.Repository.OrcamentoRepository; // Para buscar o orçamento
import com.web2.movelcontrol.Exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Date;
import java.util.logging.Logger;

@Service
public class PedidoService {

    private Logger logger = Logger.getLogger(PedidoService.class.getName());

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private OrcamentoRepository orcamentoRepository;

    // Método para criar um novo pedido
    public Pedido criarPedido(Pedido pedido) {
        if (pedido.getOrcamento() == null || pedido.getOrcamento().getId() == null) {
            throw new IllegalArgumentException("ID do Orçamento não pode ser nulo para criar um Pedido.");
        }
        Long orcamentoId = pedido.getOrcamento().getId();
        Orcamento orcamentoExistente = orcamentoRepository.findById(orcamentoId)
                .orElseThrow(() -> new NotFoundException("Orçamento com ID " + orcamentoId + " não encontrado. Não é possível criar o Pedido."));
        pedido.setOrcamento(orcamentoExistente);

        if (pedido.getData_pedido() == null) {
            pedido.setData_pedido(new Date());
        }
        if (pedido.getStatus() == null || pedido.getStatus().isEmpty()) {
            pedido.setStatus("PENDENTE"); // Status inicial
        }
        logger.info("Criando um novo pedido.");
        return pedidoRepository.save(pedido);
    }

    // Método para buscar um pedido por ID
    public Pedido buscarPedidoPorId(Long id) {
        logger.info("Buscando pedido com ID: " + id);
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado com ID: " + id));
    }

    // Método para listar todos os pedidos
    public List<Pedido> listarTodosPedidos() {
        logger.info("Listando todos os pedidos.");
        return pedidoRepository.findAll();
    }

    // Método para atualizar um pedido (ex: status, descrição)
    public Pedido atualizarPedido(Long id, Pedido pedidoAtualizado) {
        logger.info("Atualizando pedido com ID: " + id);
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado com ID: " + id + ", não foi possível atualizar."));

        // Atualiza os campos permitidos. O orçamento vinculado geralmente não muda após a criação do pedido.
        // A data do pedido também pode ser imutável.
        if (pedidoAtualizado.getStatus() != null && !pedidoAtualizado.getStatus().isEmpty()) {
            pedidoExistente.setStatus(pedidoAtualizado.getStatus());
        }
        if (pedidoAtualizado.getDescricao() != null) {
            pedidoExistente.setDescricao(pedidoAtualizado.getDescricao());
        }
        // Se houver outros campos atualizáveis (ex: data de entrega prevista), adicione aqui.

        return pedidoRepository.save(pedidoExistente);
    }

    // Método para deletar um pedido
    public void deletarPedido(Long id) {
        logger.info("Deletando pedido com ID: " + id);
        if (!pedidoRepository.existsById(id)) {
            throw new NotFoundException("Pedido não encontrado com ID: " + id + ", não foi possível deletar.");
        }
        // Atenção: Verificar se há notas fiscais vinculadas a este pedido antes de deletar.
        // A entidade NotaFiscal tem um OneToOne com Pedido. A constraint uc_nota_fiscal_pedido
        // pode causar problemas.
        // Considere a lógica de negócio para exclusão.
        pedidoRepository.deleteById(id);
        logger.info("Pedido com ID: " + id + " deletado com sucesso.");
    }
}