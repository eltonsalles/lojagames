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

import br.senac.tads4.piiv.model.Controle;
import br.senac.tads4.piiv.model.enumerated.Alimentacao;
import br.senac.tads4.piiv.model.enumerated.Conexao;
import br.senac.tads4.piiv.model.enumerated.Cor;
import br.senac.tads4.piiv.repository.ControleRepository;
import br.senac.tads4.piiv.repository.TipoConsoleRepository;
import br.senac.tads4.piiv.service.ControleService;
import br.senac.tads4.piiv.service.exception.DescricaoDaImagemPassaLimiteCaractesException;
import br.senac.tads4.piiv.service.exception.DescricaoDaImagemVaziaException;
import br.senac.tads4.piiv.service.exception.ListaDeImagensVaziasException;
import br.senac.tads4.piiv.service.exception.ProdutoComPedidoRealizadoException;

@Controller
@RequestMapping(value = "/admin/produtos/controles")
public class ControleController {

	@Autowired
	private TipoConsoleRepository tiposConsoles;
	
	@Autowired
	private ControleRepository controleRepository;
	
	@Autowired
	private ControleService controleService;
	
	@RequestMapping(value = "/novo")
	public ModelAndView novo(Controle controle) {
		ModelAndView mv = new ModelAndView("backoffice/produto/CadastroControle");
		// Carrega as opções do formulário que são comuns a todos os forms
		mv.addObject("tiposConsoles", tiposConsoles.findAll());
				
		// Carrega as opções do formulário que são relacionadas aos controles
		mv.addObject("conexoes", Conexao.values());
		mv.addObject("alimentacoes", Alimentacao.values());
		mv.addObject("cores", Cor.values());
		return mv;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView novo(@Valid Controle controle, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(controle);
		}
		
		try {
			controleService.salvar(controle);
		} catch (ListaDeImagensVaziasException | DescricaoDaImagemVaziaException | DescricaoDaImagemPassaLimiteCaractesException e) {
			result.rejectValue("imagens", e.getMessage(), e.getMessage());
			return novo(controle);
		}
		
		attributes.addFlashAttribute("mensagem", "Controle cadastrado com sucesso!");
		return new ModelAndView("redirect:/admin/produtos/controles/novo");
	}
	
	/**
	 * Método para exibir os dados do controle para alteração
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/alterar/{id}")
	public ModelAndView alterar(@PathVariable Long id) {
		Controle controle = controleRepository.findOne(id);

		ModelAndView mv = new ModelAndView("backoffice/produto/CadastroControle");
		mv.addObject("localImagem", "default");
		mv.addObject("tiposConsoles", tiposConsoles.findAll());
		mv.addObject("controle", controle);
		mv.addObject("conexoes", Conexao.values());
		mv.addObject("alimentacoes", Alimentacao.values());
		mv.addObject("cores", Cor.values());
		return mv;
	}
	
	/**
	 * Método usado caso existam erros no formulário de alteração
	 * 
	 * @param controle
	 * @return
	 */
	private ModelAndView alterar(Controle controle) {
		ModelAndView mv = new ModelAndView("backoffice/produto/CadastroControle");
		mv.addObject("localImagem", "default");
		mv.addObject("tiposConsoles", tiposConsoles.findAll());
		mv.addObject("controle", controle);
		mv.addObject("conexoes", Conexao.values());
		mv.addObject("alimentacoes", Alimentacao.values());
		mv.addObject("cores", Cor.values());
		return mv;
	}
	
	/**
	 * Método que recebe a submissão do formulário de alteração de controle
	 * 
	 * @param controle
	 * @param result
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/alterar", method = RequestMethod.POST)
	public ModelAndView alterar(@Valid Controle controle, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return this.alterar(controle);
		}

		try {
			controleService.alterar(controle);
		} catch (ListaDeImagensVaziasException | DescricaoDaImagemVaziaException | DescricaoDaImagemPassaLimiteCaractesException e) {
			result.rejectValue("imagens", e.getMessage(), e.getMessage());
			return novo(controle);
		}

		attributes.addFlashAttribute("mensagem", "Controle alterado com sucesso!");
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
			controleService.excluir(id);
		} catch (ProdutoComPedidoRealizadoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.ok().build();
	}
}
