package br.senac.tads4.piiv.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.senac.tads4.piiv.model.Cliente;
import br.senac.tads4.piiv.model.Endereco;
import br.senac.tads4.piiv.repository.ClienteRepository;
import br.senac.tads4.piiv.service.event.cliente.ClienteSalvoEvent;
import br.senac.tads4.piiv.service.exception.CpfClienteJaCadastradoException;
import br.senac.tads4.piiv.service.exception.EmailClienteJaCadastradoException;

/**
 * Classe responsável por persistir os dados no banco de dados na tabela cliente
 * 
 * @author Elton
 *
 */
@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clientes;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	/**
	 * Salva um novo cliente na base de dados
	 * 
	 * @param cliente
	 * @return
	 */
	public Long salvar(Cliente cliente, Endereco endereco) {
		String cpf = cliente.getCpf().replaceAll("\\D", "");
		Optional<Cliente> clienteCpfOptional = clientes.findByCpf(cpf);
		
		if (clienteCpfOptional.isPresent()) {
			throw new CpfClienteJaCadastradoException("O CPF informado já está cadastrado");
		}
		
		Optional<Cliente> clienteEmailOptional = clientes.findByEmailIgnoreCase(cliente.getEmail());
		
		if (clienteEmailOptional.isPresent()) {
			throw new EmailClienteJaCadastradoException("O e-mail informado já está cadastrado");
		}

		List<Endereco> listaEndereco = new ArrayList<>();
		
		endereco.setCliente(cliente);
		listaEndereco.add(endereco);

		cliente.setEnderecos(listaEndereco);
		
		publisher.publishEvent(new ClienteSalvoEvent(cliente));
		
		clientes.saveAndFlush(cliente);
		
		return cliente.getId();
	}
	
	/**
	 * Método para alterar os dados cadastrais do cliente
	 * 
	 * @param cliente
	 */
	public void alterar(Cliente cliente) {
		Cliente c = clientes.findOne(cliente.getId());
		c.setNome(cliente.getNome());
		c.setSexo(cliente.getSexo());
		c.setCpf(cliente.getCpf());
		c.setDataNascimento(cliente.getDataNascimento());
		c.setCelular(cliente.getCelular());
		c.setTelefone(cliente.getTelefone());
		c.setEmail(cliente.getEmail());
		
		clientes.save(c);
	}
	
	/**
	 * Método para alterar o endereço principal
	 * 
	 * @param endereco
	 */
	public void alterarEnderecoPrincipal(Endereco endereco) {
		Cliente c = clientes.findOne(endereco.getCliente().getId());
		c.getEnderecos().get(0).setCep(endereco.getCep());
		c.getEnderecos().get(0).setLogradouro(endereco.getLogradouro());
		c.getEnderecos().get(0).setNumero(endereco.getNumero());
		c.getEnderecos().get(0).setComplemento(endereco.getComplemento());
		c.getEnderecos().get(0).setBairro(endereco.getBairro());
		c.getEnderecos().get(0).setCidade(endereco.getCidade());
		c.getEnderecos().get(0).setUf(endereco.getUf());
		c.getEnderecos().get(0).setReferencia(endereco.getReferencia());
		
		clientes.save(c);
	}
	
	/**
	 * Método para alterar o endereço adicional
	 * 
	 * @param endereco
	 * @param index
	 */
	public void alterarEnderecoAdicional(Endereco endereco, Integer index) {
		Cliente c = clientes.findOne(endereco.getCliente().getId());
		c.getEnderecos().get(index).setCep(endereco.getCep());
		c.getEnderecos().get(index).setLogradouro(endereco.getLogradouro());
		c.getEnderecos().get(index).setNumero(endereco.getNumero());
		c.getEnderecos().get(index).setComplemento(endereco.getComplemento());
		c.getEnderecos().get(index).setBairro(endereco.getBairro());
		c.getEnderecos().get(index).setCidade(endereco.getCidade());
		c.getEnderecos().get(index).setUf(endereco.getUf());
		c.getEnderecos().get(index).setReferencia(endereco.getReferencia());
		
		clientes.save(c);
	}
	
	/**
	 * Método para remover um endereço adicional do cliente 
	 * 
	 * @param id
	 * @param index
	 */
	@Transactional
	public void removerEnderecoAdicional(Long id, Integer index) {
		Cliente cliente = clientes.findOne(id);
		Endereco endereco = cliente.getEnderecos().get(index);
		cliente.getEnderecos().remove(endereco);
		
		clientes.save(cliente);
	}
	
	/**
	 * Método para salvar um novo endereço adicional
	 * 
	 * @param endereco
	 */
	public void salvarEnderecoAdicional(Endereco endereco) {
		Cliente cliente = clientes.findOne(endereco.getCliente().getId());
		cliente.getEnderecos().add(endereco);
		
		clientes.save(cliente);
	}
}
