package br.senac.tads4.piiv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.senac.tads4.piiv.model.enumerated.TipoProduto;
import br.senac.tads4.piiv.repository.ProdutoRepository;
import br.senac.tads4.piiv.service.ProdutoService;

@Controller
@RequestMapping("/")
public class SiteController {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ProdutoService produtoService;

	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("site/Index");

		// Traz os 16 primeiros produtos
		Pageable limit = new PageRequest(0, 16);
		mv.addObject("produtos", produtoRepository.findAll(limit));
		return mv;
	}

	@RequestMapping("/detalhes-do-produto/{id}")
	public ModelAndView detalheProduto(@PathVariable long id) {
		ModelAndView mv = new ModelAndView("site/produto/DetalhesProduto");

		mv.addObject("produto", produtoRepository.findOne(id));
		mv.addObject("tiposProduto", TipoProduto.values());
		mv.addObject("percentualDesconto", produtoService.getPercentualDesconto());
		mv.addObject("maximoParcelas", produtoService.getMaximoParcelas());
		return mv;
	}
}
