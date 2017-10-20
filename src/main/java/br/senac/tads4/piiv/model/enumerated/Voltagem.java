package br.senac.tads4.piiv.model.enumerated;

public enum Voltagem {
	
	BIVOLT("Bivolt");
		
	private String descricao;

	Voltagem(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
