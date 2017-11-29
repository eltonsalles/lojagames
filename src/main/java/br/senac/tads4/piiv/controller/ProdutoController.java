package br.senac.tads4.piiv.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senac.tads4.piiv.model.HistoricoProduto;
import br.senac.tads4.piiv.model.enumerated.TipoMovimentacao;
import br.senac.tads4.piiv.model.enumerated.TipoProduto;
import br.senac.tads4.piiv.repository.HistoricoProdutoRepository;
import br.senac.tads4.piiv.repository.ProdutoRepository;
import br.senac.tads4.piiv.repository.filter.HistoricoProdutoFilter;
import br.senac.tads4.piiv.repository.filter.ProdutoFilter;
import br.senac.tads4.piiv.service.HistoricoProdutoService;
import br.senac.tads4.piiv.service.exception.CodigoProdutoNaoExisteException;

@Controller
@RequestMapping(value = "/admin/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private HistoricoProdutoRepository historicoProdutoRepository;

	@Autowired
	private HistoricoProdutoService historicoProdutoService;

	/**
	 * Exibe o formulário para a pesquisa de produtos
	 * 
	 * @param produtoFilter
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/pesquisar")
	public ModelAndView pesquisar(ProdutoFilter produtoFilter, BindingResult result) {
		ModelAndView mv = new ModelAndView("backoffice/produto/PesquisarEstoque");
		mv.addObject("tiposProduto", TipoProduto.values());
		mv.addObject("produtos", produtoRepository.filtrar(produtoFilter));
		return mv;
	}

	/**
	 * Exibe o formulário para pesquisa de histórico de produtos
	 * 
	 * @param historicoProdutoFilter
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/historico")
	public ModelAndView historico(HistoricoProdutoFilter historicoProdutoFilter, BindingResult result) {
		ModelAndView mv = new ModelAndView("backoffice/produto/HistoricoProduto");
		mv.addObject("tiposMovimentacao", TipoMovimentacao.values());
		mv.addObject("historicosProduto", historicoProdutoRepository.filtrar(historicoProdutoFilter));
		return mv;
	}

	/**
	 * Exibe o formulário para cadastro de uma nova movimentação de produto
	 * 
	 * @param historicoProduto
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/movimentar")
	public ModelAndView movimentar(HistoricoProduto historicoProduto, BindingResult result) {
		ModelAndView mv = new ModelAndView("backoffice/produto/MovimentarProduto");
		mv.addObject("tiposMovimentacao", TipoMovimentacao.values());
		return mv;
	}

	/**
	 * Salva a movimentação de produto informada
	 * 
	 * @param historicoProduto
	 * @param result
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/movimentar", method = RequestMethod.POST)
	public ModelAndView movimentar(@Valid HistoricoProduto historicoProduto, BindingResult result,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return movimentar(historicoProduto, result);
		}

		try {
			historicoProdutoService.salvar(historicoProduto);
		} catch (CodigoProdutoNaoExisteException e) {
			result.rejectValue("produto", e.getMessage(), e.getMessage());
			return movimentar(historicoProduto, result);
		}

		attributes.addFlashAttribute("mensagem", "Movimentação cadastrada com sucesso!");
		return new ModelAndView("redirect:/admin/produtos/movimentar");
	}
}
