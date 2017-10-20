package br.senac.tads4.piiv.model;

public enum Alimentacao {
	BATERIA_RECARREGAVEL("Bateria recarregável"),
	PILHAS_BATERIA_RECARREGAVEL("Pilhas AA e Bateria recarregável");
	
	private String descricao;
	
	Alimentacao (String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
}
