package br.senac.tads4.piiv.dto;

import java.math.BigDecimal;

public class ItemProdutoDto {
	private Long id;
	private String img;
	private String descricao;
	private String nome;
	private BigDecimal preco;
	private Integer qtde;
	private BigDecimal subtotal;

	public ItemProdutoDto() {

	}

	public ItemProdutoDto(Long id, String img, String descricao, String nome, BigDecimal preco, Integer qtde,
			BigDecimal subtotal) {
		this.id = id;
		this.img = img;
		this.nome = nome;
		this.preco = preco;
		this.qtde = qtde;
		this.subtotal = subtotal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Integer getQtde() {
		return qtde;
	}

	public void setQtde(Integer qtde) {
		this.qtde = qtde;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
}
