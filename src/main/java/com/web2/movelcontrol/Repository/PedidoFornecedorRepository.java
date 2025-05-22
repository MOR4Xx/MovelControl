package com.web2.movelcontrol.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web2.movelcontrol.Model.PedidoFornecedor;
@Repository
public interface PedidoFornecedorRepository extends JpaRepository<PedidoFornecedor, Long> {
    // Aqui você pode adicionar métodos personalizados, se necessário
    // Exemplo: List<PedidoFornecedor> findByStatus(String status);

}
