package com.web2.movelcontrol.Controller;

import com.web2.movelcontrol.Model.Item;
import com.web2.movelcontrol.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService service;

    @PostMapping(value = "/criar"
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public Item criarItem(@RequestBody Item item){
        return service.create(item);
    }

    @GetMapping("/{id}")
    public Item findById(@PathVariable Long id){
        return service.findById(id);
    }

    @PutMapping("/atualizar/{id}")
    public Item atualizarItem(@PathVariable Long id, Item itemNovo){
        return service.update(id, itemNovo);
    }

    @DeleteMapping("/deletar/{id}")
    public void deletarItem(@PathVariable Long id){
        service.delete(id);
    }
}
