package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.DTO.PedidoRequestDTO;
import com.web2.movelcontrol.Model.Pedido;
import com.web2.movelcontrol.Model.Orcamento;
import com.web2.movelcontrol.Repository.PedidoRepository;
import com.web2.movelcontrol.Repository.OrcamentoRepository;
import com.web2.movelcontrol.Repository.NotaFiscalRepository; // Importar NotaFiscalRepository
import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Exceptions.ConflictException; // Importar ConflictException
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
    
    // AGORA: public Pedido atualizarPedido(Long id, PedidoRequestDTO pedidoDTO)
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
        
        // Geralmente, o orçamento de um pedido não é alterado após a criação.
        // Se for permitido, você precisaria adicionar lógica aqui para buscar o novo orçamento:
        if (pedidoDTO.getOrcamentoId() != null &&
                (pedidoExistente.getOrcamento() == null || !pedidoExistente.getOrcamento().getId().equals(pedidoDTO.getOrcamentoId()))) {
            Orcamento novoOrcamento = orcamentoRepository.findById(pedidoDTO.getOrcamentoId())
                    .orElseThrow(() -> new NotFoundException("Novo Orçamento com ID " + pedidoDTO.getOrcamentoId() + " não encontrado."));
            pedidoExistente.setOrcamento(novoOrcamento);
        }
        // Se orcamentoId não vier no DTO de atualização, ou se a regra for não permitir mudar o orçamento,
        // o orçamento existente permanece.
        
        return pedidoRepository.save(pedidoExistente);
    }
    
    @Transactional
    public void deletarPedido(Long id) {
        logger.info("Tentando deletar pedido com ID: " + id);
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado com ID: " + id + ", não foi possível deletar."));
        
        // Regra de Negócio: Verificar se existe NotaFiscal vinculada ao Pedido
        // ANTES: // Atenção: Verificar se há notas fiscais vinculadas...
        // AGORA: Implementação da verificação
        // Precisamos de um método no NotaFiscalRepository para buscar por Pedido, ou
        // carregar a NotaFiscal através do Pedido se o relacionamento for bidirecional e o Pedido tiver a referência.
        // A entidade NotaFiscal tem: @OneToOne @JoinColumn(name = "pedido_id") private Pedido pedido;
        // Vamos assumir que precisamos buscar a nota fiscal pelo pedido_id.
        // Se não houver método direto, podemos verificar pela existência.
        // Seu NotaFiscalRepository provavelmente é JpaRepository<NotaFiscal, Long>
        // Adicione um método ao NotaFiscalRepository: `boolean existsByPedidoId(Long pedidoId);`
        
        // Para essa verificação, você precisará adicionar um método ao seu NotaFiscalRepository:
        // Exemplo no NotaFiscalRepository: `boolean existsByPedido_Id(Long pedidoId);`
        // Ou, se o relacionamento NotaFiscal->Pedido for o dono (como parece ser),
        // e Pedido não tiver uma referência direta para NotaFiscal, a busca deve ser no NotaFiscalRepository.
        
        // ALTERNATIVA SEM ALTERAR REPOSITORY (se a entidade Pedido tiver a referência para NotaFiscal, o que não parece ser o caso)
        // if (pedido.getNotaFiscal() != null) { // Isso exigiria um campo NotaFiscal na entidade Pedido
        //    throw new ConflictException("Não é possível deletar o Pedido com ID " + id + " pois existe uma Nota Fiscal vinculada.");
        // }
        
        // FORMA CORRETA (buscando no NotaFiscalRepository)
        // Crie o método no NotaFiscalRepository: `Optional<NotaFiscal> findByPedido_Id(Long pedidoId);` ou `boolean existsByPedido_Id(Long pedidoId);`
        // Vamos assumir que você criará `boolean existsByPedido_Id(Long pedidoId);` no NotaFiscalRepository.
        
        // --- Início da Lógica de Verificação ---
        // Você precisará ter o NotaFiscalRepository injetado e um método nele como:
        // `boolean existsByPedidoId(Long pedidoId);` ou `Optional<NotaFiscal> findByPedido(Pedido pedido);`
        // Por agora, vou simular a verificação. Você precisará implementar o método no repositório.
        
        // **Para fazer isso funcionar, adicione ao seu NotaFiscalRepository.java:**
        // ```java
        // import java.util.Optional;
        // public interface NotaFiscalRepository extends JpaRepository<NotaFiscal, Long> {
        //     Optional<NotaFiscal> findByPedido(Pedido pedido);
        //     // ou boolean existsByPedido(Pedido pedido);
        //     // ou boolean existsByPedido_Id(Long pedidoId); // Se preferir buscar pelo ID do pedido
        // }
        // ```
        // Escolhendo a abordagem `existsByPedido_Id`:
        boolean notaFiscalExistente = notaFiscalRepository.existsByPedido_Id(id); // Supondo que você adicionou este método ao NotaFiscalRepository
        if (notaFiscalExistente) {
            throw new ConflictException("Não é possível deletar o Pedido com ID " + id + " pois existe uma Nota Fiscal (ID: " + id + ") vinculada.");
        }
        // --- Fim da Lógica de Verificação ---
        
        pedidoRepository.delete(pedido);
        logger.info("Pedido com ID: " + id + " deletado com sucesso.");
    }
}