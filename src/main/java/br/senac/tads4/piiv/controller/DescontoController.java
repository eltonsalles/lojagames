package br.senac.tads4.piiv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senac.tads4.piiv.model.Produto;
import br.senac.tads4.piiv.model.enumerated.TipoProduto;
import br.senac.tads4.piiv.repository.ProdutoRepository;
import br.senac.tads4.piiv.repository.TipoConsoleRepository;
import br.senac.tads4.piiv.repository.filter.DescontoFilter;
import br.senac.tads4.piiv.service.ProdutoService;
import br.senac.tads4.piiv.service.exception.DescontoAteAntesDeHojeException;
import br.senac.tads4.piiv.service.exception.PercentualDescontoMaiorQueCemException;

@Controller
@RequestMapping("/admin/descontos")
@SessionScope
public class DescontoController {
	
	private List<Produto> listaProdutosDesconto;

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private TipoConsoleRepository tipoConsoleRepository;
	
	/**
	 * Exibe os produtos que receberão descontos conforme o filtro
	 * 
	 * @param descontoFilter
	 * @param result
	 * @return
	 */
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(DescontoFilter descontoFilter, BindingResult result) {
		ModelAndView mv = new ModelAndView("backoffice/desconto/Desconto");
		
		this.listaProdutosDesconto = produtoRepository.produtosDesconto(descontoFilter);
		
		mv.addObject("tiposProduto", TipoProduto.values());
		mv.addObject("tiposConsole", tipoConsoleRepository.findAll());
		mv.addObject("produtos", this.listaProdutosDesconto);
		
		return mv;
	}
	
	/**
	 * Salva os descontos conforme o filtro de produtos
	 * 
	 * @param descontoFilter
	 * @param result
	 * @return
	 */
	@RequestMapping("/salvar")
	public ModelAndView salvarDesconto(DescontoFilter descontoFilter, BindingResult result, RedirectAttributes attributes) {
		try {
			produtoService.salvarDescontos(this.listaProdutosDesconto, descontoFilter.getPercentualDesconto(), descontoFilter.getDescontoAte());
		} catch (PercentualDescontoMaiorQueCemException e) {
			result.rejectValue("percentualDesconto", e.getMessage(), e.getMessage());
			return pesquisar(descontoFilter, result);
		} catch (DescontoAteAntesDeHojeException e) {
			result.rejectValue("descontoAte", e.getMessage(), e.getMessage());
			return pesquisar(descontoFilter, result);
		}
		
		attributes.addFlashAttribute("mensagem", "Descontos aplicados com sucesso!");
		return new ModelAndView("redirect:/admin/descontos/pesquisar");
	}
}
