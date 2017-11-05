package br.senac.tads4.piiv.model.enumerated;

public enum StatusPedido {
	PENDENTE_PAGTO("Pendente de Pagamento"),
	SEPARANDO("Separando"),
	TRANSPORTE("Transporte"),
	ENTREGUE("Entregue"),
	CANCELADO("Cancelado");
	
	private String descricao;
	
	StatusPedido(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
