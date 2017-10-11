package br.senac.tads4.piiv.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senac.tads4.piiv.model.Controle;
import br.senac.tads4.piiv.service.ControleService;

@Controller
@RequestMapping(value = "/produtos/controles")
public class ControleController {

	@Autowired
	private ControleService controles;
	
	@RequestMapping(value = "/novo")
	public ModelAndView novo(Controle controle) {
		ModelAndView mv = new ModelAndView("backoffice/produto/CadastroControle");
		return mv;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView novo(@Valid Controle controle, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(controle);
		}
		
		try {
			controles.salvar(controle);
		} catch (Exception e) { // Trocar por uma exceção mais especifica
			// result.rejectValue();
			return novo(controle);
		}
		
		attributes.addFlashAttribute("message", "Controle cadastrado com sucesso!");
		return new ModelAndView("redirect:/produtos/controles/novo");
	}
}
