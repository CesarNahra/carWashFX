create table categoria
(
    id          int auto_increment primary key,
    descricao   varchar(50) not null
)engine = InnoDB;

create table cliente
(
    id              int auto_increment primary key,
    nome            varchar(50)  not null,
    cpf             varchar(50)  not null,
    telefone        varchar(50)  not null,
    email           varchar(100) null,
    endereco        varchar(100) null,
    data_nascimento date         null
)engine = InnoDB;

create table cor
(
    id      int auto_increment primary key,
    nome    varchar(30) not null
)engine = InnoDB;

create table servico
(
    id          int auto_increment primary key,
    descricao   varchar(255)  null,
    valor       double(10, 2) null
)engine= InnoDB;

