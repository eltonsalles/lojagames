package br.senac.tads4.piiv.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senac.tads4.piiv.model.Cliente;
import br.senac.tads4.piiv.model.Endereco;
import br.senac.tads4.piiv.model.enumerated.Sexo;
import br.senac.tads4.piiv.repository.ClienteRepository;
import br.senac.tads4.piiv.repository.TipoConsoleRepository;
import br.senac.tads4.piiv.security.UsuarioSistema;
import br.senac.tads4.piiv.service.ClienteService;
import br.senac.tads4.piiv.service.exception.CpfClienteJaCadastradoException;
import br.senac.tads4.piiv.service.exception.EmailClienteJaCadastradoException;
import br.senac.tads4.piiv.service.exception.EmailUsuarioJaCadastradoException;
import br.senac.tads4.piiv.service.exception.SenhaUsuarioObrigatoriaException;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private TipoConsoleRepository tipoConsoleRepository;

	@Autowired
	private ClienteService clienteService;

	/**
	 * Exibe o formulário para cadastro de um novo cliente
	 * 
	 * @param cliente
	 * @param endereco
	 * @return
	 */
	@RequestMapping(value = "/novo")
	public ModelAndView novo(Cliente cliente, Endereco endereco) {
		ModelAndView mv = new ModelAndView("site/cliente/CadastroCliente");
		this.menu(mv);
		mv.addObject("sexos", Sexo.values());
		return mv;
	}

	/**
	 * Método que faz a inserção de um novo cliente e cria um usuário com perfíl de
	 * cliente para acessar o sistema. Antes de salvar um cliente o sistema dispara
	 * o evento para tentar criar o perfíl de usuário
	 * 
	 * @param cliente
	 * @param result
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView novo(@Valid Cliente cliente, BindingResult resultCliente, @Valid Endereco endereco,
			BindingResult resultEndereco, RedirectAttributes attributes) {
		if (resultCliente.hasErrors()) {
			return novo(cliente, endereco);
		}

		if (resultEndereco.hasErrors()) {
			resultCliente.rejectValue("enderecos", "", "Existem campos obrigatórios do endereço sem preenchimento");
			return novo(cliente, endereco);
		}

		try {
			clienteService.salvar(cliente, endereco);
		} catch (CpfClienteJaCadastradoException e) {
			resultCliente.rejectValue("cpf", e.getMessage(), e.getMessage());
			return novo(cliente, endereco);
		} catch (EmailClienteJaCadastradoException e) {
			resultCliente.rejectValue("email", e.getMessage(), e.getMessage());
			return novo(cliente, endereco);
		} catch (EmailUsuarioJaCadastradoException e) {
			resultCliente.rejectValue("email", e.getMessage(), e.getMessage());
			return novo(cliente, endereco);
		} catch (SenhaUsuarioObrigatoriaException e) {
			resultCliente.rejectValue("senha", e.getMessage(), e.getMessage());
			return novo(cliente, endereco);
		}

		attributes.addFlashAttribute("mensagem", "Bem vindo a The Code Games!");
		return new ModelAndView("redirect:/clientes/conta/index");
	}

	/**
	 * Exibe a page home do cliente
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/conta/index")
	public ModelAndView conta(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("site/cliente/PainelCliente");

		Cliente cliente = this.getUsuarioLogado(request);
		cliente = clienteRepository.findOne(cliente.getId());

		if (cliente == null) {
			return new ModelAndView("redirect:/logout");
		}

		mv.addObject("cliente", cliente);
		this.menu(mv);
		return mv;
	}

	/**
	 * Exibe o formulário com os dados cadastrais do cliente
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/conta/editar/dados-cadastrais")
	public ModelAndView editarDadosCadastrais(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("site/cliente/DadosCadastraisCliente");

		Cliente cliente = this.getUsuarioLogado(request);
		cliente = clienteRepository.findOne(cliente.getId());

		if (cliente == null) {
			return new ModelAndView("redirect:/logout");
		}

		mv.addObject("cliente", cliente);
		mv.addObject("sexos", Sexo.values());
		this.menu(mv);
		return mv;
	}

	/**
	 * Exibe o formulário com os dados cadastrais do cliente quando existe erro no
	 * preenchimento
	 * 
	 * @param cliente
	 * @return
	 */
	private ModelAndView editarDadosCadastrais(Cliente cliente) {
		ModelAndView mv = new ModelAndView("site/cliente/DadosCadastraisCliente");

		mv.addObject("cliente", cliente);
		mv.addObject("sexos", Sexo.values());
		this.menu(mv);
		return mv;
	}

	/**
	 * Método para salvar as alterações dos dados cadastrais do cliente
	 * 
	 * @param cliente
	 * @param result
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/conta/editar/dados-cadastrais", method = RequestMethod.POST)
	public ModelAndView salvarDadosCadastrais(@Valid Cliente cliente, BindingResult result,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return editarDadosCadastrais(cliente);
		}

		try {
			clienteService.alterar(cliente);
		} catch (Exception e) {
			return editarDadosCadastrais(cliente);
		}

		attributes.addFlashAttribute("mensagem", "Dados cadastrais alterados com sucesso!");
		return new ModelAndView("redirect:/clientes/conta/index");
	}

	/**
	 * Exibe o formulário com o endereço principal do cliente
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/conta/editar/endereco-principal")
	public ModelAndView editarEnderecoPrincipal(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("site/cliente/EnderecoCliente");

		Cliente cliente = this.getUsuarioLogado(request);
		cliente = clienteRepository.findOne(cliente.getId());

		if (cliente == null) {
			return new ModelAndView("redirect:/logout");
		}

		mv.addObject("cliente", cliente);
		mv.addObject("endereco", cliente.getEnderecos().get(0));
		this.menu(mv);
		return mv;
	}

	/**
	 * Exibe o formulário com o endereço principal do cliente quando existem erros
	 * de preenchimento
	 * 
	 * @param endereco
	 * @return
	 */
	private ModelAndView editarEnderecoPrincipal(Endereco endereco) {
		ModelAndView mv = new ModelAndView("site/cliente/EnderecoCliente");

		Cliente cliente = clienteRepository.findOne(endereco.getCliente().getId());
		mv.addObject("cliente", cliente);
		mv.addObject("endereco", endereco);
		this.menu(mv);
		return mv;
	}

	/**
	 * Método para salvar as alterações no endereço principal do cliente
	 * 
	 * @param endereco
	 * @param result
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/conta/editar/endereco-principal", method = RequestMethod.POST)
	public ModelAndView salvarEnderecoPrincipal(@Valid Endereco endereco, BindingResult result,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return editarEnderecoPrincipal(endereco);
		}

		try {
			clienteService.alterarEnderecoPrincipal(endereco);
		} catch (Exception e) {
			return editarEnderecoPrincipal(endereco);
		}

		attributes.addFlashAttribute("mensagem", "Endereço principal alterado com sucesso!");
		return new ModelAndView("redirect:/clientes/conta/index");
	}

	/**
	 * Exibe o formulário com endereço adicional do cliente
	 * 
	 * @param id
	 * @param index
	 * @return
	 */
	@RequestMapping(value = "/conta/editar/endereco-adicional/{index}")
	public ModelAndView editarEnderecoAdicional(@PathVariable Integer index, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("site/cliente/EnderecoCliente");

		Cliente cliente = this.getUsuarioLogado(request);
		cliente = clienteRepository.findOne(cliente.getId());

		if (cliente == null) {
			return new ModelAndView("redirect:/logout");
		}

		mv.addObject("cliente", cliente);
		mv.addObject("endereco", cliente.getEnderecos().get(index));
		this.menu(mv);
		return mv;
	}

	/**
	 * Exibe o formulário com endereço adicional do cliente quando existem erros de
	 * preenchimento
	 * 
	 * @param endereco
	 * @param index
	 * @return
	 */
	private ModelAndView editarEnderecoAdicional(Endereco endereco, @RequestParam Integer index) {
		ModelAndView mv = new ModelAndView("site/cliente/EnderecoCliente");

		Cliente cliente = clienteRepository.findOne(endereco.getCliente().getId());
		mv.addObject("cliente", cliente);
		mv.addObject("endereco", endereco);
		this.menu(mv);
		return mv;
	}

	/**
	 * Método para salvar as alterações no endereço adicional do cliente
	 * 
	 * @param endereco
	 * @param index
	 * @param result
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/conta/editar/endereco-adicional", method = RequestMethod.POST)
	public ModelAndView salvarEnderecoAdicional(@Valid Endereco endereco, @RequestParam Integer index,
			BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return editarEnderecoAdicional(endereco, index);
		}

		try {
			clienteService.alterarEnderecoAdicional(endereco, index);
		} catch (Exception e) {
			return editarEnderecoAdicional(endereco, index);
		}

		attributes.addFlashAttribute("mensagem", "Endereço adicional alterado com sucesso!");
		return new ModelAndView("redirect:/clientes/conta/index");
	}

	/**
	 * Método para excluir endereço adicional
	 * 
	 * @param id
	 * @param index
	 * @return
	 */
	@DeleteMapping(value = "/conta/remover-endereco-adicional/{id}/{index}")
	public @ResponseBody ResponseEntity<?> removerEnderecoAdicional(@PathVariable Long id,
			@PathVariable Integer index, HttpServletRequest request) {
		this.conferirId(id, request);

		try {
			clienteService.removerEnderecoAdicional(id, index);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Não foi possível excluir o registro");
		}

		return ResponseEntity.ok().build();
	}

	/**
	 * Exibe o formulário para cadastrar um novo endereço para o cliente
	 * 
	 * @param id
	 * @param index
	 * @return
	 */
	@RequestMapping(value = "/conta/cadastrar/endereco-adicional/{index}")
	public ModelAndView cadastrarEnderecoAdicional(@PathVariable Integer index, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("site/cliente/NovoEnderecoCliente");

		Cliente cliente = this.getUsuarioLogado(request);
		cliente = clienteRepository.findOne(cliente.getId());

		if (cliente == null) {
			return new ModelAndView("redirect:/logout");
		}

		Endereco endereco = new Endereco();
		endereco.setCliente(cliente);
		
		mv.addObject("cliente", cliente);
		mv.addObject("endereco", endereco);
		this.menu(mv);
		return mv;
	}

	/**
	 * Exibe o formulário com o novo endereço adicional quando existem erros
	 * 
	 * @param endereco
	 * @param index
	 * @return
	 */
	private ModelAndView cadastrarEnderecoAdicional(Endereco endereco, @RequestParam Integer index) {
		ModelAndView mv = new ModelAndView("site/cliente/NovoEnderecoCliente");

		Cliente cliente = clienteRepository.findOne(endereco.getCliente().getId());
		
		mv.addObject("cliente", cliente);
		mv.addObject("endereco", endereco);
		this.menu(mv);
		return mv;
	}

	/**
	 * Método para salvar o novo endereço adicional
	 * 
	 * @param endereco
	 * @param index
	 * @param result
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/conta/cadastrar/endereco-adicional", method = RequestMethod.POST)
	public ModelAndView cadastrarEnderecoAdicional(@Valid Endereco endereco, @RequestParam Integer index,
			BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return cadastrarEnderecoAdicional(endereco, index);
		}

		try {
			clienteService.salvarEnderecoAdicional(endereco);
		} catch (Exception e) {
			return cadastrarEnderecoAdicional(endereco, index);
		}

		attributes.addFlashAttribute("mensagem", "Endereço adicional adicionado com sucesso!");
		return new ModelAndView("redirect:/clientes/conta/index");
	}
	
	/**
	 * Monta o menu superior
	 * 
	 * @param mv
	 */
	private void menu(ModelAndView mv) {
		mv.addObject("tiposConsole", tipoConsoleRepository.findAll());
	}

	/**
	 * Método para garantir que a manipulação dos dados do cliente sejam apenas para
	 * o cliente logado no sistema
	 * 
	 * @param id
	 * @return
	 */
	private ModelAndView conferirId(Long id, HttpServletRequest request) {
		Cliente cliente = this.getUsuarioLogado(request);
		
		try {
			if (id != cliente.getId()) {
				return new ModelAndView("redirect:/logout");
			}
		} catch (Exception e) {
			return null;
		}

		return null;
	}

	/**
	 * Retorna o usuário cliente logado
	 * 
	 * @param request
	 * @return
	 */
	private Cliente getUsuarioLogado(HttpServletRequest request) {
		try {
			HttpSession session = (HttpSession) request.getSession();
			UsuarioSistema usuarioSistema = (UsuarioSistema) session.getAttribute("usuarioLogado");

			return usuarioSistema.getCliente();
		} catch (Exception e) {
			return null;
		}
	}
}
