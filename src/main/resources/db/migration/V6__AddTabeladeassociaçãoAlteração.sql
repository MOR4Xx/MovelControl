-- Corrige o tipo para INT se for o mesmo usado em fornecedor
ALTER TABLE pedido_fornecedor
ADD COLUMN fornecedor_id INT,
ADD CONSTRAINT fk_pedido_fornecedor_fornecedor
    FOREIGN KEY (fornecedor_id) REFERENCES fornecedor(id);


-- Também corrija aqui se item.id for INT
CREATE TABLE pedido_item (
    pedido_id INT NOT NULL,
    item_id   INT NOT NULL,
    PRIMARY KEY (pedido_id, item_id),
    CONSTRAINT fk_pedido_item_pedido FOREIGN KEY (pedido_id) REFERENCES pedido_fornecedor(id) ON DELETE CASCADE,
    CONSTRAINT fk_pedido_item_item FOREIGN KEY (item_id) REFERENCES estoque(id)
);
