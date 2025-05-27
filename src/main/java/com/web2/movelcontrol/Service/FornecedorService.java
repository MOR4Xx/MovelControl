package com.web2.movelcontrol.Service;

import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.Fornecedor;
import com.web2.movelcontrol.Repository.FornecedorRepository;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository repository;

    Logger logger = Logger.getLogger(Fornecedor.class.getName());

    public Fornecedor create(Fornecedor fornecedor){
        logger.info("Fornecedor cadastrado com sucesso");
        return repository.save(fornecedor);
    }

    public void delete(Long id) {
        repository.deleteById(id);
        logger.info("Fornecedor deletado com sucesso");
    }

    public Fornecedor update(Long id, Fornecedor novoFornecedor) {
  Fornecedor antigo = repository.findById(id)
                .orElseThrow(()-> new NotFoundException("fornecedor "+id));

        antigo.setNome(novoFornecedor.getNome());
        antigo.setCnpj(novoFornecedor.getCnpj());
        antigo.setEmail(novoFornecedor.getEmail());
        antigo.setTelefone(novoFornecedor.getTelefone());
        antigo.setEndereco(novoFornecedor.getEndereco());

        return repository.save(antigo);
    }

    public Fornecedor findById(Long id) {
        logger.info("Fornecedor buscado por ID: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pessoa Fisica não encontrada com ID: " + id));
    }

}
