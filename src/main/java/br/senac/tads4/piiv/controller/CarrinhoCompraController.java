package br.senac.tads4.piiv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.senac.tads4.piiv.dto.ItemProdutoDto;
import br.senac.tads4.piiv.repository.ProdutoRepository;

@Controller
@RequestMapping("/carrinho-compra")
public class CarrinhoCompraController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@RequestMapping("/")
	public ModelAndView carrinhodeCompra(ItemProdutoDto itemProdutoDto) {
		ModelAndView mv = new ModelAndView("site/Compra/CarrinhoCompras");
		return mv;
	}
	
	//alterar para método POST.
	@RequestMapping(value = "/adicionar-item-carrinho/{id}")
	public ModelAndView itemCarrinho(@PathVariable Long id) {
		ItemProdutoDto itemProdutoDto = produtoRepository.itemProduto(id);
		return carrinhodeCompra(itemProdutoDto);
	}
}
