package com.web2.movelcontrol.Repository;

import com.web2.movelcontrol.Model.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {

    List<PessoaJuridica> findByNome(String nome);
    List<PessoaJuridica> findByCnpj(String cnpj);
    List<PessoaJuridica> findByEmail(String email);
    List<PessoaJuridica> findByTipo(String tipo);
}
