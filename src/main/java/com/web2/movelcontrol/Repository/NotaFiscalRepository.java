package com.web2.movelcontrol.Repository;

import com.web2.movelcontrol.Model.NotaFiscal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaFiscalRepository extends JpaRepository<NotaFiscal, Long> {

}
