/*
 * Autor: Jorge Afonso
 * Responsavel: Jorge Afonso
 */

package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.Endereco;
import com.web2.movelcontrol.Model.PessoaFisica;
import com.web2.movelcontrol.Repository.PessoaFisicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PessoaFisicaService {

    @Autowired
    PessoaFisicaRepository repository;

    Logger logger = Logger.getLogger(PessoaFisicaService.class.getName());

    public PessoaFisica create(PessoaFisica pf) {
        logger.info("Pessoa Fisica criada com sucesso");
        return repository.save(pf);
    }

    public PessoaFisica findById(Long id) {
        logger.info("Pessoa Fisica buscada por ID: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pessoa Fisica não encontrada com ID: " + id));
    }

    public List<PessoaFisica> findByNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }

        logger.info("Pessoa Fisica buscada por Nome: " + nome);
        List<PessoaFisica> pessoasFisicas = repository.findByNome(nome);

        if (pessoasFisicas.isEmpty()) {
            logger.warning("Nenhuma Pessoa Fisica encontrada com o nome: " + nome);
            throw new NotFoundException("Nenhuma Pessoa Fisica encontrada com o nome: " + nome);
        }

        return pessoasFisicas;
    }

    public List<PessoaFisica> findAll() {
        logger.info("Buscando todas as pessoas fisicas");
        List<PessoaFisica> pessoasFisicas = repository.findAll();

        if (pessoasFisicas.isEmpty()) {
            logger.warning("Nenhuma Pessoa Fisica encontrada");
            throw new NotFoundException("Nenhuma Pessoa Fisica encontrada");
        }

        return pessoasFisicas;
    }

    public PessoaFisica update(Long id, PessoaFisica pessoaUpdate) {
        PessoaFisica pessoaFisica = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pessoa Fisica " + id));

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

    public void delete(Long id) {
        repository.deleteById(id);
        logger.info("Pessoa Fisica Apagada com sucesso");
    }

}
