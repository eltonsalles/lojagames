package br.senac.tads4.piiv.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.senac.tads4.piiv.model.Genero;
import br.senac.tads4.piiv.model.Jogo;
import br.senac.tads4.piiv.model.Produto;
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
		menu(mv);
		return mv;
	}

	@RequestMapping("/detalhes-do-produto/{id}")
	public ModelAndView detalheProduto(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("site/produto/DetalhesProduto");
		tiposProduto(mv);
		percentualDesconto(mv);
		menu(mv);
		
		Pageable limit = new PageRequest(0, 4);
		Produto produto = produtoRepository.findOne(id);
		
		List<?> listaJogo = new ArrayList<>();
		if (produto.getTipoProduto().name().equalsIgnoreCase("jogo")) {
			Jogo jogo = (Jogo) produto;
			Genero genero = jogo.getGenero();
			listaJogo = jogoRepository.findByGenero(genero, limit);
		} else {
			listaJogo = produtoRepository.findByTipoConsole(produto.getTipoConsole(), limit);
		}
		
		mv.addObject("produtos", listaJogo);
		mv.addObject("produto", produto);
		mv.addObject("maximoParcelas", produtoService.getMaximoParcelas());
		return mv;
	}

	@RequestMapping("/lista-produto/{id}")
	public ModelAndView listaProdutoConsole(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("site/produto/ListaProduto");
		tiposProduto(mv);
		percentualDesconto(mv);
		menu(mv);
		
		mv.addObject("produtos", produtoRepository.findByTipoConsole(idTipoConsole(id)));
		mv.addObject("idConsole", id);
		return mv;
	}
	
	@RequestMapping("/lista-produto/{id}/{ordenar}")
	public ModelAndView listaProdutoConsole(@PathVariable Long id, @PathVariable String ordenar) {
		ModelAndView mv = new ModelAndView("site/produto/ListaProduto");
		tiposProduto(mv);
		percentualDesconto(mv);
		menu(mv);
		
		mv.addObject("produtos", this.produtos(this.idTipoConsole(id), ordenar));
		mv.addObject("idConsole", id);
		return mv;
	}

	@RequestMapping("/lista-produtos/{idConsole}/{tipoProduto}")
	public ModelAndView listaProdutoCategoria(@PathVariable Long idConsole, @PathVariable TipoProduto tipoProduto) {
		ModelAndView mv = new ModelAndView("site/produto/ListaProduto");
		tiposProduto(mv);
		percentualDesconto(mv);
		menu(mv);
		
		if (tipoProduto.equals(TipoProduto.JOGO)) {
			generos(mv);
		}
		
		mv.addObject("produtos", produtoRepository.findByTipoConsoleAndTipoProduto(idTipoConsole(idConsole), tipoProduto));
		mv.addObject("idConsole", idConsole);
		mv.addObject(idTipoConsole(idConsole));
		return mv;
	}

	@RequestMapping("/lista-produtos/{idConsole}/{tipoProduto}/{ordenar}")
	public ModelAndView listaProdutoCategoria(@PathVariable Long idConsole, @PathVariable TipoProduto tipoProduto, @PathVariable String ordenar) {
		ModelAndView mv = new ModelAndView("site/produto/ListaProduto");
		tiposProduto(mv);
		percentualDesconto(mv);
		menu(mv);
		
		if (tipoProduto.equals(TipoProduto.JOGO)) {
			generos(mv);
		}
		
		mv.addObject("produtos", this.produtos(this.idTipoConsole(idConsole), tipoProduto, ordenar));
		mv.addObject("idConsole", idConsole);
		mv.addObject(idTipoConsole(idConsole));
		return mv;
	}

	@RequestMapping("/lista-produto/genero/{idConsole}/{tipoProduto}/{idGenero}")
	public ModelAndView listaProdutoCategoriaGenero(@PathVariable Long idConsole, @PathVariable TipoProduto tipoProduto, @PathVariable Long idGenero) {
		ModelAndView mv = new ModelAndView("site/produto/ListaProduto");
		tiposProduto(mv);
		percentualDesconto(mv);
		generos(mv);
		menu(mv);
		
		Genero genero = new Genero();
		genero.setId(idGenero);
		
		mv.addObject("idConsole", idConsole);
		mv.addObject(tipoProduto);
		mv.addObject("produtos", jogoRepository.findByGeneroAndTipoConsole(idGenero, idConsole));
		return mv;
	}

	@RequestMapping("/lista-produto/genero/{idConsole}/{tipoProduto}/{idGenero}/{ordenar}")
	public ModelAndView listaProdutoCategoriaGenero(@PathVariable Long idConsole, @PathVariable TipoProduto tipoProduto, @PathVariable Long idGenero, @PathVariable String ordenar) {
		ModelAndView mv = new ModelAndView("site/produto/ListaProduto");
		tiposProduto(mv);
		percentualDesconto(mv);
		generos(mv);
		menu(mv);
		
		Genero genero = new Genero();
		genero.setId(idGenero);
		
		mv.addObject("idConsole", idConsole);
		mv.addObject(tipoProduto);
		mv.addObject("produtos", this.jogos(idGenero, idConsole, ordenar));
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
	
	private List<Produto> produtos(TipoConsole tipoConsole, String ordenar) {
		List<Produto> produtos;
		switch (ordenar.toLowerCase()) {
		case "ordem-alfabetica":
			produtos = produtoRepository.findByTipoConsoleOrderByNome(tipoConsole);
			break;

		case "menor-preco":
			produtos = produtoRepository.findByTipoConsoleOrderByPrecoVenda(tipoConsole);
			break;

		case "maior-preco":
			produtos = produtoRepository.findByTipoConsoleOrderByPrecoVendaDesc(tipoConsole);
			break;

		default:
			produtos = produtoRepository.findByTipoConsole(tipoConsole);
		}

		return produtos;
	}

	private List<Produto> produtos(TipoConsole tipoConsole, TipoProduto tipoProduto, String ordenar) {
		List<Produto> produtos;
		switch (ordenar.toLowerCase()) {
		case "ordem-alfabetica":
			produtos = produtoRepository.findByTipoConsoleAndTipoProdutoOrderByNome(tipoConsole, tipoProduto);
			break;

		case "menor-preco":
			produtos = produtoRepository.findByTipoConsoleAndTipoProdutoOrderByPrecoVenda(tipoConsole, tipoProduto);
			break;

		case "maior-preco":
			produtos = produtoRepository.findByTipoConsoleAndTipoProdutoOrderByPrecoVendaDesc(tipoConsole, tipoProduto);
			break;

		default:
			produtos = produtoRepository.findByTipoConsoleAndTipoProduto(tipoConsole, tipoProduto);
		}

		return produtos;
	}

	private List<Jogo> jogos(Long idGenero, Long idConsole, String ordenar) {
		List<Jogo> jogos;
		switch (ordenar.toLowerCase()) {
		case "ordem-alfabetica":
			jogos = jogoRepository.findByGeneroAndTipoConsoleOrderByNome(idGenero, idConsole);
			break;

		case "menor-preco":
			jogos = jogoRepository.findByGeneroAndTipoConsoleOrderByPrecoVenda(idGenero, idConsole);
			break;

		case "maior-preco":
			jogos = jogoRepository.findByGeneroAndTipoConsoleOrderByPrecoVendaDesc(idGenero, idConsole);
			break;

		default:
			jogos = jogoRepository.findByGeneroAndTipoConsole(idGenero, idConsole);
		}

		return jogos;
	}
}
