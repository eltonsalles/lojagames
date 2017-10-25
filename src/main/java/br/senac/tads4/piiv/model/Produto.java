package br.senac.tads4.piiv.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.senac.tads4.piiv.model.enumerated.TipoProduto;

@Entity
@Table(name = "produto")
@Inheritance(strategy = InheritanceType.JOINED)
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long idProduto;
	
	@Column(name = "tipo_produto")
	@Enumerated(EnumType.STRING)
	private TipoProduto tipoProduto;

	@NotNull(message = "O status é obrigatório")
	private Boolean status;
	
	@NotNull(message = "O tipo de console é obrigatóio")
	@ManyToOne
	@JoinColumn(name = "id_tipo_console")
	private TipoConsole tipoConsole;
	
	@NotBlank(message = "O nome é obrigatório")
	@Size(max = 150, message = "O campo nome deve ter no máximo 150 caracteres")
	private String nome;
	
	@NotNull(message = "O preço de compra é obrigatório")
	@DecimalMax(value = "9999999.99", message = "O preço de compra do produto deve ser menor que R$ 9.999.999,99")
	@Column(name = "preco_compra")
	private BigDecimal precoCompra;
	
	@NotNull(message = "O preço de venda é obrigatório")
	@DecimalMax(value = "9999999.99", message = "O preço de venda do produto deve ser menor que R$ 9.999.999,99")
	@Column(name = "preco_venda")
	private BigDecimal precoVenda;
	
	@NotBlank(message = "Os itens inclusos são obrigatórios")
	@Column(name = "itens_inclusos")
	private String itensInclusos;
	
	@NotNull(message = "O estoque é obrigatório")
	@Max(value = 9999, message = "O estoque deve ser menor que 9.999")
	private Integer estoque;
	
	@NotBlank(message = "A garantia é obrigatória")
	@Size(max = 2, message = "O campo garantia deve ter no máximo 2 caracteres")
	private String garantia;
	
	@NotBlank(message = "A descrição é obrigatória")
	@Size(max = 1000, message = "O campo descricao")
	private String descricao;
	
	@OneToMany(mappedBy = "produto", fetch = FetchType.EAGER)
	private List<Imagem> imagens;

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public TipoProduto getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(TipoProduto tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public TipoConsole getTipoConsole() {
		return tipoConsole;
	}

	public void setTipoConsole(TipoConsole tipoConsole) {
		this.tipoConsole = tipoConsole;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPrecoCompra() {
		return precoCompra;
	}

	public void setPrecoCompra(BigDecimal precoCompra) {
		this.precoCompra = precoCompra;
	}

	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}

	public String getItensInclusos() {
		return itensInclusos;
	}

	public void setItensInclusos(String itensInclusos) {
		this.itensInclusos = itensInclusos;
	}

	public Integer getEstoque() {
		return estoque;
	}

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}

	public String getGarantia() {
		return garantia;
	}

	public void setGarantia(String garantia) {
		this.garantia = garantia;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public List<Imagem> getImagens() {
		return imagens;
	}

	public void setImagens(List<Imagem> imagens) {
		this.imagens = imagens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idProduto == null) ? 0 : idProduto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (idProduto == null) {
			if (other.idProduto != null)
				return false;
		} else if (!idProduto.equals(other.idProduto))
			return false;
		return true;
	}
}
