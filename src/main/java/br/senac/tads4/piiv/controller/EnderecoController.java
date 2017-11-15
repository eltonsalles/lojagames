package br.senac.tads4.piiv.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senac.tads4.piiv.model.Cliente;
import br.senac.tads4.piiv.model.Endereco;
import br.senac.tads4.piiv.repository.ClienteRepository;
import br.senac.tads4.piiv.service.EnderecoService;

@Controller
@RequestMapping("/painel")
public class EnderecoController {

	@Autowired
	private EnderecoService enderecos;
	
	@Autowired
	private ClienteRepository clienteRepository;

	@RequestMapping(value = "/endereco")
	public ModelAndView novo(Endereco endereco) {
		ModelAndView mv = new ModelAndView("site/cliente/PainelCliente");
		return mv;
	}
	
	@RequestMapping(value = "/endereco", method = RequestMethod.POST)
	public ModelAndView novo(@Valid Endereco endereco, BindingResult result, RedirectAttributes attributes) {
		Cliente cliente = clienteRepository.findOne(1L);
		endereco.setCliente(cliente);
		
		// Estavamos validando o que vem do form e o que vem do form não tinha o cliente. Deixa esse if comentado por enquanto e faz a validação no front e termina a página!
		//if (result.hasErrors()) {
			//return novo(endereco);
		//}
		
		try {
			enderecos.salvar(endereco);
		} catch (Exception e) {
			//result.rejectValue("imagens", e.getMessage(), e.getMessage());
			return novo(endereco);
		}
		
		attributes.addFlashAttribute("mensagem", "Endereço adicionado com sucesso!");
		return new ModelAndView("redirect:/produtos/controles/novo");
	}

	
}
