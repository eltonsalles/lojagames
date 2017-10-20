package br.senac.tads4.piiv.model.enumerated;

public enum Midia {
	
	BLUERAY("Blue-Ray 3D");
		
	private String descricao;

	Midia(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
