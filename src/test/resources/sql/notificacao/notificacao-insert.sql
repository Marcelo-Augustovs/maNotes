insert IGNORE into usuarios (id, login, senha, role) values (100, 'ana', '$2a$10$OoiEq8GQEsNsgqNP7l/axuTbwiaxDm4ZfPRLNGgHLoFZJutSckNPS', 'ADMIN');
insert IGNORE into usuarios (id, login, senha, role) values (101, 'bia', '$2a$10$OoiEq8GQEsNsgqNP7l/axuTbwiaxDm4ZfPRLNGgHLoFZJutSckNPS', 'USER');
insert IGNORE into usuarios (id, login, senha, role) values (102, 'bob', '$2a$10$OoiEq8GQEsNsgqNP7l/axuTbwiaxDm4ZfPRLNGgHLoFZJutSckNPS', 'USER');

INSERT INTO notificacao (id, titulo, descricao, horario) VALUES (100, 'A', 'descricao', '2025-05-12 01:00:00');
INSERT INTO notificacao (id, titulo, descricao, horario) VALUES (101, 'b', 'descricao', '2025-05-12 01:00:00');
INSERT INTO notificacao (id, titulo, descricao, horario) VALUES (102, 'c', 'descricao', '2025-05-12 01:00:00');