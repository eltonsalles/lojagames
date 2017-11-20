package br.senac.tads4.piiv.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senac.tads4.piiv.model.Jogo;
import br.senac.tads4.piiv.model.enumerated.Idioma;
import br.senac.tads4.piiv.model.enumerated.Legenda;
import br.senac.tads4.piiv.model.enumerated.Resolucao;
import br.senac.tads4.piiv.repository.GeneroRepository;
import br.senac.tads4.piiv.repository.TipoConsoleRepository;
import br.senac.tads4.piiv.service.JogoService;
import br.senac.tads4.piiv.service.exception.DescricaoDaImagemPassaLimiteCaractesException;
import br.senac.tads4.piiv.service.exception.DescricaoDaImagemVaziaException;
import br.senac.tads4.piiv.service.exception.ListaDeImagensVaziasException;

@Controller
@RequestMapping(value = "/admin/produtos/jogos")
public class JogoController {

	@Autowired
	private TipoConsoleRepository tiposConsoles;
	
	@Autowired
	private GeneroRepository generos;
	
	@Autowired
	private JogoService jogoService;

	@RequestMapping(value = "/novo")
	public ModelAndView novo(Jogo jogo) {
		ModelAndView mv = new ModelAndView("backoffice/produto/CadastroJogo");
		// Carrega as opções do formulário que são comuns a todos os forms
		mv.addObject("tiposConsoles", tiposConsoles.findAll());
		
		// Carrega as opções do formulário que são relacionadas aos jogos
		mv.addObject("idiomas", Idioma.values());
		mv.addObject("legendas", Legenda.values());
		mv.addObject("Resolucoes", Resolucao.values());
		mv.addObject("generos", generos.findAll());
		return mv;
	}

	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView novo(@Valid Jogo jogo, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(jogo);
		}

		try {
			jogoService.salvar(jogo);
		} catch (ListaDeImagensVaziasException | DescricaoDaImagemVaziaException | DescricaoDaImagemPassaLimiteCaractesException e) {
			result.rejectValue("imagens", e.getMessage(), e.getMessage());
			return novo(jogo);
		}

		attributes.addFlashAttribute("mensagem", "Jogo cadastrado com sucesso!");
		return new ModelAndView("redirect:/admin/produtos/jogos/novo");
	}
}
