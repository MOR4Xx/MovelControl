package com.web2.movelcontrol.Repository;

import com.web2.movelcontrol.Model.PessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {
	
	List<PessoaFisica> findByNome(String nome);
	
	List<PessoaFisica> findByCpf(String cpf);
	
	List<PessoaFisica> findByEmail(String email);

}
