create table fornecedor
(
    id       int auto_increment
        primary key,
    cnpj     varchar(14)  not null,
    email    varchar(100) null,
    endereco varchar(100) null,
    nome     varchar(100) not null,
    telefone varchar(11)  null
);

create table pedido_fornecedor
(
    id          int auto_increment
        primary key,
    data_pedido datetime(6)  null,
    status      varchar(255) null
);

create table estoque
(
    id                 int auto_increment
        primary key,
    descricao          varchar(150) not null,
    nome               varchar(100) not null,
    preco_unitario     double       not null,
    quantidade_estoque int          not null,
    unidade_medida     varchar(10)  not null,
    pedido_id          int          null,
    constraint FKcq85cg2cvx1q1i3sbw86lt7dp
        foreign key (pedido_id) references pedido_fornecedor (id)
);

create table pessoa
(
    id       int auto_increment
        primary key,
    email    varchar(50)  not null,
    endereco varchar(100) not null,
    nome     varchar(50)  not null,
    telefone varchar(15)  not null,
    constraint UKama8eqblyg3f2303y731p8707
        unique (telefone),
    constraint UKe72oxh322rl9cwc4j8cclbve7
        unique (endereco),
    constraint UKmc87q8fpvldpdyfo9o5633o5l
        unique (email),
    constraint UKppfc4sjk52dk2jex4f880oifm
        unique (nome)
);

create table orcamento
(
    id           int auto_increment
        primary key,
    data_criacao date        not null,
    status       varchar(50) null,
    valor_total  double      null,
    cliente_id   int         null,
    constraint FKcvdven31k250pjm394g1fix2p
        foreign key (cliente_id) references pessoa (id)
);

create table orcamento_material
(
    orcamento_id int not null,
    item_id      int not null,
    constraint FKaeo5u7jlcoseskcfl5w7fmm71
        foreign key (item_id) references estoque (id),
    constraint FKjyg0ufyn520fi34qqf8euwbym
        foreign key (orcamento_id) references orcamento (id)
);

create table pedido
(
    id           int auto_increment
        primary key,
    data_pedido  date         not null,
    descricao    varchar(100) null,
    status       varchar(10)  not null,
    orcamento_id int          null,
    constraint UKe8nx9infhbyikmmo6h4og0ib8
        unique (orcamento_id),
    constraint FK204ps6ydu1vu7pk0heij3hkdc
        foreign key (orcamento_id) references orcamento (id)
);

create table nota_fiscal
(
    id           int auto_increment
        primary key,
    codigo       bigint not null,
    data_emissao date   not null,
    valor        double null,
    pedido_id    int    null,
    constraint UKlkvv5etp5bgekolckuorv7mqy
        unique (pedido_id),
    constraint FKa7ehd6lmvylbir6lg48ofmryk
        foreign key (pedido_id) references pedido (id)
);

create table pessoa_fisica
(
    id       int auto_increment
        primary key,
    email    varchar(50)  not null,
    endereco varchar(100) not null,
    nome     varchar(50)  not null,
    telefone varchar(15)  not null,
    cpf      varchar(11)  not null,
    constraint UKd70aayxv20yf3y8kofcx7fhbg
        unique (email),
    constraint UKhqs28057u6d1pwxfv1ehus404
        unique (telefone),
    constraint UKklnofpd5fa5c4s6gp9delqljy
        unique (nome),
    constraint UKp2iu8kes1ck0va1fj6wmhow3q
        unique (endereco),
    constraint UKp3d8co8s4y5h7y18fpqco1wv6
        unique (cpf),
    constraint FKsrkbwlwurui650owdeqn7mbx6
        foreign key (id) references pessoa (id)
);

create table pessoa_juridica
(
    id       int auto_increment
        primary key,
    email    varchar(50)  not null,
    endereco varchar(100) not null,
    nome     varchar(50)  not null,
    telefone varchar(15)  not null,
    cnpj     varchar(14)  not null,
    constraint UK3h78rtw3ei11cb43k77af5nhl
        unique (cnpj),
    constraint UK6f8dnvy9aakthuofgyrj66lyf
        unique (email),
    constraint UKhyj9t6b8bpc61p7nswmtiwjdb
        unique (telefone),
    constraint UKko1jhfo6031sqfaua7u5axyas
        unique (endereco),
    constraint UKmykesvurfgl1jgb7opldpmam9
        unique (nome),
    constraint FKor0e1i8f3vc9f721q0wcx1nbp
        foreign key (id) references pessoa (id)
);

create table usuario
(
    id           int auto_increment
        primary key,
    email        varchar(100) not null,
    nivel_acesso varchar(10)  not null,
    nome         varchar(100) not null,
    senha        varchar(50)  not null
);

