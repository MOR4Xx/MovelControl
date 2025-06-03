package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.DTO.PedidoRequestDTO;
import com.web2.movelcontrol.Model.Pedido;
import com.web2.movelcontrol.Model.Orcamento;
import com.web2.movelcontrol.Repository.PedidoRepository;
import com.web2.movelcontrol.Repository.OrcamentoRepository;
import com.web2.movelcontrol.Repository.NotaFiscalRepository;
import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Exceptions.ConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    
    @Autowired // Injetar o NotaFiscalRepository
    private NotaFiscalRepository notaFiscalRepository;
    
    @Transactional
    public Pedido criarPedido(PedidoRequestDTO pedidoDTO) {
        Orcamento orcamentoExistente = orcamentoRepository.findById(pedidoDTO.getOrcamentoId())
                .orElseThrow(() -> new NotFoundException("Orçamento com ID " + pedidoDTO.getOrcamentoId() + " não encontrado. Não é possível criar o Pedido."));
        
        Pedido novoPedido = new Pedido();
        
        if (pedidoDTO.getDataPedido() == null) {
            novoPedido.setData_pedido(new Date());
        } else {
            novoPedido.setData_pedido(pedidoDTO.getDataPedido());
        }
        novoPedido.setStatus(pedidoDTO.getStatus());
        if (pedidoDTO.getDescricao() != null) {
            novoPedido.setDescricao(pedidoDTO.getDescricao());
        }
        novoPedido.setOrcamento(orcamentoExistente);
        
        logger.info("Criando um novo pedido a partir de DTO.");
        return pedidoRepository.save(novoPedido);
    }
    
    public Pedido buscarPedidoPorId(Long id) {
        logger.info("Buscando pedido com ID: " + id);
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado com ID: " + id));
    }
    
    public List<Pedido> listarTodosPedidos() {
        logger.info("Listando todos os pedidos.");
        return pedidoRepository.findAll();
    }
    
    public Pedido atualizarPedido(Long id, PedidoRequestDTO pedidoDTO) {
        logger.info("Atualizando pedido com ID: " + id + " a partir de DTO.");
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado com ID: " + id + ", não foi possível atualizar."));
        
        // Atualiza os campos permitidos
        if (pedidoDTO.getDataPedido() != null) {
            pedidoExistente.setData_pedido(pedidoDTO.getDataPedido()); // Atenção ao nome do campo na entidade
        }
        if (pedidoDTO.getStatus() != null && !pedidoDTO.getStatus().isEmpty()) {
            pedidoExistente.setStatus(pedidoDTO.getStatus());
        }
        if (pedidoDTO.getDescricao() != null) {
            pedidoExistente.setDescricao(pedidoDTO.getDescricao());
        }
        
       
        if (pedidoDTO.getOrcamentoId() != null &&
                (pedidoExistente.getOrcamento() == null || !pedidoExistente.getOrcamento().getId().equals(pedidoDTO.getOrcamentoId()))) {
            Orcamento novoOrcamento = orcamentoRepository.findById(pedidoDTO.getOrcamentoId())
                    .orElseThrow(() -> new NotFoundException("Novo Orçamento com ID " + pedidoDTO.getOrcamentoId() + " não encontrado."));
            pedidoExistente.setOrcamento(novoOrcamento);
        }
     
        return pedidoRepository.save(pedidoExistente);
    }
    
    @Transactional
    public void deletarPedido(Long id) {
        logger.info("Tentando deletar pedido com ID: " + id);
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado com ID: " + id + ", não foi possível deletar."));
        
        // Escolhendo a abordagem `existsByPedido_Id`:
        boolean notaFiscalExistente = notaFiscalRepository.existsByPedido_Id(id); // Supondo que você adicionou este método ao NotaFiscalRepository
        if (notaFiscalExistente) {
            throw new ConflictException("Não é possível deletar o Pedido com ID " + id + " pois existe uma Nota Fiscal (ID: " + id + ") vinculada.");
        }
        
        pedidoRepository.delete(pedido);
        logger.info("Pedido com ID: " + id + " deletado com sucesso.");
    }
}