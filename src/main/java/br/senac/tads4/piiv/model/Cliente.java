package br.senac.tads4.piiv.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.caelum.stella.bean.validation.CPF;
import br.senac.tads4.piiv.validation.AtributoConfirmacao;
import br.senac.tads4.piiv.validation.Telefone;

@AtributoConfirmacao(atributo = "senha", atributoConfirmacao = "confirmacaoSenha", message = "O campo senha não é igual ao confirmação de senha")
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O nome é obrigatório")
	@Size(max = 150, message = "O campo nome deve ter no máximo 150 caracteres")
	private String nome;

	@NotNull(message = "O sexo é obrigatório")
	private String sexo;

	@NotBlank(message = "O CPF é obrigatório")
	@CPF(message = "CPF inválido")
	private String cpf;

	@NotNull(message = "A data de nascimento é obrigatória")
	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;

	@NotBlank(message = "O telefone é obrigatório")
	@Telefone(message = "Telefone inválido")
	private String telefone;

	@NotBlank(message = "O celular é obrigatório")
	@Telefone(message = "Celular inválido")
	private String celular;

	@NotBlank(message = "O e-mail é obrigatório")
	@Email(message = "E-mail inválido")
	@Size(max = 150, message = "O campo e-mail deve ter no máximo 150 caracteres")
	private String email;

	@Transient
	private String senha;
	
	@Transient
	private String confirmacaoSenha;
	
	@OneToMany(mappedBy = "cliente", targetEntity = Endereco.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Endereco> enderecos;
	
	@OneToMany(mappedBy = "cliente")
	private List<Pedido> pedidos;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	
	public String getPrimeiroNome() {
		String[] partesNome = this.nome.split(" ");
		String result = partesNome[0].substring(0, 1).toUpperCase() + partesNome[0].substring(1);
		return result;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}
	
	public String getTelefoneFormatado() {
		if (telefone.length() > 10) {
			return "(" + telefone.substring(0, 2) + ") " + telefone.substring(2, 7) + "-" + telefone.substring(7);
		} else {
			return "(" + telefone.substring(0, 2) + ") " + telefone.substring(2, 6) + "-" + telefone.substring(6);
		}
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}
	
	public String getCelularFormatado() {
		if (celular.length() > 10) {
			return "(" + celular.substring(0, 2) + ") " + celular.substring(2, 7) + "-" + celular.substring(7);
		} else {
			return "(" + celular.substring(0, 2) + ") " + celular.substring(2, 6) + "-" + celular.substring(6);
		}
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@PrePersist
	@PreUpdate
	private void prePersistUpdate() {
		this.cpf = this.cpf.replaceAll("\\D", "");
		this.telefone = this.telefone.replaceAll("\\D", "");
		this.celular = this.celular.replaceAll("\\D", "");
	}
	
	@PostLoad
	private void postLoad() {
		this.cpf = String.format("%s.%s.%s-%s", this.cpf.substring(0, 3), this.cpf.substring(3,  6), this.cpf.substring(6, 9), this.cpf.substring(9, 11));
	}
}
