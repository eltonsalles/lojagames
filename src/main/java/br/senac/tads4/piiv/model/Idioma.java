package br.senac.tads4.piiv.model;

public enum Idioma {
	INGLES("Inglês"),
	PORTUGUES_BR("Português BR");

	private String descricao;

	private Idioma(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
