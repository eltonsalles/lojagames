package br.senac.tads4.piiv.model.enumerated;

public enum TipoProduto {
	
	CONSOLE("Console"),
	CONTROLE("Controle"),
	JOGO("Wireless bluetooth");

	private String descricao;

	TipoProduto (String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
