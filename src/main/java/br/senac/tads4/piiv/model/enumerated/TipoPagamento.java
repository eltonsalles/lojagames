package br.senac.tads4.piiv.model.enumerated;

public enum TipoPagamento {
	CARTAO_CREDIDO("Cartão de Crédito"),
	BOLETO("Boleto");
	
	private String descricao;
	
	TipoPagamento (String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
