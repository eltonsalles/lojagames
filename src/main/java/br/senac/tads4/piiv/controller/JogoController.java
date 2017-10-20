package br.senac.tads4.piiv.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senac.tads4.piiv.model.Idioma;
import br.senac.tads4.piiv.model.Jogo;
import br.senac.tads4.piiv.model.Legenda;
import br.senac.tads4.piiv.model.Resolucao;
import br.senac.tads4.piiv.service.JogoService;

@Controller
@RequestMapping(value = "/produtos/jogos")
public class JogoController {

	@Autowired
	private JogoService jogos;

	@RequestMapping(value = "/novo")
	public ModelAndView novo(Jogo jogo) {
		ModelAndView mv = new ModelAndView("backoffice/produto/CadastroJogo");
		mv.addObject("idiomas", Idioma.values());
		mv.addObject("legendas", Legenda.values());
		mv.addObject("Resolucoes", Resolucao.values());
		return mv;
	}

	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView novo(@Valid Jogo jogo, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(jogo);
		}

		try {
			jogos.salvar(jogo);
		} catch (Exception e) { // Trocar por uma exceção mais especifica
			// result.rejectValue();
			return novo(jogo);
		}

		attributes.addFlashAttribute("message", "Jogo cadastrado com sucesso!");
		return new ModelAndView("redirect:/produtos/jogos/novo");
	}
}
