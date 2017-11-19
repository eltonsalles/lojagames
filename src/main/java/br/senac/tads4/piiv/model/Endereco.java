package br.senac.tads4.piiv.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "endereco")
public class Endereco implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;

	@NotBlank(message = "O campo logradouro é obrigatório")
	@Size(max = 150, message = "O campo logradouro deve ter no máximo 150 caracteres")
	private String logradouro;

	@NotBlank(message = "O campo número é obrigatório")
	@Size(max = 10, message = "O campo número deve ter no máximo 10 caracteres")
	private String numero;

	@Size(max = 150, message = "O campo logradouro deve ter no máximo 100 caracteres")
	private String complemento;

	@NotBlank(message = "O campo bairro é obrigatório")
	@Size(max = 100, message = "O campo bairro deve ter no máximo 100 caracteres")
	private String bairro;

	@NotBlank(message = "O campo cidade é obrigatório")
	@Size(max = 100, message = "O campo logradouro deve ter no máximo 100 caracteres")
	private String cidade;

	@NotBlank(message = "O campo UF é obrigatório")
	@Size(max = 2, message = "O campo UF deve ter no máximo 2 caracteres")
	private String uf;

	@NotBlank(message = "O campo CEP é obrigatório")
	@Size(max = 9, message = "O campo CEP deve ter no máximo 8 caracteres")
	private String cep;

	@NotBlank(message = "O campo referência é obrigatório")
	@Size(max = 255, message = "O campo referência deve ter no máximo 255 caracteres")
	private String referencia;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCep() {
		return cep;
	}
	
	public String getCepFormatado() {
		return cep.substring(0, 5) + "-" + cep.substring(5);
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	@PrePersist
	@PreUpdate
	private void prePersistUpdate() {
		this.cep = this.cep.replaceAll("\\D", "");
		this.uf = this.uf.toUpperCase();
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
		Endereco other = (Endereco) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
