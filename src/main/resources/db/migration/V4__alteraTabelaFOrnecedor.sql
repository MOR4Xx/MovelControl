ALTER TABLE fornecedor DROP COLUMN endereco;

ALTER TABLE fornecedor ADD COLUMN endereco_id BIGINT;

ALTER TABLE fornecedor
ADD CONSTRAINT fk_fornecedor_endereco
FOREIGN KEY (endereco_id) REFERENCES endereco(id);
