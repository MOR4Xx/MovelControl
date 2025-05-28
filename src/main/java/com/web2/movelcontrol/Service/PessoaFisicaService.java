package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.Endereco;
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

    public PessoaFisica update(Long id, PessoaFisica pessoaUpdate){
        PessoaFisica pessoaFisica = repository.findById(id)
                .orElseThrow(()-> new NotFoundException("Pessoa Fisica "+id));

        if (pessoaUpdate.getNome() != null) pessoaFisica.setNome(pessoaUpdate.getNome());
        if (pessoaUpdate.getCpf() != null) pessoaFisica.setCpf(pessoaUpdate.getCpf());
        if (pessoaUpdate.getEmail() != null) pessoaFisica.setEmail(pessoaUpdate.getEmail());
        if (pessoaUpdate.getTelefone() != null) pessoaFisica.setTelefone(pessoaUpdate.getTelefone());
        if (pessoaUpdate.getEndereco() != null) {
            Endereco endereco = new Endereco();

            if (endereco.getCep() != null) endereco.setCep(pessoaUpdate.getEndereco().getCep());
            if (endereco.getRua() != null) endereco.setRua(pessoaUpdate.getEndereco().getRua());
            if (endereco.getBairro() != null) endereco.setBairro(pessoaUpdate.getEndereco().getBairro());
            if (endereco.getNumero() != null) endereco.setNumero(pessoaUpdate.getEndereco().getNumero());
            if (endereco.getComplemento() != null) endereco.setComplemento(pessoaUpdate.getEndereco().getComplemento());

            pessoaFisica.setEndereco(endereco);
        }

        return repository.save(pessoaFisica);
    }

    public void delete(Long id){
        repository.deleteById(id);
        logger.info("Pessoa Fisica Apagada com sucesso");
    }

}
