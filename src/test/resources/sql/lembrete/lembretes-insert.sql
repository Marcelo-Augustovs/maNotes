insert IGNORE into usuarios (id, login, senha, role) values (100, 'ana', '$2a$10$OoiEq8GQEsNsgqNP7l/axuTbwiaxDm4ZfPRLNGgHLoFZJutSckNPS', 'ADMIN');
insert IGNORE into usuarios (id, login, senha, role) values (101, 'bia', '$2a$10$OoiEq8GQEsNsgqNP7l/axuTbwiaxDm4ZfPRLNGgHLoFZJutSckNPS', 'USER');
insert IGNORE into usuarios (id, login, senha, role) values (102, 'bob', '$2a$10$OoiEq8GQEsNsgqNP7l/axuTbwiaxDm4ZfPRLNGgHLoFZJutSckNPS', 'USER');

insert into lembretes (id, evento, dia_marcado) values (100, 'eventoA','2025-05-12');
insert into lembretes (id, evento, dia_marcado) values (101, 'eventoB','2025-05-13');
insert into lembretes (id, evento, dia_marcado) values (102, 'eventoC','2025-05-14');
