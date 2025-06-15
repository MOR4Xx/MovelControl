package com.web2.movelcontrol.Repository;

import com.web2.movelcontrol.Model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	boolean existsByOrcamentoId(Long orcamentoId);
	Optional<Pedido> findByOrcamentoId(Long orcamentoId);
}