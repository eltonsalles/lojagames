package br.senac.tads4.piiv.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.senac.tads4.piiv.model.enumerated.FormaPagamento;
import br.senac.tads4.piiv.model.enumerated.StatusPedido;
import br.senac.tads4.piiv.model.enumerated.TipoPagamento;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "O campo data do pedido é obrigatório")
	@Column(name = "data_pedido")
	private LocalDate dataPedido;

	@NotNull(message = "O campo cliente é obrigatório")
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;

	@NotNull(message = "O campo itens do pedido é obrigatório")
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<ItemPedido> itensPedido;

	@NotNull(message = "O campo tipo de pagamento é obrigatório")
	@Column(name = "tipo_pagamento")
	@Enumerated(EnumType.STRING)
	private TipoPagamento tipoPagamento;

	@NotNull(message = "O campo forma de pagamento é obrigatório")
	@Column(name = "forma_pagamento")
	@Enumerated(EnumType.STRING)
	private FormaPagamento formaPagamento;

	@NotNull(message = "O campo número de parcelas é obrigatório")
	private Integer parcelas;

	@NotNull(message = "O campo data de pagamento é obrigatório")
	@Column(name = "data_pagamento")
	private LocalDate dataPagamento;

	@NotNull(message = "O campo valor subtotal é obrigatório")
	@Column(name = "valor_subtotal")
	private BigDecimal valorSubTotal;

	@NotNull(message = "O campo valor do frete é obrigatório")
	@Column(name = "valor_frete")
	private BigDecimal valorFrete;

	@NotNull(message = "O campo valor total é obrigatório")
	@Column(name = "valor_total")
	private BigDecimal valorTotal;

	@NotNull(message = "O campo data da entrega é obrigatório")
	@Column(name = "dias_entrega")
	private Integer diasEntrega;

	@Column(name = "data_separacao")
	private LocalDate dataSeparacao;

	@Column(name = "data_transporte")
	private LocalDate dataTransporte;

	@Column(name = "data_entrega")
	private LocalDate dataEntrega;

	@Enumerated(EnumType.STRING)
	private StatusPedido status;

	@Embedded
	private EnderecoEntrega enderecoEntrega;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDate dataPedido) {
		this.dataPedido = dataPedido;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemPedido> getItensPedido() {
		return itensPedido;
	}

	public void setItensPedido(List<ItemPedido> itensPedido) {
		this.itensPedido = itensPedido;
	}

	public TipoPagamento getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(TipoPagamento tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Integer getParcelas() {
		return parcelas;
	}

	public void setParcelas(Integer parcelas) {
		this.parcelas = parcelas;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public BigDecimal getValorSubTotal() {
		return valorSubTotal;
	}

	public void setValorSubTotal(BigDecimal valorSubTotal) {
		this.valorSubTotal = valorSubTotal;
	}

	public BigDecimal getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Integer getDiasEntrega() {
		return diasEntrega;
	}

	public void setDiasEntrega(Integer diasEntrega) {
		this.diasEntrega = diasEntrega;
	}

	public LocalDate getDataSeparacao() {
		return dataSeparacao;
	}

	public void setDataSeparacao(LocalDate dataSeparacao) {
		this.dataSeparacao = dataSeparacao;
	}

	public LocalDate getDataTransporte() {
		return dataTransporte;
	}

	public void setDataTransporte(LocalDate dataTransporte) {
		this.dataTransporte = dataTransporte;
	}

	public LocalDate getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(LocalDate dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	public EnderecoEntrega getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(EnderecoEntrega enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
