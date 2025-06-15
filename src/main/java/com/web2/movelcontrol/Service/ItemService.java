package com.web2.movelcontrol.Service;

import com.web2.movelcontrol.Exceptions.NotFoundException;
import com.web2.movelcontrol.Model.Item;
import com.web2.movelcontrol.Repository.ItemRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@CacheConfig(cacheNames = "item")
public class ItemService {

    @Autowired
    private ItemRepository repository;

    Logger logger = Logger.getLogger(Item.class.getName());

    @Cacheable(key = "#id")
    public Item create(Item item) {
        logger.info("Item criado com sucesso");
        return repository.save(item);
    }

    @Cacheable(key = "#id")
    public Item findById(Long id) {
        logger.info("Item buscado por ID: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item não encontrado com ID: " + id));
    }

    @CachePut(key = "#id")
    public Item update(Long id, Item itemNovo) {
        Item itemAntigo = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item não encontrado com ID: " + id));
        itemAntigo.setId(itemNovo.getId());
        itemAntigo.setNome(itemNovo.getNome());
        itemAntigo.setDescricao(itemNovo.getDescricao());
        itemAntigo.setPrecoUnitario(itemNovo.getPrecoUnitario());

        logger.info("Item atualizado com sucesso");
        return repository.save(itemAntigo);
    }

    @CacheEvict(value = "item", key = "#id")
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
