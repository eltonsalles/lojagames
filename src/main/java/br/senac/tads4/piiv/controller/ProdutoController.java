package br.senac.tads4.piiv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.senac.tads4.piiv.model.enumerated.TipoMovimentacao;
import br.senac.tads4.piiv.model.enumerated.TipoProduto;
import br.senac.tads4.piiv.repository.HistoricoProdutoRepository;
import br.senac.tads4.piiv.repository.ProdutoRepository;
import br.senac.tads4.piiv.repository.filter.HistoricoProdutoFilter;
import br.senac.tads4.piiv.repository.filter.ProdutoFilter;

@Controller
@RequestMapping(value = "/admin/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private HistoricoProdutoRepository historicoProdutoRepository;
	
	@RequestMapping(value = "/pesquisar")
	public ModelAndView pesquisar(ProdutoFilter produtoFilter, BindingResult result) {
		ModelAndView mv = new ModelAndView("backoffice/produto/PesquisarEstoque");
		mv.addObject("tiposProduto", TipoProduto.values());
		mv.addObject("produtos", produtoRepository.filtrar(produtoFilter));
		return mv;
	}
	
	@RequestMapping(value = "/historico")
	public ModelAndView historico(HistoricoProdutoFilter historicoProdutoFilter, BindingResult result) {
		ModelAndView mv = new ModelAndView("backoffice/produto/HistoricoProduto");
		mv.addObject("tiposMovimentacao", TipoMovimentacao.values());
		mv.addObject("historicosProduto", historicoProdutoRepository.filtrar(historicoProdutoFilter));
		return mv;
	}
}
