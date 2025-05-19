package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.PessoaJuridica;
import com.web2.movelcontrol.Repository.PessoaJuridicaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class PessoaJuridicaService {
    @Autowired
    PessoaJuridicaRepository repository;

    private Logger logger = Logger.getLogger(PessoaJuridicaService.class.getName());

    public PessoaJuridica create(PessoaJuridica pj){
        logger.info("Pessoa Juridica criada com sucesso");
        return repository.save(pj);
    }

    public PessoaJuridica findById(Long id) {
        logger.info("Pessoa Jurídica buscada por ID: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pessoa Jurídica não encontrada com ID: " + id));
    }

    public PessoaJuridica update(Long id, PessoaJuridica novaPessoa){
        PessoaJuridica antigo = repository.findById(id)
                .orElseThrow(()-> new NotFoundException("Pessoa Juridica "+id));

        antigo.setNome(novaPessoa.getNome());
        antigo.setCnpj(novaPessoa.getCnpj());
        antigo.setEmail(novaPessoa.getEmail());
        antigo.setTelefone(novaPessoa.getTelefone());
        antigo.setEndereco(novaPessoa.getEndereco());

        return repository.save(antigo);
    }

    public void delete(Long id){
        repository.deleteById(id);
        logger.info("Pessoa Juridica Apagada com sucesso");
    }
}
