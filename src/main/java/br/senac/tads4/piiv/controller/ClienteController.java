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
import br.senac.tads4.piiv.model.enumerated.Sexo;
import br.senac.tads4.piiv.service.ClienteService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clientes;
	
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
		
		try {
			clientes.salvar(cliente);
		} catch (Exception e) { // Trocar por uma exceção mais especifica
			// result.rejectValue();
			return novo(cliente);
		}
		
		attributes.addFlashAttribute("messagem", "Bem vindo a The Code Games!");
		return new ModelAndView("redirect:/clientes/novo"); // Redireciona para a página central do cliente
	}
	
	@RequestMapping(value = "/conta")
	public ModelAndView conta(Cliente cliente) {
		ModelAndView mv = new ModelAndView("site/Conta");
		return mv;
	}
}
