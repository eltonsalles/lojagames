CREATE TABLE pedido (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	data_pedido DATE NOT NULL,
	id_cliente BIGINT(20) NOT NULL,
	tipo_pagamento VARCHAR(14) NOT NULL,
	forma_pagamento VARCHAR(9) NOT NULL,
	parcelas INTEGER NOT NULL,
	data_pagamento DATE NOT NULL,
	valor_subtotal DECIMAL(10, 2) NOT NULL,
	valor_frete DECIMAL(10, 2) NOT NULL,
	valor_total DECIMAL(10, 2) NOT NULL,
	status VARCHAR(20) NOT NULL,
	dias_entreda INTEGER NOT NULL,
	data_separacao DATE,
	data_tranporte DATE,
	data_entrega DATE,
    cep CHAR(8) NOT NULL,
    logradouro VARCHAR(150) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    complemento VARCHAR(100),
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    uf CHAR(2) NOT NULL,
    referencia VARCHAR(255),
	FOREIGN KEY (id_cliente) REFERENCES cliente(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE itens_pedido (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	id_pedido BIGINT(20) NOT NULL,
	id_produto BIGINT(20) NOT NULL,
	quantidade INTEGER NOT NULL,
	valor_unitario DECIMAL(10, 2) NOT NULL,
	FOREIGN KEY (id_pedido) REFERENCES pedido(id) ON DELETE CASCADE,
	FOREIGN KEY (id_produto) REFERENCES produto(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;