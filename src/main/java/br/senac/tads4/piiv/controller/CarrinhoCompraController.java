package br.senac.tads4.piiv.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senac.tads4.piiv.dto.ItemProdutoDto;
import br.senac.tads4.piiv.repository.ProdutoRepository;

@Controller
@RequestMapping("/carrinho-compra")
public class CarrinhoCompraController {
	
	private List<ItemProdutoDto> carrinho = new ArrayList<>();
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@RequestMapping
	public ModelAndView carrinhoCompra() {
		return new ModelAndView("site/carrinho/CarrinhoCompras");
	}
	
	@RequestMapping(value = "/adicionar-item-carrinho")
	public ModelAndView itemCarrinho() {
		return new ModelAndView("redirect:/carrinho-compra");
	}
	
	@RequestMapping(value = "/adicionar-item-carrinho", method = RequestMethod.POST)
	public ModelAndView itemCarrinho(@RequestParam Long idProduto, @RequestParam String cep, RedirectAttributes attributes) {
		ItemProdutoDto itemProdutoDto = produtoRepository.itemProduto(idProduto);
		carrinho.add(itemProdutoDto);
		
		attributes.addFlashAttribute("mensagem", "Produto adicionado ao carrinho com sucesso!");
		return new ModelAndView("redirect:/carrinho-compra");
	}
	
	public List<ItemProdutoDto> getCarrinho() {
		return this.carrinho;
	}
	
	public BigDecimal getSubTotalCarrinho() {
		BigDecimal preco = BigDecimal.ZERO;
		BigDecimal subTotal = BigDecimal.ZERO;
		
		for (ItemProdutoDto p : carrinho) {
			preco = p.getPreco().multiply(new BigDecimal(p.getQtde()));
			subTotal = subTotal.add(preco);
		}
		
		return subTotal;
	}
}
