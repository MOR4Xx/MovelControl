/*
 * Autor: Jorge Afonso
 * Responsavel: Jorge Afonso
 */

package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.NotaFiscal;
import com.web2.movelcontrol.Repository.NotaFiscalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class NotaFiscalService {

    @Autowired
    private NotaFiscalRepository repository;
    Logger logger = Logger.getLogger(NotaFiscalService.class.getName());

    public NotaFiscal create(NotaFiscal notaFiscal) {
        logger.info("Nota fiscal criada com sucesso");
        return repository.save(notaFiscal);
    }

    public NotaFiscal findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nota fiscal não encontrada com ID: " + id));
    }

    public NotaFiscal update(Long id, NotaFiscal notaFiscalUpdate) {
        NotaFiscal notaFiscal = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nota fiscal não encontrada com ID: " + id));

        if (notaFiscalUpdate.getCodigo() != null) notaFiscal.setCodigo(notaFiscalUpdate.getCodigo());
        if (notaFiscalUpdate.getData_emissao() != null) notaFiscal.setData_emissao(notaFiscalUpdate.getData_emissao());
        if (notaFiscalUpdate.getPedido() != null) notaFiscal.setPedido(notaFiscalUpdate.getPedido());
        if (notaFiscalUpdate.getValor() != null) notaFiscal.setValor(notaFiscalUpdate.getValor());

        return repository.save(notaFiscal);
    }

    public void delete(Long id) {
        repository.deleteById(id);
        logger.info("Nota fiscal apagada com sucesso");
    }
}
