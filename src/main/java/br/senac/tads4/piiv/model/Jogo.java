package br.senac.tads4.piiv.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "jogo")
@PrimaryKeyJoinColumn(name = "idProduto")
public class Jogo extends Produto {

	private static final long serialVersionUID = 1L;
	
	private String edicao;

	public String getEdicao() {
		return edicao;
	}

	public void setEdicao(String edicao) {
		this.edicao = edicao;
	}
}
