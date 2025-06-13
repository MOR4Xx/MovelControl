package com.web2.movelcontrol.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web2.movelcontrol.Model.PedidoFornecedor;
@Repository
public interface PedidoFornecedorRepository extends JpaRepository<PedidoFornecedor, Long> {

    List<PedidoFornecedor> findByFornecedorId(Long fornecedorId);
    

}
