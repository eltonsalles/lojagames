package br.senac.tads4.piiv.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	@NotBlank(message = "Nome é obrigatorio")
	@Size(max = 150, message = "O Campo nome deve ter no maximo 150 caracteres")
	private String nome;
	
	@NotNull(message = "O Campo Data de Nascimento é obrigatorio")
	private String sexo;
	
	@NotBlank(message = "CPF é obrigatorio")
	@Size(max = 11, message = "O Campo cpf deve ter no maximo 11 caracteres")
	private String cpf;
	
	@NotNull(message = "O Campo Data de Nascimento é obrigatorio")
	private LocalDate dataNascimento;
	
	@NotBlank(message = "telefone é obrigatorio")
	@Size(max = 11, message = "O Campo telefone deve ter no maximo 11 caracteres")
	private String telefone;

	@NotBlank(message = "celular é obrigatorio")
	@Size(max = 11, message = "O Campo celular deve ter no maximo 11 caracteres")
	private String celular;
	
	@NotBlank(message = "Email é obrigatorio")
	@Size(max = 150, message = "O Campo email deve ter no maximo 150 caracteres")
	private String email;
	
	@NotBlank(message = "Nome é obrigatorio")
	@Size(max = 150, message = "O Campo nome deve ter no maximo 150 caracteres")
	private String senha;

	public String getNome() {
		return nome;
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

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
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
	
		
}
