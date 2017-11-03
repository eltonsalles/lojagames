package br.senac.tads4.piiv.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senac.tads4.piiv.dto.ItemProdutoDto;
import br.senac.tads4.piiv.model.Produto;
import br.senac.tads4.piiv.model.enumerated.Meses;
import br.senac.tads4.piiv.repository.ProdutoRepository;

@Controller
@RequestMapping("/carrinho-compra")
public class CarrinhoCompraController {

	private List<ItemProdutoDto> carrinho = new ArrayList<>();

	private String cep;

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
	public ModelAndView adicionarItemCarrinho(@RequestParam Long id, @RequestParam String cep,
			RedirectAttributes attributes) {
		for (ItemProdutoDto item : carrinho) {
			if (item.getId() == id) {
				return new ModelAndView("redirect:/carrinho-compra");
			}
		}

		if (!cep.isEmpty()) {
			this.cep = cep;
		}

		ItemProdutoDto itemProdutoDto = produtoRepository.itemProduto(id);
		this.carrinho.add(itemProdutoDto);

		attributes.addFlashAttribute("mensagem", "Produto adicionado ao carrinho com sucesso!");
		return new ModelAndView("redirect:/carrinho-compra");
	}

	@RequestMapping(value = "/remover-item-carrinho/{id}")
	public ModelAndView removerItemCarrinho(@PathVariable Long id, RedirectAttributes attributes) {
		for (ItemProdutoDto item : carrinho) {
			if (item.getId() == id) {
				carrinho.remove(item);
				attributes.addFlashAttribute("mensagem", "Produto removido do carrinho com sucesso!");
				return new ModelAndView("redirect:/carrinho-compra");
			}
		}

		attributes.addFlashAttribute("mensagem", "Não foi possível excluir o produto do carrinho!");
		return new ModelAndView("redirect:/carrinho-compra");
	}

	@RequestMapping(value = "/alterar-quantidade", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> alterarQuantidadeDoItem(@RequestBody ItemProdutoDto itemProdutoDto,
			BindingResult result) {
		Produto produto = produtoRepository.findOne(itemProdutoDto.getId());

		if (itemProdutoDto.getQtde() > produto.getEstoque()) {
			return ResponseEntity.badRequest().body(produto.getEstoque());
		}

		for (ItemProdutoDto item : carrinho) {
			if (item.getId() == itemProdutoDto.getId()) {
				item.setQtde(itemProdutoDto.getQtde());
				break;
			}
		}

		return ResponseEntity.ok("Ok");
	}

	@RequestMapping(value = "/finalizar-compra", method = RequestMethod.POST)
	public ModelAndView finalizarCompra(@RequestParam String cep, @RequestParam(name = "valor-frete") String valorFrete,
			RedirectAttributes attributes) {
		ModelAndView mv = new ModelAndView("site/carrinho/FinalizarCompra");

		if (!cep.isEmpty()) {
			this.cep = cep;
		}

		Calendar hoje = Calendar.getInstance();
		
		mv.addObject("ano", hoje.get(Calendar.YEAR));
		mv.addObject("meses", Meses.values());
		mv.addObject("valorFrete", valorFrete);
		return mv;
	}

	public List<ItemProdutoDto> getCarrinho() {
		return this.carrinho;
	}

	public String getCep() {
		return this.cep;
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
