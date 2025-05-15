package com.web2.movelcontrol.Services;

import com.web2.movelcontrol.Models.PessoaJuridica;
import com.web2.movelcontrol.Repository.PessoaJuridicaRepository;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;

@Service
public class PessoaJuridicaService {

    PessoaJuridicaRepository repository;

    private Logger logger = Logger.getLogger(PessoaJuridicaService.class.getName());

    public PessoaJuridica create(PessoaJuridica pj){
        logger.info("Pessoa Juridica criada com sucesso");
        return repository.save(pj);
    }

    public void delete(Integer id){
        repository.deleteById(id);
        logger.info("Pessoa Juridica Apagada com sucesso");
    }
}
