package br.senac.tads4.piiv.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senac.tads4.piiv.model.Contato;
import br.senac.tads4.piiv.model.enumerated.TipoContato;
import br.senac.tads4.piiv.service.ContatoService;

@Controller
@RequestMapping(value = "/contatos")
public class ContatoController {

	@Autowired
	private ContatoService contatoService;

	/**
	 * Exibe o formulário de contato
	 * 
	 * @param contato
	 * @return
	 */
	@RequestMapping(value = "/novo")
	public ModelAndView novo(Contato contato) {
		ModelAndView mv = new ModelAndView("site/contato/Contato");
		mv.addObject("tiposContato", TipoContato.values());
		return mv;
	}

	/**
	 * Método responsável por cuidar da submissão do formulário de contato do cliente
	 * 
	 * @param contato
	 * @param result
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView novo(@Valid Contato contato, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(contato);
		}

		Long id;
		try {
			id = contatoService.salvar(contato);
		} catch (Exception e) {
			return novo(contato);
		}

		attributes.addFlashAttribute("mensagem", "Contato enviado com sucesso! - O número do seu protocolo é: " + id);
		return new ModelAndView("redirect:/contatos/novo");
	}
}
