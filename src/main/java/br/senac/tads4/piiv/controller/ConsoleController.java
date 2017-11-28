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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senac.tads4.piiv.model.Console;
import br.senac.tads4.piiv.model.enumerated.Capacidade;
import br.senac.tads4.piiv.model.enumerated.Cor;
import br.senac.tads4.piiv.model.enumerated.Midia;
import br.senac.tads4.piiv.model.enumerated.Resolucao;
import br.senac.tads4.piiv.model.enumerated.Voltagem;
import br.senac.tads4.piiv.repository.ConsoleRepository;
import br.senac.tads4.piiv.repository.TipoConsoleRepository;
import br.senac.tads4.piiv.service.ConsoleService;
import br.senac.tads4.piiv.service.exception.DescricaoDaImagemPassaLimiteCaractesException;
import br.senac.tads4.piiv.service.exception.DescricaoDaImagemVaziaException;
import br.senac.tads4.piiv.service.exception.ListaDeImagensVaziasException;
import br.senac.tads4.piiv.service.exception.ProdutoComPedidoRealizadoExcepetion;

@Controller
@RequestMapping(value = "/admin/produtos/consoles")
public class ConsoleController {

	@Autowired
	private TipoConsoleRepository tiposConsoles;
	
	@Autowired
	private ConsoleRepository consoleRepository;
	
	@Autowired
	private ConsoleService consoleService;

	/**
	 * Exibe o formulário para cadastrar um novo console
	 * 
	 * @param console
	 * @return
	 */
	@RequestMapping(value = "/novo")
	public ModelAndView novo(Console console) {
		ModelAndView mv = new ModelAndView("backoffice/produto/CadastroConsole");
		// Carrega as opções do formulário que são comuns a todos os forms
		mv.addObject("tiposConsoles", tiposConsoles.findAll());
		
		// Carrega as opções do formulário que são relacionadas aos controles
		mv.addObject("capacidades", Capacidade.values());
		mv.addObject("voltagens", Voltagem.values());
		mv.addObject("cores", Cor.values());
		mv.addObject("resolucoes", Resolucao.values());
		mv.addObject("midias", Midia.values());
		return mv;
	}

	/**
	 * Método que recebe a submição post para salvar um novo console
	 * 
	 * @param console
	 * @param result
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView novo(@Valid Console console, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(console);
		}

		try {
			consoleService.salvar(console);
		} catch (ListaDeImagensVaziasException | DescricaoDaImagemVaziaException | DescricaoDaImagemPassaLimiteCaractesException e) {
			result.rejectValue("imagens", e.getMessage(), e.getMessage());
			return novo(console);
		}

		attributes.addFlashAttribute("mensagem", "Console cadastrado com sucesso!");
		return new ModelAndView("redirect:/admin/produtos/consoles/novo");
	}
	
	/**
	 * Método para exibir os dados do console para alteração
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/alterar/{id}")
	public ModelAndView alterar(@PathVariable Long id) {
		Console console = consoleRepository.findOne(id);

		ModelAndView mv = new ModelAndView("backoffice/produto/CadastroConsole");
		mv.addObject("localImagem", "default");
		mv.addObject("tiposConsoles", tiposConsoles.findAll());
		mv.addObject("console", console);
		mv.addObject("capacidades", Capacidade.values());
		mv.addObject("voltagens", Voltagem.values());
		mv.addObject("cores", Cor.values());
		mv.addObject("resolucoes", Resolucao.values());
		mv.addObject("midias", Midia.values());
		return mv;
	}
	
	/**
	 * Método usado caso existam erros no formulário de alteração
	 * 
	 * @param console
	 * @return
	 */
	private ModelAndView alterar(Console console) {
		ModelAndView mv = new ModelAndView("backoffice/produto/CadastroConsole");
		mv.addObject("localImagem", "default");
		mv.addObject("tiposConsoles", tiposConsoles.findAll());
		mv.addObject("console", console);
		mv.addObject("capacidades", Capacidade.values());
		mv.addObject("voltagens", Voltagem.values());
		mv.addObject("cores", Cor.values());
		mv.addObject("resolucoes", Resolucao.values());
		mv.addObject("midias", Midia.values());
		return mv;
	}
	
	/**
	 * Método que recebe a submissão do formulário de alteração de console
	 * 
	 * @param console
	 * @param result
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/alterar", method = RequestMethod.POST)
	public ModelAndView alterar(@Valid Console console, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return this.alterar(console);
		}

		try {
			consoleService.alterar(console);
		} catch (ListaDeImagensVaziasException | DescricaoDaImagemVaziaException | DescricaoDaImagemPassaLimiteCaractesException e) {
			result.rejectValue("imagens", e.getMessage(), e.getMessage());
			return novo(console);
		}

		attributes.addFlashAttribute("mensagem", "Console alterado com sucesso!");
		return new ModelAndView("redirect:/admin/produtos/pesquisar");
	}
	
	/**
	 * Método para excluir
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/excluir/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable Long id) {
		try {
			consoleService.excluir(id);
		} catch (ProdutoComPedidoRealizadoExcepetion e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.ok().build();
	}
}
