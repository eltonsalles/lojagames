package br.senac.tads4.piiv.model.enumerated;

public enum Idioma {
	INGLES("Inglês"),
	PORTUGUES_BR("Português BR");

	private String descricao;

	Idioma(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
