package br.senac.tads4.piiv.controller;

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
import br.senac.tads4.piiv.service.ClienteService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ClienteService clienteService;

	@RequestMapping(value = "/novo")
	public ModelAndView novo(Cliente cliente) {
		ModelAndView mv = new ModelAndView("site/cliente/CadastroCliente");
		mv.addObject("sexos", Sexo.values());
		return mv;
	}

	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView novo(@Valid Cliente cliente, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(cliente);
		}

		Long id;
		try {
			id = clienteService.salvar(cliente);
		} catch (Exception e) { // Trocar por uma exceção mais especifica
			// result.rejectValue();
			return novo(cliente);
		}

		attributes.addFlashAttribute("mensagem", "Bem vindo a The Code Games!");
		return new ModelAndView("redirect:/clientes/conta/index/" + id);
	}

	@RequestMapping(value = "/conta/index/{id}")
	public ModelAndView conta(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("site/cliente/PainelCliente");

		// #MOCK
		Cliente cliente = clienteRepository.findOne(id);
		mv.addObject("cliente", cliente);

		return mv;
	}

	@RequestMapping(value = "/conta/editar/dados-cadastrais/{id}")
	public ModelAndView editarDadosCadastrais(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("site/cliente/DadosCadastraisCliente");

		// #MOCK
		Cliente cliente = clienteRepository.findOne(id);
		mv.addObject("cliente", cliente);
		mv.addObject("sexos", Sexo.values());

		return mv;
	}

	@RequestMapping(value = "/conta/editar/dados-cadastrais")
	public ModelAndView editarDadosCadastrais(Cliente cliente) {
		ModelAndView mv = new ModelAndView("site/cliente/DadosCadastraisCliente");

		mv.addObject("cliente", cliente);
		mv.addObject("sexos", Sexo.values());

		return mv;
	}

	@RequestMapping(value = "/conta/editar/dados-cadastrais", method = RequestMethod.POST)
	public ModelAndView salvarDadosCadastrais(@Valid Cliente cliente, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return editarDadosCadastrais(cliente);
		}

		try {
			clienteService.alterar(cliente);
		} catch (Exception e) {
			return editarDadosCadastrais(cliente);
		}

		attributes.addFlashAttribute("mensagem", "Dados cadastrais alterados com sucesso!");
		return new ModelAndView("redirect:/clientes/conta/index/" + cliente.getId());
	}

	@RequestMapping(value = "/conta/editar/endereco-principal/{id}")
	public ModelAndView editarEnderecoPrincipal(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("site/cliente/EnderecoCliente");

		// #MOCK
		Cliente cliente = clienteRepository.findOne(id);
		mv.addObject("cliente", cliente);
		mv.addObject("endereco", cliente.getEnderecos().get(0));

		return mv;
	}

	@RequestMapping(value = "/conta/editar/endereco-principal")
	public ModelAndView editarEnderecoPrincipal(Endereco endereco) {
		ModelAndView mv = new ModelAndView("site/cliente/EnderecoCliente");
		
		// #MOCK
		Cliente cliente = clienteRepository.findOne(endereco.getCliente().getId());
		mv.addObject("cliente", cliente);
		mv.addObject("endereco", endereco);

		return mv;
	}

	@RequestMapping(value = "/conta/editar/endereco-principal", method = RequestMethod.POST)
	public ModelAndView salvarEnderecoPrincipal(@Valid Endereco endereco, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return editarEnderecoPrincipal(endereco);
		}

		try {
			clienteService.alterarEnderecoPrincipal(endereco);
		} catch (Exception e) {
			return editarEnderecoPrincipal(endereco);
		}

		attributes.addFlashAttribute("mensagem", "Endereço principal alterado com sucesso!");
		return new ModelAndView("redirect:/clientes/conta/index/" + endereco.getCliente().getId());
	}
	
	@RequestMapping(value = "/conta/editar/endereco-adicional/{id}/{index}")
	public ModelAndView editarEnderecoAdicional(@PathVariable Long id, @PathVariable Integer index) {
		ModelAndView mv = new ModelAndView("site/cliente/EnderecoCliente");

		// #MOCK
		Cliente cliente = clienteRepository.findOne(id);
		mv.addObject("cliente", cliente);
		mv.addObject("endereco", cliente.getEnderecos().get(index));

		return mv;
	}

	@RequestMapping(value = "/conta/editar/endereco-adicional")
	public ModelAndView editarEnderecoAdicional(Endereco endereco, @RequestParam Integer index) {
		ModelAndView mv = new ModelAndView("site/cliente/EnderecoCliente");
		
		// #MOCK
		Cliente cliente = clienteRepository.findOne(endereco.getCliente().getId());
		mv.addObject("cliente", cliente);
		mv.addObject("endereco", endereco);

		return mv;
	}

	@RequestMapping(value = "/conta/editar/endereco-adicional", method = RequestMethod.POST)
	public ModelAndView salvarEnderecoAdicional(@Valid Endereco endereco, @RequestParam Integer index, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return editarEnderecoAdicional(endereco, index);
		}

		try {
			clienteService.alterarEnderecoAdicional(endereco, index);
		} catch (Exception e) {
			return editarEnderecoAdicional(endereco, index);
		}

		attributes.addFlashAttribute("mensagem", "Endereço adicional alterado com sucesso!");
		return new ModelAndView("redirect:/clientes/conta/index/" + endereco.getCliente().getId());
	}
	
	@DeleteMapping(value = "/conta/remover-endereco-adicional/{id}/{index}")
	public @ResponseBody ResponseEntity<?> removerEnderecoAdicional(@PathVariable Long id, @PathVariable Integer index) {
		try {
			clienteService.removerEnderecoAdicional(id, index);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Não foi possível excluir o registro");
		}
		
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(value = "/conta/cadastrar/endereco-adicional/{id}/{index}")
	public ModelAndView cadastrarEnderecoAdicional(@PathVariable Long id, @PathVariable Integer index) {
		ModelAndView mv = new ModelAndView("site/cliente/NovoEnderecoCliente");

		// #MOCK
		Cliente cliente = clienteRepository.findOne(id);
		Endereco endereco = new Endereco();
		endereco.setCliente(cliente);
		mv.addObject("cliente", cliente);
		mv.addObject("endereco", endereco);

		return mv;
	}

	@RequestMapping(value = "/conta/cadastrar/endereco-adicional")
	public ModelAndView cadastrarEnderecoAdicional(Endereco endereco, @RequestParam Integer index) {
		ModelAndView mv = new ModelAndView("site/cliente/NovoEnderecoCliente");
		
		// #MOCK
		Cliente cliente = clienteRepository.findOne(endereco.getCliente().getId());
		mv.addObject("cliente", cliente);
		mv.addObject("endereco", endereco);

		return mv;
	}

	@RequestMapping(value = "/conta/cadastrar/endereco-adicional", method = RequestMethod.POST)
	public ModelAndView cadastrarEnderecoAdicional(@Valid Endereco endereco, @RequestParam Integer index, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return editarEnderecoAdicional(endereco, index);
		}

		try {
			clienteService.salvarEnderecoAdicional(endereco);
		} catch (Exception e) {
			return editarEnderecoAdicional(endereco, index);
		}

		attributes.addFlashAttribute("mensagem", "Endereço adicional adicionado com sucesso!");
		return new ModelAndView("redirect:/clientes/conta/index/" + endereco.getCliente().getId());
	}
}
