package br.senac.tads4.piiv.model.enumerated;

public enum TipoMovimentacao {
	ENTRADA("Entrada"),
	QUEBRA("Quebra"),
	VENDA("Venda"),
	CANCELAMENTO("Cancelamento");

	private String descricao;

	TipoMovimentacao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
