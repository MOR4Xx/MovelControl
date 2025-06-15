/*
 * Autor: Jorge Afonso
 * Responsavel: Jorge Afonso
 */

package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.Controller.PessoaFisicaController;
import com.web2.movelcontrol.DTO.DataMapper;
import com.web2.movelcontrol.DTO.PessoaFisicaRequestDTO;
import com.web2.movelcontrol.DTO.PessoaFisicaResponseDTO;
import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.Endereco;
import com.web2.movelcontrol.Model.PessoaFisica;
import com.web2.movelcontrol.Repository.PessoaFisicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PessoaFisicaService {

    @Autowired
    PessoaFisicaRepository repository;

    private Logger logger = Logger.getLogger(PessoaFisicaService.class.getName());

    public PessoaFisicaResponseDTO create(PessoaFisicaRequestDTO pf) {
        PessoaFisica pessoaFisica = DataMapper.parseObject(pf, PessoaFisica.class);

        repository.save(pessoaFisica);

        logger.info("Pessoa Fisica criada com sucesso");
        return DataMapper.parseObject(pessoaFisica, PessoaFisicaResponseDTO.class);
    }

    public EntityModel<PessoaFisicaResponseDTO> findById(Long id) {
        PessoaFisicaResponseDTO pfDTO = DataMapper.parseObject(repository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Pessoa Fisica " + id)),
                PessoaFisicaResponseDTO.class);

        EntityModel<PessoaFisicaResponseDTO> response = EntityModel.of(pfDTO,
                linkTo(methodOn(PessoaFisicaController.class).findByNome(pfDTO.getNome())).withRel("buscarPorNome"),
                linkTo(methodOn(PessoaFisicaController.class).findAll()).withRel("listarPessoasFisicas"),
                linkTo(methodOn(PessoaFisicaController.class).atualizarPessoaFisica(id, null)).withRel("update"),
                linkTo(methodOn(PessoaFisicaController.class).deletePessoaFisica(id)).withRel("delete")
                );

        logger.info("Pessoa Fisica buscada por ID: " + id);
        return response;
    }

    public List<EntityModel<PessoaFisicaResponseDTO>> findByNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }

        logger.info("Pessoa Fisica buscada por Nome: " + nome);
        List<PessoaFisica> pessoasFisicas = repository.findByNome(nome);

        List<EntityModel<PessoaFisicaResponseDTO>> pessoasFisicasDTO = pessoasFisicas.stream().map(pf -> {
            PessoaFisicaResponseDTO pfDTO = DataMapper.parseObject(pf, PessoaFisicaResponseDTO.class);
            return EntityModel.of(pfDTO,
                    linkTo(methodOn(PessoaFisicaController.class).findById(pf.getId())).withRel("busca por Id"),
                    linkTo(methodOn(PessoaFisicaController.class).findByNome(pfDTO.getNome())).withRel("buscarPorNome"),
                    linkTo(methodOn(PessoaFisicaController.class).findAll()).withRel("listarPessoasFisicas"),
                    linkTo(methodOn(PessoaFisicaController.class).atualizarPessoaFisica(pf.getId(), null)).withRel("update"),
                    linkTo(methodOn(PessoaFisicaController.class).deletePessoaFisica(pf.getId())).withRel("delete")
            );

        }).toList();

        if (pessoasFisicas.isEmpty()) {
            logger.warning("Nenhuma Pessoa Fisica encontrada com o nome: " + nome);
            throw new NotFoundException("Nenhuma Pessoa Fisica encontrada com o nome: " + nome);
        }

        return pessoasFisicasDTO;
    }

    public List<EntityModel<PessoaFisicaResponseDTO>> findAll() {
        logger.info("Buscando todas as pessoas fisicas");
        List<PessoaFisica> pessoasFisicas = repository.findAll();

        List<EntityModel<PessoaFisicaResponseDTO>> pessoasFisicasDTO = pessoasFisicas.stream().map(pf -> {
            PessoaFisicaResponseDTO pfDTO = DataMapper.parseObject(pf, PessoaFisicaResponseDTO.class);
            return EntityModel.of(pfDTO,
                    linkTo(methodOn(PessoaFisicaController.class).findById(pf.getId())).withRel("busca por Id"),
                    linkTo(methodOn(PessoaFisicaController.class).findByNome(pfDTO.getNome())).withRel("busca por Nome")
                    );
        }).toList();

        if (pessoasFisicas.isEmpty()) {
            logger.warning("Nenhuma Pessoa Fisica encontrada");
            throw new NotFoundException("Nenhuma Pessoa Fisica encontrada");
        }

        return pessoasFisicasDTO;
    }

    public PessoaFisicaResponseDTO update(Long id, PessoaFisicaRequestDTO pessoaUpdate) {
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

        repository.save(pessoaFisica);

        return DataMapper.parseObject(pessoaFisica, PessoaFisicaResponseDTO.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
        logger.info("Pessoa Fisica Apagada com sucesso");
    }

}
