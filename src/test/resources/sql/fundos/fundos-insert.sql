insert IGNORE into usuarios (id, login, senha, role) values (100, 'ana', '$2a$10$OoiEq8GQEsNsgqNP7l/axuTbwiaxDm4ZfPRLNGgHLoFZJutSckNPS', 'ADMIN');
insert IGNORE into usuarios (id, login, senha, role) values (101, 'bia', '$2a$10$OoiEq8GQEsNsgqNP7l/axuTbwiaxDm4ZfPRLNGgHLoFZJutSckNPS', 'USER');
insert IGNORE into usuarios (id, login, senha, role) values (102, 'bob', '$2a$10$OoiEq8GQEsNsgqNP7l/axuTbwiaxDm4ZfPRLNGgHLoFZJutSckNPS', 'USER');

insert into fundos (id, fundos_arecadado, valor_recebido) values (100, 'salario', 2500.00);
insert into fundos (id, fundos_arecadado, valor_recebido) values (101, 'deposito', 100.00);
insert into fundos (id, fundos_arecadado, valor_recebido) values (102, 'venda produto', 50.00);
