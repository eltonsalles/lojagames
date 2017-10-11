package br.senac.tads4.piiv.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "controle")
@PrimaryKeyJoinColumn(name = "idProduto")
public class Controle extends Produto {

	private static final long serialVersionUID = 1L;
	
	private String fabricante;

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
}
