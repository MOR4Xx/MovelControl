package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.Model.Orcamento;
import com.web2.movelcontrol.Repository.OrcamentoRepository;
import com.web2.movelcontrol.Exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class OrcamentoService {

    private Logger logger = Logger.getLogger(OrcamentoService.class.getName());

    @Autowired
    private OrcamentoRepository orcamentoRepository;

    // Método para criar um novo orçamento
    public Orcamento criarOrcamento(Orcamento orcamento) {
        if (orcamento.getDataCriacao() == null) {
            orcamento.setDataCriacao(new Date());
        }
        if (orcamento.getStatus() == null || orcamento.getStatus().isEmpty()) {
            orcamento.setStatus("PENDENTE");
        }
        // Aqui você pode adicionar a lógica para calcular o valorTotal
        // com base na listaMateriais, se aplicável na criação.
        // Ex: orcamento.setValorTotal(calcularValorTotal(orcamento.getListaMateriais()));
        logger.info("Criando um novo orçamento.");
        return orcamentoRepository.save(orcamento);
    }

    // Método para buscar um orçamento por ID
    public Orcamento buscarOrcamentoPorId(Long id) {
        logger.info("Buscando orçamento com ID: " + id);
        return orcamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Orçamento não encontrado com ID: " + id));
    }

    // Método para listar todos os orçamentos
    public List<Orcamento> listarTodosOrcamentos() {
        logger.info("Listando todos os orçamentos.");
        return orcamentoRepository.findAll();
    }

    // Método para atualizar um orçamento
    public Orcamento atualizarOrcamento(Long id, Orcamento orcamentoAtualizado) {
        logger.info("Atualizando orçamento com ID: " + id);
        Orcamento orcamentoExistente = orcamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Orçamento não encontrado com ID: " + id + ", não foi possível atualizar."));

        // Atualiza os campos do orçamento existente com os valores do orçamento atualizado
        // É uma boa prática não alterar o ID.
        orcamentoExistente.setDataCriacao(orcamentoAtualizado.getDataCriacao()); // Ou manter a data original se preferir
        orcamentoExistente.setValorTotal(orcamentoAtualizado.getValorTotal());
        orcamentoExistente.setStatus(orcamentoAtualizado.getStatus());
        orcamentoExistente.setListaMateriais(orcamentoAtualizado.getListaMateriais());
        // Se houver um cliente associado futuramente, atualize também:
        // orcamentoExistente.setCliente(orcamentoAtualizado.getCliente());


        // Aqui você pode recalcular o valorTotal se a lista de materiais foi alterada.
        // Ex: orcamentoExistente.setValorTotal(calcularValorTotal(orcamentoExistente.getListaMateriais()));

        return orcamentoRepository.save(orcamentoExistente);
    }

    // Método para deletar um orçamento
    public void deletarOrcamento(Long id) {
        logger.info("Deletando orçamento com ID: " + id);
        if (!orcamentoRepository.existsById(id)) {
            throw new NotFoundException("Orçamento não encontrado com ID: " + id + ", não foi possível deletar.");
        }
        // Atenção: Verificar se há pedidos vinculados a este orçamento antes de deletar,
        // dependendo da regra de negócio (pode ser necessário impedir a exclusão ou excluir em cascata -
        // o relacionamento atual OneToOne em Pedido para Orcamento não tem cascade delete por padrão).
        // A constraint `uc_pedido_orcamento` em `V1__Init.sql` pode causar erro se um pedido usa este orçamento.
        // Por agora, vamos deletar diretamente.
        orcamentoRepository.deleteById(id);
        logger.info("Orçamento com ID: " + id + " deletado com sucesso.");
    }

    // Exemplo de método auxiliar (privado) se necessário
    // private double calcularValorTotal(List<Item> materiais) {
    //    if (materiais == null) return 0.0;
    //    return materiais.stream().mapToDouble(item -> item.getPrecoUnitario() * ALGUMA_QUANTIDADE_DO_ITEM_NO_ORCAMENTO).sum();
    //    // Nota: A entidade Item não tem quantidade no contexto do orçamento diretamente.
    //    // A tabela 'orcamento_material' é uma tabela de junção.
    //    // Para calcular o valor, você precisaria de mais informações sobre a quantidade de cada item no orçamento.
    //    // Isso geralmente é feito com uma entidade intermediária @ManyToOne @OneToMany com atributos extras na relação.
    //    // Por simplicidade, vamos assumir que o valorTotal é setado diretamente no objeto por enquanto.
    // }
}
