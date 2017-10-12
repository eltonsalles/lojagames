package br.senac.tads4.piiv.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "jogo")
@PrimaryKeyJoinColumn(name = "idProduto")
public class Jogo extends Produto {

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Edição é obrigatório")
	@Size(max = 25, message = "O campo edição deve ter no máximo 25 caracteres")
	private String edicao;
	
	@NotBlank(message = "Produtora é obrigatório")
	@Size(max = 50, message = "O campo produtora deve ter no máximo 50 caracteres")
	private String produtora;
	
	@NotNull(message = "Lançamento é obrigatório")
	private LocalDate lancamento;
	
	@NotNull(message = "Idioma é obrigatório")
	@Enumerated(EnumType.STRING)
	private Idioma idioma;
	
	@NotNull(message = "Legenda é obrigatório")
	@Enumerated(EnumType.STRING)
	private Legenda legenda;
	
	@NotBlank(message = "Idade é obrigatório")
	@Size(max = 5, message = "O campo idade deve ter no máximo 5 caracteres")
	private String idade;
	
	@NotNull(message = "A quantidade de jogadores offline é obrigatória")
	@Max(value = 23, message = "O campo de jogadores offline não pode ultrapassar 22")
	@Column(name = "quantidade_jogadores_off")
	private Integer qtdeJogadoresOff;
	
	@NotNull(message = "A quantidade de jogadores online é obrigatória")
	@Max(value = 23, message = "O campo de jogadores online não pode ultrapassar 22")
	@Column(name = "quantidade_jogadores_on")
	private Integer qtdeJogadoresOn;
	
	@NotNull(message = "Resolucao é obrigatório")
	@Enumerated(EnumType.STRING)
	private Resolucao resolucao;

	public String getEdicao() {
		return edicao;
	}

	public void setEdicao(String edicao) {
		this.edicao = edicao;
	}

	public String getProdutora() {
		return produtora;
	}

	public void setProdutora(String produtora) {
		this.produtora = produtora;
	}

	public LocalDate getLancamento() {
		return lancamento;
	}

	public void setLancamento(LocalDate lancamento) {
		this.lancamento = lancamento;
	}

	public Idioma getIdioma() {
		return idioma;
	}

	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}

	public Legenda getLegenda() {
		return legenda;
	}

	public void setLegenda(Legenda legenda) {
		this.legenda = legenda;
	}

	public String getIdade() {
		return idade;
	}

	public void setIdade(String idade) {
		this.idade = idade;
	}

	public Integer getQtdeJogadoresOff() {
		return qtdeJogadoresOff;
	}

	public void setQtdeJogadoresOff(Integer qtdeJjogadoresOff) {
		this.qtdeJogadoresOff = qtdeJjogadoresOff;
	}

	public Integer getQtdeJogadoresOn() {
		return qtdeJogadoresOn;
	}

	public void setQtdeJogadoresOn(Integer qtdeJjogadoresOn) {
		this.qtdeJogadoresOn = qtdeJjogadoresOn;
	}

	public Resolucao getResolucao() {
		return resolucao;
	}

	public void setResolucao(Resolucao resolucao) {
		this.resolucao = resolucao;
	}

}
