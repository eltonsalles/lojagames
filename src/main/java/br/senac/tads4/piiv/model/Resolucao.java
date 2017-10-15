package br.senac.tads4.piiv.model;

public enum Resolucao {
	
	R1080("1080"),
	R4K("4K");
	
	private String descricao;

	Resolucao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
