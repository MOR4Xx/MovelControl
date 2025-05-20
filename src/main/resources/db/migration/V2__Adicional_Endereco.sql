-- 1. Criar tabela endereco
CREATE TABLE endereco (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          cep VARCHAR(10),
                          rua VARCHAR(100),
                          numero VARCHAR(10),
                          complemento VARCHAR(100),
                          bairro VARCHAR(100)
);

-- 2. Remove a coluna de endereco
ALTER TABLE pessoa
    DROP COLUMN endereco;

-- 3. Adicionar coluna endereco_id à tabela pessoa
ALTER TABLE pessoa
    ADD COLUMN endereco_id BIGINT;

-- 4. Criar a foreign key
ALTER TABLE pessoa
    ADD CONSTRAINT fk_pessoa_endereco
        FOREIGN KEY (endereco_id) REFERENCES endereco(id);
