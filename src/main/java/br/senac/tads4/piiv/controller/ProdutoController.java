package br.senac.tads4.piiv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.senac.tads4.piiv.model.enumerated.TipoProduto;
import br.senac.tads4.piiv.repository.ProdutoRepository;

@Controller
@RequestMapping(value = "/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@RequestMapping(value = "/pesquisar")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView("backoffice/produto/PesquisarEstoque");
		mv.addObject("tiposProduto", TipoProduto.values());
		mv.addObject("produtos", produtoRepository.findAll());
		return mv;
	}
}
