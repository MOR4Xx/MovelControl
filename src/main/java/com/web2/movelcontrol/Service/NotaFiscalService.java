/*
 * Autor: Jorge Afonso
 * Responsavel: Jorge Afonso
 */

package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.Controller.NotaFiscalController;
import com.web2.movelcontrol.DTO.DataMapper;
import com.web2.movelcontrol.DTO.NotaFiscalRequestDTO;
import com.web2.movelcontrol.DTO.NotaFiscalResponseDTO;
import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.NotaFiscal;
import com.web2.movelcontrol.Repository.NotaFiscalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class NotaFiscalService {

    @Autowired
    private NotaFiscalRepository repository;
    Logger logger = Logger.getLogger(NotaFiscalService.class.getName());

    public NotaFiscalResponseDTO create(NotaFiscalRequestDTO nf) {
        NotaFiscal notaFiscal = DataMapper.parseObject(nf, NotaFiscal.class);

        repository.save(notaFiscal);

        logger.info("Nota fiscal criada com sucesso");
        return DataMapper.parseObject(nf, NotaFiscalResponseDTO.class);
    }

    public EntityModel<NotaFiscalResponseDTO> findById(Long id) {

        NotaFiscal notaFiscal = DataMapper.parseObject(
                repository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Nota fiscal não encontrada com ID: " + id)),
                NotaFiscal.class
        );

        EntityModel<NotaFiscalResponseDTO> response = EntityModel.of(DataMapper.parseObject(notaFiscal, NotaFiscalResponseDTO.class),
                linkTo(methodOn(NotaFiscalController.class).findById(notaFiscal.getId())).withSelfRel(),
                linkTo(methodOn(NotaFiscalController.class).findAll()).withRel("listarNotasFiscais"),
                linkTo(methodOn(NotaFiscalController.class).findByPedido(notaFiscal.getPedido().getId())).withRel("buscarPorPedido"),
                linkTo(methodOn(NotaFiscalController.class).findByCodigo(notaFiscal.getCodigo())).withRel("buscarPorCodigo"),
                linkTo(methodOn(NotaFiscalController.class).deletarNotaFiscal(notaFiscal.getId())).withRel("delete")
        );

        logger.info("Nota fiscal buscada por ID: " + id);
        return response;
    }

    public List<EntityModel<NotaFiscalResponseDTO>> findAll() {
        logger.info("Buscando todas as notas fiscais");
        List<NotaFiscal> notasFiscais = repository.findAll();

        List<EntityModel<NotaFiscalResponseDTO>> notasFiscaisDTO = notasFiscais.stream().map(nf -> {
            NotaFiscalResponseDTO nfDTO = DataMapper.parseObject(nf, NotaFiscalResponseDTO.class);
            return EntityModel.of(nfDTO,
                    linkTo(methodOn(NotaFiscalController.class).findById(nf.getId())).withRel("busca por Id")
            );
        }).toList();

        return notasFiscaisDTO;
    }

    public NotaFiscalResponseDTO findByCodigo(String codigo) {

        logger.info("Nota fiscal buscada por código: " + codigo);
        NotaFiscal notaFiscal = repository.findByCodigo(codigo);

        NotaFiscalResponseDTO nfDTO = DataMapper.parseObject(notaFiscal, NotaFiscalResponseDTO.class);

        return nfDTO;
    }

    public NotaFiscalResponseDTO findByPedido(Long pedido) {

        logger.info("Nota fiscal buscada por pedido: " + pedido);
        NotaFiscal notaFiscal = repository.findByPedido_Id(pedido);

        return DataMapper.parseObject(notaFiscal, NotaFiscalResponseDTO.class);
    }

    public NotaFiscal delete(Long id) {
        repository.deleteById(id);
        logger.info("Nota fiscal apagada com sucesso");
        return null;
    }
}
