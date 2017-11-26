create table contato (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	status VARCHAR(20) NOT NULL,
    data DATE NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    nome VARCHAR(150) NOT NULL,
    email varchar(150) NOT NULL,
    mensagem LONGTEXT NOT NULL,
    resposta LONGTEXT       
);

INSERT INTO contato (status, data, tipo, nome, email, mensagem) 
VALUES ('Pendente', now(), 'DUVIDAS', 'Elton Rodrigues', 'eltonrms.leite@gmail.com', 'Contato feito pelo formulário do site');