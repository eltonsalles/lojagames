package br.senac.tads4.piiv.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senac.tads4.piiv.model.Produto;
import br.senac.tads4.piiv.service.ProdutoService;

@Controller
@RequestMapping(value = "/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtos;
	
	@RequestMapping(value = "/novo")
	public ModelAndView novo(Produto produto) {
		ModelAndView mv = new ModelAndView("backoffice/produto/CadastroProduto");
		return mv;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView novo(@Valid Produto produto, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(produto);
		}
		
		try {
			produtos.salvar(produto);
		} catch (Exception e) { // Trocar por uma exceção mais especifica
			// result.rejectValue();
			return novo(produto);
		}
		
		attributes.addFlashAttribute("message", "Produto cadastrado com sucesso!");
		return new ModelAndView("redirect:/produtos/novo");
	}
}
