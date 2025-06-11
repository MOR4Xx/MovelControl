CREATE TABLE estoque
(
    id                 INT AUTO_INCREMENT NOT NULL,
    nome               VARCHAR(100) NOT NULL,
    descricao          VARCHAR(150) NOT NULL,
    unidade_medida     VARCHAR(10)  NOT NULL,
    preco_unitario DOUBLE NOT NULL,
    quantidade_estoque INT          NOT NULL,
    CONSTRAINT pk_estoque PRIMARY KEY (id)
);

CREATE TABLE fornecedor
(
    id       INT AUTO_INCREMENT NOT NULL,
    nome     VARCHAR(100) NOT NULL,
    cnpj     VARCHAR(14)  NOT NULL,
    telefone VARCHAR(11) NULL,
    email    VARCHAR(100) NULL,
    endereco VARCHAR(100) NULL,
    CONSTRAINT pk_fornecedor PRIMARY KEY (id)
);

CREATE TABLE nota_fiscal
(
    id           INT AUTO_INCREMENT NOT NULL,
    codigo       BIGINT NOT NULL,
    data_emissao date   NOT NULL,
    valor DOUBLE NULL,
    pedido_id    INT NULL,
    CONSTRAINT pk_nota_fiscal PRIMARY KEY (id)
);

CREATE TABLE orcamento
(
    id           INT AUTO_INCREMENT NOT NULL,
    data_criacao date NOT NULL,
    valor_total DOUBLE NULL,
    status       VARCHAR(50) NULL,
    CONSTRAINT pk_orcamento PRIMARY KEY (id)
);

CREATE TABLE orcamento_material
(
    item_id      INT NOT NULL,
    orcamento_id INT NOT NULL
);

CREATE TABLE pedido
(
    id           INT AUTO_INCREMENT NOT NULL,
    data_pedido  date        NOT NULL,
    status       VARCHAR(10) NOT NULL,
    descricao    VARCHAR(100) NULL,
    orcamento_id INT NULL,
    CONSTRAINT pk_pedido PRIMARY KEY (id)
);

CREATE TABLE pedido_fornecedor
(
    id          INT AUTO_INCREMENT NOT NULL,
    data_pedido datetime NULL,
    status      VARCHAR(255) NULL,
    CONSTRAINT pk_pedido_fornecedor PRIMARY KEY (id)
);

CREATE TABLE pessoa
(
    id            INT AUTO_INCREMENT NOT NULL,
    nome          VARCHAR(50) NOT NULL,
    telefone      VARCHAR(50) NOT NULL,
    email         VARCHAR(50) NOT NULL,
    identificador VARCHAR(50) NOT NULL,
    tipo          VARCHAR(10) NOT NULL,
    CONSTRAINT pk_pessoa PRIMARY KEY (id)
);

CREATE TABLE usuario
(
    id           INT AUTO_INCREMENT NOT NULL,
    nome         VARCHAR(100) NOT NULL,
    email        VARCHAR(100) NOT NULL,
    senha        VARCHAR(50)  NOT NULL,
    nivel_acesso VARCHAR(10)  NOT NULL,
    CONSTRAINT pk_usuario PRIMARY KEY (id)
);

ALTER TABLE nota_fiscal
    ADD CONSTRAINT uc_nota_fiscal_pedido UNIQUE (pedido_id);

ALTER TABLE pedido
    ADD CONSTRAINT uc_pedido_orcamento UNIQUE (orcamento_id);

ALTER TABLE pessoa
    ADD CONSTRAINT uc_pessoa_identificador UNIQUE (identificador);



ALTER TABLE nota_fiscal
    ADD CONSTRAINT FK_NOTA_FISCAL_ON_PEDIDO FOREIGN KEY (pedido_id) REFERENCES pedido (id);

ALTER TABLE pedido
    ADD CONSTRAINT FK_PEDIDO_ON_ORCAMENTO FOREIGN KEY (orcamento_id) REFERENCES orcamento (id);

ALTER TABLE orcamento_material
    ADD CONSTRAINT fk_orcmat_on_item FOREIGN KEY (item_id) REFERENCES estoque (id);

ALTER TABLE orcamento_material
    ADD CONSTRAINT fk_orcmat_on_orcamento FOREIGN KEY (orcamento_id) REFERENCES orcamento (id);