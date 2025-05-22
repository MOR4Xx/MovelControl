package com.web2.movelcontrol.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.MediaType;

import com.web2.movelcontrol.Model.PedidoFornecedor;
import com.web2.movelcontrol.Service.PedidoFornecedorService;

@RestController
@RequestMapping("/pFornecedor")
public class PedidoFornecedorController {

    @Autowired
    PedidoFornecedorService service;

    @PostMapping(value = "/criar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public PedidoFornecedor creatPedidoFornecedor(@RequestBody PedidoFornecedor pedidoFornecedor) {
        return service.create(pedidoFornecedor);
    }
}
