package br.senac.tads4.piiv.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "controle")
@PrimaryKeyJoinColumn(name = "idProduto")
public class Controle extends Produto {

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Fabricante é obrigatório")
	@Size(max = 50, message = "O campo fabricante deve ter no máximo 50 caracteres")
	private String fabricante;
	
	@NotNull(message = "Conexão é obrigatório")
	@Enumerated(EnumType.STRING)
	private Conexao conexao;
	
	@NotNull(message = "Alimentação é obrigatório")
	@Enumerated(EnumType.STRING)
	private Alimentacao alimentacao;
	
	@NotBlank(message = "Alcance é obrigatório")
	@Size(max = 25, message = "O campo fabricante deve ter no máximo 3 caracteres")
	private String alcance;
	
	@NotNull(message = "Cor é obrigatório")
	@Enumerated(EnumType.STRING)
	private Cor cor;
	
	public String getFabricante() {
		return fabricante;
	}
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	public Conexao getConexao() {
		return conexao;
	}
	public void setConexao(Conexao conexao) {
		this.conexao = conexao;
	}
	public Alimentacao getAlimentacao() {
		return alimentacao;
	}
	public void setAlimentacao(Alimentacao alimentacao) {
		this.alimentacao = alimentacao;
	}
	public String getAlcance() {
		return alcance;
	}
	public void setAlcance(String alcance) {
		this.alcance = alcance;
	}
	public Cor getCor() {
		return cor;
	}
	public void setCor(Cor cor) {
		this.cor = cor;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
