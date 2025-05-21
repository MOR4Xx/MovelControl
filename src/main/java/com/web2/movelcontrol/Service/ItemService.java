package com.web2.movelcontrol.Service;


import com.web2.movelcontrol.Model.Item;
import com.web2.movelcontrol.Repository.ItemRepository;
import com.web2.movelcontrol.Exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ItemService {

    private static final Logger log = LoggerFactory.getLogger(ItemService.class);
    private final ItemRepository repository;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    /* -------- CRUD ---------- */

    public List<Item> listAll() {
        return repository.findAll();       // depois trocamos para página
    }

    public Item findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item id " + id + " não encontrado"));
    }

    public Item create(Item item) {
        validate(item);
        Item saved = repository.save(item);
        log.info("Item criado: {}", saved.getId());
        return saved;
    }

    public Item update(Long id, Item item) {
        Item existing = findById(id);      // dispara exceção se não existe
        existing.setNome(item.getNome());
        existing.setDescricao(item.getDescricao());
        existing.setUnidadeMedida(item.getUnidadeMedida());
        existing.setPrecoUnitario(item.getPrecoUnitario());
        existing.setQuantidadeEstoque(item.getQuantidadeEstoque());
        validate(existing);
        return repository.save(existing);
    }

    public void delete(Long id) {
        Item existing = findById(id);
        repository.delete(existing);
        log.info("Item deletado: {}", id);
    }

    /* -------- Regras de negócio simples ---------- */

    private void validate(Item item) {
        if (item.getPrecoUnitario() <= 0)
            throw new IllegalArgumentException("Preço unitário deve ser > 0");

        if (item.getQuantidadeEstoque() < 0)
            throw new IllegalArgumentException("Quantidade em estoque não pode ser negativa");
    }


}
