package br.senac.tads4.piiv.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senac.tads4.piiv.dto.ItemProdutoDto;
import br.senac.tads4.piiv.mail.Mailer;
import br.senac.tads4.piiv.model.Cliente;
import br.senac.tads4.piiv.model.Endereco;
import br.senac.tads4.piiv.model.EnderecoEntrega;
import br.senac.tads4.piiv.model.Pedido;
import br.senac.tads4.piiv.model.Produto;
import br.senac.tads4.piiv.model.enumerated.Meses;
import br.senac.tads4.piiv.model.enumerated.TipoPagamento;
import br.senac.tads4.piiv.repository.ClienteRepository;
import br.senac.tads4.piiv.repository.ProdutoRepository;
import br.senac.tads4.piiv.security.UsuarioSistema;
import br.senac.tads4.piiv.service.ClienteService;
import br.senac.tads4.piiv.service.PedidoService;
import br.senac.tads4.piiv.service.ProdutoService;

@Controller
@RequestMapping("/carrinho-compra")
@SessionScope
public class CarrinhoCompraController {

	private List<ItemProdutoDto> carrinho = new ArrayList<>();

	private String cep;

	private BigDecimal valorFrete;

	private Integer diasEntrega;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ClienteService clienteService;

	@Autowired
	private Mailer mailer;

	/**
	 * Exibe o carrinho de compra
	 * 
	 * @return
	 */
	@RequestMapping
	public ModelAndView carrinhoCompra() {
		return new ModelAndView("site/carrinho/CarrinhoCompras");
	}

	/**
	 * Exibe o carrinho de compra
	 * 
	 * @return
	 */
	@RequestMapping(value = "/adicionar-item-carrinho")
	public ModelAndView itemCarrinho() {
		return new ModelAndView("redirect:/carrinho-compra");
	}

	/**
	 * Adiciona um item ao carrinho. Se o item clicado já estiver no carrinho então a sua quantidade é alterada
	 * 
	 * @param id
	 * @param cep
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/adicionar-item-carrinho", method = RequestMethod.POST)
	public ModelAndView adicionarItemCarrinho(@RequestParam Long id, @RequestParam String cep,
			RedirectAttributes attributes) {
		for (ItemProdutoDto item : carrinho) {
			if (item.getId() == id) {
				Integer novaQtde = item.getQtde() + 1;

				Produto produto = produtoRepository.findOne(id);
				if (novaQtde <= produto.getEstoque()) {
					item.setQtde(novaQtde);
				}

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

	/**
	 * Remove o item do carrinho de compra
	 * 
	 * @param id
	 * @param attributes
	 * @return
	 */
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

	/**
	 * O cliente pode alterar a quantidade do item no carrinho de compra sempre respeitando o estoque do produto
	 * 
	 * @param itemProdutoDto
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/alterar-quantidade", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> alterarQuantidadeDoItem(@RequestBody ItemProdutoDto itemProdutoDto, BindingResult result) {
		Produto produto = produtoRepository.findOne(itemProdutoDto.getId());

		if (itemProdutoDto.getQtde() > produto.getEstoque()) {
			// Insere a quantidade em estoque do produto no carrinho.
			// Esse for é necessário para quando o cliente digitar uma quantidade maior que o estoque
			for (ItemProdutoDto item : carrinho) {
				if (item.getId() == itemProdutoDto.getId()) {
					item.setQtde(produto.getEstoque());
					break;
				}
			}
			
			return ResponseEntity.badRequest().body(produto.getEstoque());
		}

		// Insere a quantidade informada pelo cliente no carrinho
		// Esse for é necessário para quando o cliente clica nas setas para alterar a quantidade de compra do produto
		for (ItemProdutoDto item : carrinho) {
			if (item.getId() == itemProdutoDto.getId()) {
				item.setQtde(itemProdutoDto.getQtde());
				break;
			}
		}

		return ResponseEntity.ok("Ok");
	}
	
	/**
	 * O sistema sempre carrega o endereço principal, mas o cliente pode alterar o endereço de entrega
	 * 
	 * @param endereco
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/cadastrar/endereco-adicional/entrega", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> cadastrarEnderecoEntrega(@RequestBody @Valid Endereco endereco, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body("Verifique o preenchimento de todos os campos obrigatórios");
		}
		
		try {
			clienteService.salvarEnderecoAdicional(endereco);
			return ResponseEntity.ok("Ok");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Não foi possível salvar o endereço informado");
		}
	}

	/**
	 * Exibe a tela para finalizar a compra. Esse método é acionado caso o cliente clique em finalizar compra, mas não esteja logado.
	 * Depois do login se existir itens no carrinho o cliente vai pra tela de finalizar compra
	 * 
	 * @param attributes
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/finalizar-compra")
	public ModelAndView finalizarCompra(RedirectAttributes attributes, HttpSession session,
			HttpServletRequest request) {

		session.setAttribute("carrinhoCompras", this.getCarrinho().size());

		Cliente cliente = this.getUsuarioLogado(request);

		if (cliente == null) {
			return new ModelAndView("redirect:/login/site");
		}

		if (this.getCarrinho().size() <= 0) {
			return new ModelAndView("redirect:/carrinho-compra");
		}

		ModelAndView mv = new ModelAndView("site/carrinho/FinalizarCompra");

		// Informações para o formulário
		Pedido pedido = new Pedido();
		pedido.setValorFrete(this.valorFrete);
		pedido.setDiasEntrega(this.diasEntrega);
		pedido.setValorSubTotal(this.getSubTotalCarrinho());
		pedido.setValorTotal(this.getSubTotalCarrinho()); // No front tem a soma do frete

		pedido.setCliente(clienteRepository.findOne(cliente.getId()));

		Calendar hoje = Calendar.getInstance();

		mv.addObject("ano", hoje.get(Calendar.YEAR));
		mv.addObject("meses", Meses.values());
		mv.addObject("tiposPagamento", TipoPagamento.values());
		mv.addObject("maximoParcelas", produtoService.getMaximoParcelas());
		mv.addObject("pedido", pedido);

		Map<String, ?> mensagem = attributes.getFlashAttributes();
		if (mensagem.containsKey("msgErroRealizarPedido")) {
			mv.addObject("msgErroRealizarPedido", mensagem.get("msgErroRealizarPedido"));
		}

		return mv;
	}

	/**
	 * Exibe a tela para finalizar o pedido
	 * 
	 * @param cep
	 * @param valorFrete
	 * @param diasEntrega
	 * @param attributes
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/finalizar-compra", method = RequestMethod.POST)
	public ModelAndView finalizarCompra(@RequestParam String cep,
			@RequestParam(name = "valor-frete") BigDecimal valorFrete,
			@RequestParam(name = "dias-entrega") Integer diasEntrega, RedirectAttributes attributes,
			HttpSession session, HttpServletRequest request) {

		session.setAttribute("carrinhoCompras", this.getCarrinho().size());

		Cliente cliente = this.getUsuarioLogado(request);

		if (cliente == null) {
			this.cep = cep;
			this.valorFrete = valorFrete;
			this.diasEntrega = diasEntrega;

			return new ModelAndView("redirect:/login/site");
		}

		if (this.getCarrinho().size() <= 0) {
			return new ModelAndView("redirect:/carrinho-compra");
		}

		ModelAndView mv = new ModelAndView("site/carrinho/FinalizarCompra");

		// Informações para o formulário
		Pedido pedido = new Pedido();
		pedido.setValorFrete(valorFrete);
		pedido.setDiasEntrega(diasEntrega);
		pedido.setValorSubTotal(this.getSubTotalCarrinho());
		pedido.setValorTotal(this.getSubTotalCarrinho()); // No front tem a soma do frete

		pedido.setCliente(clienteRepository.findOne(cliente.getId()));

		Calendar hoje = Calendar.getInstance();

		mv.addObject("ano", hoje.get(Calendar.YEAR));
		mv.addObject("meses", Meses.values());
		mv.addObject("tiposPagamento", TipoPagamento.values());
		mv.addObject("maximoParcelas", produtoService.getMaximoParcelas());
		mv.addObject("pedido", pedido);

		Map<String, ?> mensagem = attributes.getFlashAttributes();
		if (mensagem.containsKey("msgErroRealizarPedido")) {
			mv.addObject("msgErroRealizarPedido", mensagem.get("msgErroRealizarPedido"));
		}

		return mv;
	}

	/**
	 * Salva o pedido do cliente
	 * 
	 * @param pedido
	 * @param result
	 * @param attributes
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/realizar-pedido", method = RequestMethod.POST)
	public ModelAndView realizarPedido(@Valid Pedido pedido, BindingResult result, RedirectAttributes attributes, HttpSession session, HttpServletRequest request) {
		// Verifica erros de preenchimento no pedido
		if (result.hasErrors()) {
			attributes.addFlashAttribute("msgErroRealizarPedido", "Informe os dados de entrega e/ou pagamento");

			return finalizarCompra(pedido.getCliente().getEnderecos().get(0).getCep(), pedido.getValorFrete(), pedido.getDiasEntrega(), attributes, session, request);
		}

		// Se o pagamento é com cartão de crédito então valida as informações pertinentes de pagamento
		if (pedido.getTipoPagamento().toString().equalsIgnoreCase("CARTAO_CREDITO")) {
			if (pedido.getParcelas() == null || StringUtils.isEmpty(pedido.getDadosPagamento().getNomeTitular())
					|| StringUtils.isEmpty(pedido.getDadosPagamento().getCartao())
					|| StringUtils.isEmpty(pedido.getDadosPagamento().getMesVencimento())
					|| StringUtils.isEmpty(pedido.getDadosPagamento().getAnoVencimento())
					|| StringUtils.isEmpty(pedido.getDadosPagamento().getCodigoSeguranca())) {
				attributes.addFlashAttribute("msgErroRealizarPedido", "Informe os dados para o pagamento");

				return finalizarCompra(pedido.getCliente().getEnderecos().get(0).getCep(), pedido.getValorFrete(), pedido.getDiasEntrega(), attributes, session, request);
			}
		}

		// Verificação para evitar pedidos sem produtos 
		if (this.getCarrinho().size() <= 0) {
			return new ModelAndView("redirect:/carrinho-compra");
		}

		// Verifica se existe um cliente logado
		Cliente cliente = this.getUsuarioLogado(request);
		if (cliente == null) {
			return new ModelAndView("redirect:/login/site");
		}

		// Monta o endereço de entrega
		EnderecoEntrega enderecoEntrega = new EnderecoEntrega();
		enderecoEntrega.setCep(pedido.getCliente().getEnderecos().get(0).getCep());
		enderecoEntrega.setLogradouro(pedido.getCliente().getEnderecos().get(0).getLogradouro());
		enderecoEntrega.setNumero(pedido.getCliente().getEnderecos().get(0).getNumero());
		enderecoEntrega.setBairro(pedido.getCliente().getEnderecos().get(0).getBairro());
		enderecoEntrega.setCidade(pedido.getCliente().getEnderecos().get(0).getCidade());
		enderecoEntrega.setUf(pedido.getCliente().getEnderecos().get(0).getUf());
		enderecoEntrega.setReferencia(pedido.getCliente().getEnderecos().get(0).getReferencia());
		if (!StringUtils.isEmpty(pedido.getCliente().getEnderecos().get(0).getComplemento())) {
			enderecoEntrega.setComplemento(pedido.getCliente().getEnderecos().get(0).getComplemento());
		}

		pedido.setEnderecoEntrega(enderecoEntrega);
		pedido.setCliente(clienteRepository.findOne(cliente.getId()));
		
		Long id = pedidoService.salvar(pedido, this.getCarrinho());
		
		// Envia o e-mail com o pedido para o cliente
		this.mailer.enviarPedido(pedido);

		// Limpa as variáveis do carrinho
		this.carrinho.removeAll(carrinho);
		this.cep = "";
		session.removeAttribute("carrinhoCompras");

		return new ModelAndView("redirect:/pedidos/pedido/" + id);
	}

	/**
	 * Retorna o carrinho de compras
	 * 
	 * @return
	 */
	public List<ItemProdutoDto> getCarrinho() {
		return this.carrinho;
	}

	/**
	 * Retorna o cep que o cliente informar em detalhes do produto (lá ele pode consultar o frete e o prazo)
	 * 
	 * @return
	 */
	public String getCep() {
		return this.cep;
	}

	/**
	 * Retorna o subtotal da compra
	 * 
	 * @return
	 */
	public BigDecimal getSubTotalCarrinho() {
		BigDecimal preco = BigDecimal.ZERO;
		BigDecimal subTotal = BigDecimal.ZERO;

		for (ItemProdutoDto p : carrinho) {
			preco = p.getPreco().multiply(new BigDecimal(p.getQtde()));
			subTotal = subTotal.add(preco);
		}

		return subTotal;
	}

	/**
	 * Retorna o usuário cliente logado
	 * 
	 * @param request
	 * @return
	 */
	private Cliente getUsuarioLogado(HttpServletRequest request) {
		try {
			HttpSession session = (HttpSession) request.getSession();
			UsuarioSistema usuarioSistema = (UsuarioSistema) session.getAttribute("usuarioLogado");

			return usuarioSistema.getCliente();
		} catch (Exception e) {
			return null;
		}
	}
}
