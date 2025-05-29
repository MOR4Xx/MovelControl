-- Remove a tabela de junção antiga entre orcamento e item
DROP TABLE IF EXISTS orcamento_material;

-- Cria a nova tabela para os itens do orçamento, incluindo a quantidade
CREATE TABLE orcamento_item (
                                orcamento_id INT NOT NULL,
                                item_id INT NOT NULL,
                                quantity INT NOT NULL,
                                PRIMARY KEY (orcamento_id, item_id),
                                CONSTRAINT fk_orcamentoitem_orcamento FOREIGN KEY (orcamento_id) REFERENCES orcamento(id) ON DELETE CASCADE,
                                CONSTRAINT fk_orcamentoitem_item FOREIGN KEY (item_id) REFERENCES estoque(id) -- 'estoque' é a tabela para a entidade 'Item'
);

-- Adiciona a coluna para referenciar o cliente na tabela de orçamento
ALTER TABLE orcamento
    ADD COLUMN cliente_id INT NULL;

-- Adiciona a constraint de chave estrangeira para cliente_id, referenciando a tabela pessoa
ALTER TABLE orcamento
    ADD CONSTRAINT fk_orcamento_cliente
        FOREIGN KEY (cliente_id) REFERENCES pessoa(id);