INSERT INTO usuario (nome, email, senha, status) VALUES ('Cliente', 'cliente@thecode.com.br', '$2a$10$QVYf7pWiZRAn4YZol9dC7u.xAKMg8bxKDqhaRpBe6y0zM0Agbkv66', 1);

INSERT INTO permissao VALUES (4, 'CLIENTE');

INSERT INTO grupo_permissao (id_grupo, id_permissao) VALUES (4, 4);

INSERT INTO usuario_grupo (id_usuario, id_grupo) VALUES (
	(SELECT id FROM usuario WHERE email = 'cliente@thecode.com.br'), 4
);