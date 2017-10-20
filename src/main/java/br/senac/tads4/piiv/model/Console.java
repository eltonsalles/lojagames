package br.senac.tads4.piiv.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.senac.tads4.piiv.model.enumerated.Capacidade;
import br.senac.tads4.piiv.model.enumerated.Cor;
import br.senac.tads4.piiv.model.enumerated.Midia;
import br.senac.tads4.piiv.model.enumerated.Resolucao;
import br.senac.tads4.piiv.model.enumerated.Voltagem;


@Entity
@Table(name = "console")
@PrimaryKeyJoinColumn(name = "idProduto")
public class Console extends Produto {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "O campo fabricante é obrigatório")
	@Size(max= 50, message = "O campo fabricante deve ter no máximo 50 caracteres")
	private String fabricante;
	
	@NotBlank(message = "O campo fabricante é obrigatório")
	@Size(max= 25, message = "O campo fabricante deve ter no máximo 50 caracteres")
	private String modelo;
	
	@NotNull(message = "O campo capacidade é obrigatório")
	@Enumerated(EnumType.STRING)
	private Capacidade capacidade;
		
	@NotNull(message = "O campo voltagem é obrigatório")
	@Enumerated(EnumType.STRING)
	private Voltagem voltagem;
	
	@NotNull(message = "O campo cor é obrigatório")
	@Enumerated(EnumType.STRING)
	private Cor cor;
	
	@NotNull(message = "O campo resolução é obrigatório")
	@Enumerated(EnumType.STRING)
	private Resolucao resolucao;
	
	@NotNull(message = "O campo midia é obrigatório")
	@Enumerated(EnumType.STRING)
	private Midia midia;
	
	
	public Capacidade getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(Capacidade capacidade) {
		this.capacidade = capacidade;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Voltagem getVoltagem() {
		return voltagem;
	}

	public void setVoltagem(Voltagem voltagem) {
		this.voltagem = voltagem;
	}

	public Cor getCor() {
		return cor;
	}

	public void setCor(Cor cor) {
		this.cor = cor;
	}

	public Resolucao getResolucao() {
		return resolucao;
	}

	public void setResolucao(Resolucao resolucao) {
		this.resolucao = resolucao;
	}

	public Midia getMidia() {
		return midia;
	}

	public void setMidia(Midia midia) {
		this.midia = midia;
	}
	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	
}
