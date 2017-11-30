INSERT INTO permissao(id, nome) VALUES(12, 'ALTERAR_PRODUTO');
INSERT INTO permissao(id, nome) VALUES(13, 'EXCLUIR_PRODUTO');
INSERT INTO permissao(id, nome) VALUES(14, 'PESQUISAR_USUARIO');
INSERT INTO permissao(id, nome) VALUES(15, 'ALTERAR_USUARIO');
INSERT INTO permissao(id, nome) VALUES(16, 'EXCLUIR_USUARIO');
INSERT INTO permissao(id, nome) VALUES(17, 'INDEX');

INSERT INTO grupo_permissao(id_grupo, id_permissao) VALUES (1, 12);
INSERT INTO grupo_permissao(id_grupo, id_permissao) VALUES (1, 13);
INSERT INTO grupo_permissao(id_grupo, id_permissao) VALUES (1, 14);
INSERT INTO grupo_permissao(id_grupo, id_permissao) VALUES (1, 15);
INSERT INTO grupo_permissao(id_grupo, id_permissao) VALUES (1, 16);
INSERT INTO grupo_permissao(id_grupo, id_permissao) VALUES (1, 17);