package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.Endereco;
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

    public PessoaJuridica create(PessoaJuridica pj) {
        logger.info("Pessoa Juridica criada com sucesso");
        return repository.save(pj);
    }

    public PessoaJuridica findById(Long id) {
        logger.info("Pessoa Jurídica buscada por ID: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pessoa Jurídica não encontrada com ID: " + id));
    }

    public PessoaJuridica update(Long id, PessoaJuridica pessoaUpdate) {
        PessoaJuridica pessoaJuridica = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pessoa Juridica " + id));

        if (pessoaUpdate.getNome() != null) pessoaJuridica.setNome(pessoaUpdate.getNome());
        if (pessoaUpdate.getCnpj() != null) pessoaJuridica.setCnpj(pessoaUpdate.getCnpj());
        if (pessoaUpdate.getEmail() != null) pessoaJuridica.setEmail(pessoaUpdate.getEmail());
        if (pessoaUpdate.getTelefone() != null) pessoaJuridica.setTelefone(pessoaUpdate.getTelefone());
        if (pessoaUpdate.getEndereco() != null) {

            Endereco endereco = new Endereco();

            if (endereco.getCep() != null) endereco.setCep(pessoaUpdate.getEndereco().getCep());
            if (endereco.getRua() != null) endereco.setRua(pessoaUpdate.getEndereco().getRua());
            if (endereco.getBairro() != null) endereco.setBairro(pessoaUpdate.getEndereco().getBairro());
            if (endereco.getNumero() != null) endereco.setNumero(pessoaUpdate.getEndereco().getNumero());
            if (endereco.getComplemento() != null) endereco.setComplemento(pessoaUpdate.getEndereco().getComplemento());

            pessoaJuridica.setEndereco(endereco);
        }

        return repository.save(pessoaJuridica);
    }

    public void delete(Long id) {
        repository.deleteById(id);
        logger.info("Pessoa Juridica Apagada com sucesso");
    }
}
