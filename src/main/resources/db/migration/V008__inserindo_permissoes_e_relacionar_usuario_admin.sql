INSERT INTO permissao VALUES (1, 'CADASTRAR_PRODUTO');
INSERT INTO permissao VALUES (2, 'CONSULTAR_PRODUTO');
INSERT INTO permissao VALUES (3, 'CADASTRAR_USUARIO');

INSERT INTO grupo_permissao (id_grupo, id_permissao) VALUES (1, 1);
INSERT INTO grupo_permissao (id_grupo, id_permissao) VALUES (1, 2);
INSERT INTO grupo_permissao (id_grupo, id_permissao) VALUES (1, 3);

INSERT INTO usuario_grupo (id_usuario, id_grupo) VALUES (
	(SELECT id FROM usuario WHERE email = 'admin@thecode.com.br'), 1
);