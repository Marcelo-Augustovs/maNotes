insert IGNORE into usuarios (id, login, senha, role) values (100, 'ana', '$2a$10$OoiEq8GQEsNsgqNP7l/axuTbwiaxDm4ZfPRLNGgHLoFZJutSckNPS', 'ADMIN');
insert IGNORE into usuarios (id, login, senha, role) values (101, 'bia', '$2a$10$OoiEq8GQEsNsgqNP7l/axuTbwiaxDm4ZfPRLNGgHLoFZJutSckNPS', 'USER');
insert IGNORE into usuarios (id, login, senha, role) values (102, 'bob', '$2a$10$OoiEq8GQEsNsgqNP7l/axuTbwiaxDm4ZfPRLNGgHLoFZJutSckNPS', 'USER');

insert into usuario_anotacoes (id, anotacao) values (100, 'Redação');
insert into usuario_anotacoes (id, anotacao) values (101, 'Receita');
insert into usuario_anotacoes (id, anotacao) values (102, 'Dados da empresa');