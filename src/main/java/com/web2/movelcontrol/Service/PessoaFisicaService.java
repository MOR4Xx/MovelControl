package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.PessoaFisica;
import com.web2.movelcontrol.Repository.PessoaFisicaRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;

@Service
public class PessoaFisicaService {

    @Autowired
    PessoaFisicaRepository repository;

    Logger logger = Logger.getLogger(PessoaFisicaService.class.getName());

    public PessoaFisica create(PessoaFisica pf){
        logger.info("Pessoa Fisica criada com sucesso");
        return repository.save(pf);
    }

    public PessoaFisica findById(Long id) {
        logger.info("Pessoa Fisica buscada por ID: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pessoa Fisica não encontrada com ID: " + id));
    }

    public PessoaFisica update(Long id, PessoaFisica novaPessoa){
        PessoaFisica antigo = repository.findById(id)
                .orElseThrow(()-> new NotFoundException("Pessoa Fisica "+id));

        antigo.setNome(novaPessoa.getNome());
        antigo.setCpf(novaPessoa.getCpf());
        antigo.setEmail(novaPessoa.getEmail());
        antigo.setTelefone(novaPessoa.getTelefone());
        antigo.setEndereco(novaPessoa.getEndereco());

        return repository.save(antigo);
    }

    public void delete(Long id){
        repository.deleteById(id);
        logger.info("Pessoa Fisica Apagada com sucesso");
    }

}
