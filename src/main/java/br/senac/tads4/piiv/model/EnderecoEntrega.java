package br.senac.tads4.piiv.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Embeddable
public class EnderecoEntrega implements Serializable {

	private static final long serialVersionUID = 1L;

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
	@Size(max = 8, message = "O campo CEP deve ter no máximo 8 caracteres")
	private String cep;

	@NotBlank(message = "O campo referência é obrigatório")
	@Size(max = 255, message = "O campo referência deve ter no máximo 255 caracteres")
	private String referencia;

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

	public void setCep(String cep) {
		this.cep = cep.replaceAll("\\D", "");
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
}
