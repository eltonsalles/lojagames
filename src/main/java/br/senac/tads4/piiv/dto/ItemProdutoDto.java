package br.senac.tads4.piiv.dto;

import java.math.BigDecimal;

public class ItemProdutoDto {
	private String img;
	private String item;
	private BigDecimal preco;
	private Integer qtde;
	private BigDecimal subtotal;

	public ItemProdutoDto() {

	}

	public ItemProdutoDto(String img, String item, BigDecimal preco, Integer qtde, BigDecimal subtotal) {
		this.img = img;
		this.item = item;
		this.preco = preco;
		this.qtde = qtde;
		this.subtotal = subtotal;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}
