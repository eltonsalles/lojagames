package br.senac.tads4.piiv.model.enumerated;

public enum Capacidade {

	HD500GB("500GB"),
	HD1TB("1TB");
	
	private String descricao;

	Capacidade(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}
}
