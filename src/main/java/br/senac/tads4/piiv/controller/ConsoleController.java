package br.senac.tads4.piiv.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senac.tads4.piiv.model.Capacidade;
import br.senac.tads4.piiv.model.Console;
import br.senac.tads4.piiv.model.Cor;
import br.senac.tads4.piiv.model.Midia;
import br.senac.tads4.piiv.model.Resolucao;
import br.senac.tads4.piiv.model.Voltagem;
import br.senac.tads4.piiv.service.ConsoleService;

@Controller
@RequestMapping(value = "/produtos/consoles")
public class ConsoleController {

	@Autowired
	private ConsoleService consoles;

	@RequestMapping(value = "/novo")
	public ModelAndView novo(Console console) {
		ModelAndView mv = new ModelAndView("backoffice/produto/CadastroConsole");
		mv.addObject("capacidades", Capacidade.values());
		mv.addObject("voltagens", Voltagem.values());
		mv.addObject("cores", Cor.values());
		mv.addObject("resolucoes", Resolucao.values());
		mv.addObject("midias", Midia.values());		
		return mv;
	}

	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView novo(@Valid Console console, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(console);
		}

		try {
			consoles.salvar(console);
		} catch (Exception e) { // Trocar por uma exceção mais especifica
			// result.rejectValue();
			return novo(console);
		}

		attributes.addFlashAttribute("message", "Console cadastrado com sucesso!");
		return new ModelAndView("redirect:/produtos/consoles/novo");
	}
}
