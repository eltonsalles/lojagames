package br.senac.tads4.piiv.model;

public enum Legenda {
	INGLES("Inglês"),
	PORTUGUES_BR("Português BR");

	private String descricao;

	private Legenda(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
