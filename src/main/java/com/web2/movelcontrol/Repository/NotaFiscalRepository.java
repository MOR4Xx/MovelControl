package com.web2.movelcontrol.Repository;

import com.web2.movelcontrol.Model.NotaFiscal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NotaFiscalRepository extends JpaRepository<NotaFiscal, Long> {
	
	boolean existsByPedido_Id(Long pedidoId); //utilizado para saber se há pedido linkado a nota fiscal impedindo de deletar.
}
