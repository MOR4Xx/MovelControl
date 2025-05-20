package com.web2.movelcontrol.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web2.movelcontrol.Model.Fornecedor;
@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long>{


}

