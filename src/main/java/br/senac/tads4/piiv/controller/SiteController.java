package br.senac.tads4.piiv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.senac.tads4.piiv.model.Genero;
import br.senac.tads4.piiv.model.TipoConsole;
import br.senac.tads4.piiv.model.enumerated.TipoProduto;
import br.senac.tads4.piiv.repository.GeneroRepository;
import br.senac.tads4.piiv.repository.JogoRepository;
import br.senac.tads4.piiv.repository.ProdutoRepository;
import br.senac.tads4.piiv.repository.TipoConsoleRepository;
import br.senac.tads4.piiv.service.ProdutoService;

@Controller
@RequestMapping("/")
public class SiteController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private JogoRepository jogoRepository;

	@Autowired
	private GeneroRepository generoRepository;

	@Autowired
	private TipoConsoleRepository tipoConsoleRepository;

	@Autowired
	private ProdutoService produtoService;

	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("site/Index");

		// Traz os 16 primeiros produtos
		Pageable limit = new PageRequest(0, 16);
		mv.addObject("produtos", produtoRepository.findAll(limit));
		percentualDesconto(mv);
		return mv;
	}

	@RequestMapping("/detalhes-do-produto/{id}")
	public ModelAndView detalheProduto(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("site/produto/DetalhesProduto");

		tiposProduto(mv);
		percentualDesconto(mv);
		mv.addObject("produto", produtoRepository.findOne(id));
		mv.addObject("maximoParcelas", produtoService.getMaximoParcelas());
		return mv;
	}

	@RequestMapping("/lista-produto/{id}")
	public ModelAndView listaProdutoConsole(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("site/produto/ListaProduto");

		tiposProduto(mv);
		percentualDesconto(mv);
		generos(mv);
		menu(mv);
		mv.addObject("produtos", produtoRepository.findByTipoConsole(idTipoConsole(id)));
		mv.addObject("idConsole", id);
		return mv;
	}

	@RequestMapping("/lista-produto/{idConsole}/{tipoProduto}")
	public ModelAndView listaProdutoCategoria(@PathVariable Long idConsole, @PathVariable TipoProduto tipoProduto) {
		ModelAndView mv = new ModelAndView("site/produto/ListaProduto");

		tiposProduto(mv);
		percentualDesconto(mv);
		generos(mv);
		menu(mv);
		mv.addObject("produtos",
				produtoRepository.findByTipoConsoleAndTipoProduto(idTipoConsole(idConsole), tipoProduto));
		mv.addObject("idConsole", idConsole);
		mv.addObject(idTipoConsole(idConsole));
		return mv;
	}

	@RequestMapping("/lista-produto/{idConsole}/{tipoProduto}/{idGenero}")
	public ModelAndView listaProdutoCategoriaGenero(@PathVariable Long idConsole, @PathVariable TipoProduto tipoProduto,
			@PathVariable Long idGenero) {
		ModelAndView mv = new ModelAndView("site/produto/ListaProduto");

		tiposProduto(mv);
		percentualDesconto(mv);
		generos(mv);
		menu(mv);
		mv.addObject("idConsole", idConsole);
		mv.addObject(tipoProduto);
		Genero genero = new Genero();
		genero.setId(idGenero);
		mv.addObject("produtos", jogoRepository.findByGeneroAndTipoConsole(idGenero, idConsole));
		return mv;
	}

	private void menu(ModelAndView mv) {
		mv.addObject("tiposConsole", tipoConsoleRepository.findAll());
	}

	private void percentualDesconto(ModelAndView mv) {
		mv.addObject("percentualDesconto", produtoService.getPercentualDesconto());
	}

	private void tiposProduto(ModelAndView mv) {
		mv.addObject("tiposProduto", TipoProduto.values());
	}

	private void generos(ModelAndView mv) {
		mv.addObject("generos", generoRepository.findAll());
	}

	private TipoConsole idTipoConsole(Long idConsole) {
		TipoConsole tipoConsole = new TipoConsole();
		tipoConsole.setId(idConsole);
		return tipoConsole;
	}
}
