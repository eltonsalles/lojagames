package br.senac.tads4.piiv.model;

public enum Resolucao {
	R720P("720p"), R1080P("1080p"), R4K("4K");

	private String descricao;

	private Resolucao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
