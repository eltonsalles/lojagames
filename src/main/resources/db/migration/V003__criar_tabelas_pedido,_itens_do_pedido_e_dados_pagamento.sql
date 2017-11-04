CREATE TABLE dados_pagamento(
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	tipo_pagamento VARCHAR(14) NOT NULL,
	forma_pagamento VARCHAR(9) NOT NULL,
	parcelas INTEGER NOT NULL,
	data_pagamento DATE NOT NULL,
	valor_subtotal DECIMAL(10, 2) NOT NULL,
	valor_frete DECIMAL(10, 2) NOT NULL,
	valor_total DECIMAL(10, 2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE pedido (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	id_cliente BIGINT(20) NOT NULL,
	id_dados_pagamento BIGINT(20) NOT NULL,
	data_pedido DATE NOT NULL,
	dias_entreda INTEGER NOT NULL,
	data_previsao_entrega DATE NOT NULL,
	data_separacao DATE NOT NULL,
	data_tranporte DATE NOT NULL,
	data_entrega DATE NOT NULL,
	FOREIGN KEY (id_cliente) REFERENCES cliente(id),
	FOREIGN KEY (id_dados_pagamento) REFERENCES dados_pagamento(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE itens_pedido (
	id_pedido BIGINT(20) NOT NULL,
	id_produto BIGINT(20) NOT NULL,
	quantidade INTEGER NOT NULL,
	valor_unitario DECIMAL(10, 2) NOT NULL,
	PRIMARY KEY (id_pedido, id_produto),
	FOREIGN KEY (id_pedido) REFERENCES pedido(id) ON DELETE CASCADE,
	FOREIGN KEY (id_produto) REFERENCES produto(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;