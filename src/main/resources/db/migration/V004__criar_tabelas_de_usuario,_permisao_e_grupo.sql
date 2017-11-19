CREATE TABLE usuario (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(150) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    status BOOLEAN DEFAULT true,
    data_nascimento DATE,
    UNIQUE INDEX email_UNIQUE (email ASC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE grupo (
    id BIGINT(20) PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    UNIQUE INDEX nome_UNIQUE (nome ASC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permissao (
    id BIGINT(20) PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    UNIQUE INDEX nome_UNIQUE (nome ASC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usuario_grupo (
    id_usuario BIGINT(20) NOT NULL,
    id_grupo BIGINT(20) NOT NULL,
    PRIMARY KEY (id_usuario, id_grupo),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id),
    FOREIGN KEY (id_grupo) REFERENCES grupo(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE grupo_permissao (
    id_grupo BIGINT(20) NOT NULL,
    id_permissao BIGINT(20) NOT NULL,
    PRIMARY KEY (id_grupo, id_permissao),
    FOREIGN KEY (id_grupo) REFERENCES grupo(id),
    FOREIGN KEY (id_permissao) REFERENCES permissao(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;