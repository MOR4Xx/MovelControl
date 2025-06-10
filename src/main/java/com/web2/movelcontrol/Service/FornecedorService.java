package com.web2.movelcontrol.Service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web2.movelcontrol.DTO.EnderecoResponseDTO;
import com.web2.movelcontrol.DTO.FornecedorRequestDTO;
import com.web2.movelcontrol.DTO.FornecedorResponseDTO;
import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.Endereco;
import com.web2.movelcontrol.Model.Fornecedor;
import com.web2.movelcontrol.Repository.FornecedorRepository;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository repository;

    Logger logger = Logger.getLogger(Fornecedor.class.getName());

    public FornecedorResponseDTO create(FornecedorRequestDTO dto) {
        Fornecedor fornecedor = fromDTO(dto);
        return toDTO(repository.save(fornecedor));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Fornecedor não encontrado com ID: " + id);
        }
        repository.deleteById(id);
        logger.info("Fornecedor deletado com sucesso");
    }

    public FornecedorResponseDTO update(Long id, FornecedorRequestDTO dto) {
        Fornecedor fornecedor = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fornecedor não encontrado com ID: " + id));

        fornecedor.setNome(dto.getNome());
        fornecedor.setCnpj(dto.getCnpj());
        fornecedor.setTelefone(dto.getTelefone());
        fornecedor.setEmail(dto.getEmail());

        if (dto.getEndereco() != null) {
            Endereco endereco = fornecedor.getEndereco();
            if (endereco == null) {
                endereco = new Endereco();
            }

            Endereco endereconovo = dto.getEndereco();
            endereco.setCep(endereconovo.getCep());
            endereco.setRua(endereconovo.getRua());
            endereco.setNumero(endereconovo.getNumero());
            endereco.setComplemento(endereconovo.getComplemento());
            endereco.setBairro(endereconovo.getBairro());

            fornecedor.setEndereco(endereco);
        }

        return toDTO(repository.save(fornecedor));
    }

    public FornecedorResponseDTO findById(Long id) {
        return toDTO(repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fornecedor não encontrado com ID: " + id)));
    }

    public List<FornecedorResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    // Conversões

    private Fornecedor fromDTO(FornecedorRequestDTO dto) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(dto.getNome());
        fornecedor.setCnpj(dto.getCnpj());
        fornecedor.setTelefone(dto.getTelefone());
        fornecedor.setEmail(dto.getEmail());

        if (dto.getEndereco() != null) {
            Endereco enderecoDTO = dto.getEndereco();
            Endereco endereco = new Endereco();
            endereco.setCep(enderecoDTO.getCep());
            endereco.setRua(enderecoDTO.getRua());
            endereco.setNumero(enderecoDTO.getNumero());
            endereco.setComplemento(enderecoDTO.getComplemento());
            endereco.setBairro(enderecoDTO.getBairro());
            fornecedor.setEndereco(endereco);
        }

        return fornecedor;
    }

    private FornecedorResponseDTO toDTO(Fornecedor fornecedor) {
        FornecedorResponseDTO dto = new FornecedorResponseDTO();
        dto.setId(fornecedor.getId());
        dto.setNome(fornecedor.getNome());
        dto.setCnpj(fornecedor.getCnpj());
        dto.setTelefone(fornecedor.getTelefone());
        dto.setEmail(fornecedor.getEmail());

        if (fornecedor.getEndereco() != null) {
            Endereco endereco = fornecedor.getEndereco();
            EnderecoResponseDTO enderecoDTO = new EnderecoResponseDTO();
            enderecoDTO.setCep(endereco.getCep());
            enderecoDTO.setRua(endereco.getRua());
            enderecoDTO.setNumero(endereco.getNumero());
            enderecoDTO.setComplemento(endereco.getComplemento());
            enderecoDTO.setBairro(endereco.getBairro());
            dto.setEndereco(enderecoDTO);
        }

        return dto;
    }
}
