/*
 * Autor: Jorge Afonso
 * Responsavel: Jorge Afonso
 */

package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.Controller.PessoaJuridicaController;
import com.web2.movelcontrol.DTO.DataMapper;
import com.web2.movelcontrol.DTO.PessoaJuridicaRequestDTO;
import com.web2.movelcontrol.DTO.PessoaJuridicaResponseDTO;
import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.Endereco;
import com.web2.movelcontrol.Model.PessoaJuridica;
import com.web2.movelcontrol.Repository.PessoaJuridicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PessoaJuridicaService {

    @Autowired
    PessoaJuridicaRepository repository;

    private Logger logger = Logger.getLogger(PessoaJuridicaService.class.getName());

    public PessoaJuridicaResponseDTO create(PessoaJuridicaRequestDTO pj) {
        PessoaJuridica pessoaJuridica = DataMapper.parseObject(pj, PessoaJuridica.class);

        repository.save(pessoaJuridica);

        logger.info("Pessoa Juridica criada com sucesso");
        return DataMapper.parseObject(pessoaJuridica, PessoaJuridicaResponseDTO.class);
    }

    public EntityModel<PessoaJuridicaResponseDTO> findById(Long id) {
        PessoaJuridicaResponseDTO pjDTO = DataMapper.parseObject(repository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Pessoa Juridica " + id)),
                PessoaJuridicaResponseDTO.class);

        EntityModel<PessoaJuridicaResponseDTO> response = EntityModel.of(pjDTO,
                linkTo(methodOn(PessoaJuridicaController.class).findByNome(pjDTO.getNome())).withRel("buscarPorNome"),
                linkTo(methodOn(PessoaJuridicaController.class).findAll()).withRel("listarPessoasJuridicas"),
                linkTo(methodOn(PessoaJuridicaController.class).atualizarPessoaJuridica(id, null)).withRel("update"),
                linkTo(methodOn(PessoaJuridicaController.class).deletePessoaJuridica(id)).withRel("delete")
                );

        logger.info("Pessoa Jurídica buscada por ID: " + id);
        return response;
    }

    public List<EntityModel<PessoaJuridicaResponseDTO>> findByNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }

        logger.info("Buscando pessoa jurídica por nome: " + nome);
        List<PessoaJuridica> pessoasJuridicas = repository.findByNome(nome);

        List<EntityModel<PessoaJuridicaResponseDTO>> pessoasJuridicasDTO = pessoasJuridicas.stream().map(pj -> {
            PessoaJuridicaResponseDTO pjDTO = DataMapper.parseObject(pj, PessoaJuridicaResponseDTO.class);
            return EntityModel.of(pjDTO,
                    linkTo(methodOn(PessoaJuridicaController.class).findById(pj.getId())).withRel("busca por Id"),
                    linkTo(methodOn(PessoaJuridicaController.class).findByNome(pjDTO.getNome())).withRel("buscarPorNome"),
                    linkTo(methodOn(PessoaJuridicaController.class).findAll()).withRel("listarPessoasJuridicas"),
                    linkTo(methodOn(PessoaJuridicaController.class).atualizarPessoaJuridica(pj.getId(), null)).withRel("update"),
                    linkTo(methodOn(PessoaJuridicaController.class).deletePessoaJuridica(pj.getId())).withRel("delete")
            );
        }).toList();

        if (pessoasJuridicasDTO.isEmpty()) {
            logger.warning("Nenhuma Pessoa Juridica encontrada com o nome: " + nome);
            throw new NotFoundException("Nenhuma Pessoa Juridica encontrada com o nome: " + nome);
        }

        return pessoasJuridicasDTO;
    }

    public List<EntityModel<PessoaJuridicaResponseDTO>> findAll() {
        logger.info("Buscando todas as pessoas jurídicas");
        List<PessoaJuridica> pessoasJuridicas = repository.findAll();

        List<EntityModel<PessoaJuridicaResponseDTO>> pessoasJuridicasDTO = pessoasJuridicas.stream().map(pj ->{
            PessoaJuridicaResponseDTO pjDTO = DataMapper.parseObject(pj, PessoaJuridicaResponseDTO.class);
            return EntityModel.of(pjDTO,
                    linkTo(methodOn(PessoaJuridicaController.class).findById(pj.getId())).withRel("busca por Id"),
                    linkTo(methodOn(PessoaJuridicaController.class).findByNome(pjDTO.getNome())).withRel("busca por Nome")
                    );
        }).toList();

        if (pessoasJuridicasDTO.isEmpty()) {
            logger.warning("Nenhuma Pessoa Juridica encontrada");
            throw new NotFoundException("Nenhuma Pessoa Juridica encontrada");
        }

        return pessoasJuridicasDTO;
    }

    public PessoaJuridicaResponseDTO update(Long id, PessoaJuridicaRequestDTO pessoaUpdate) {
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

        repository.save(pessoaJuridica);

        return DataMapper.parseObject(pessoaJuridica, PessoaJuridicaResponseDTO.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
        logger.info("Pessoa Juridica Apagada com sucesso");
    }
}
