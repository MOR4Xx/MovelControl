package com.web2.movelcontrol.Service;

import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
