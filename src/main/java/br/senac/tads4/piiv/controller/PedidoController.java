package br.senac.tads4.piiv.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senac.tads4.piiv.model.Cliente;
import br.senac.tads4.piiv.model.Pedido;
import br.senac.tads4.piiv.repository.PedidoRepository;
import br.senac.tads4.piiv.repository.TipoConsoleRepository;
import br.senac.tads4.piiv.service.PedidoService;
import br.senac.tads4.piiv.service.exception.StatusNaoSelecionadoException;

@Controller
@RequestMapping(value = "/admin/pedidos")
public class PedidoController {

	@Autowired
	private TipoConsoleRepository tipoConsoleRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private LoginController login;

	/**
	 * Permite visualizar o pedido em detalhes do cliente
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}")
	public ModelAndView pedido(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("site/pedido/VisualizarPedido");

		Pedido pedido = pedidoRepository.findOne(id);
		Cliente cliente = login.recuperarUsuario().getCliente();

		mv.addObject("pedido", pedido);
		mv.addObject("cliente", cliente);
		mv.addObject("tiposConsole", tipoConsoleRepository.findAll());

		// Se constar pagamento é informado a data de previsão de entrega
		if (pedido.getDataPagamento() != null) {
			LocalDate previsaoEntrega = pedido.getDataPagamento().plusDays(pedido.getDiasEntrega());
			mv.addObject("previsaoEntrega", previsaoEntrega);
		}

		// Só exibe o botão para visualizar o boleto se não tiver passado o prazo de
		// pagamento
		if (!pedido.getDataPedido().plusDays(BoletoController.VENCIMENTO_BOLETO + BoletoController.PAGO_ATE)
				.isBefore(LocalDate.now())) {
			mv.addObject("botaoBoleto", true);
		}

		return mv;
	}

	@RequestMapping(value = "/pedidos")
	public ModelAndView pedidos() {
		ModelAndView mv = new ModelAndView("site/pedido/MeusPedidos");

		Cliente cliente = login.recuperarUsuario().getCliente();
		List<Pedido> pedidos = pedidoRepository.findByCliente(cliente);

		mv.addObject("pedidos", pedidos);
		mv.addObject("cliente", cliente);
		return mv;
	}

	/**
	 * Exibe o formulário de pesquisa de pedido
	 * 
	 * @param pedido
	 * @return
	 */
	@RequestMapping(value = "/pesquisar")
	public ModelAndView pesquisar(Pedido pedido) {
		return new ModelAndView("backoffice/pedido/LiberarPedido");
	}

	/**
	 * Exibe o formulário de pesquisa de pedido com a mensagem de erro
	 * 
	 * @param pedido
	 * @return
	 */
	private ModelAndView pesquisarComErro(Pedido pedido) {
		ModelAndView mv = new ModelAndView("backoffice/pedido/LiberarPedido");
		mv.addObject("mensagemErro", "Código de pedido inválido");
		mv.addObject("pedido", pedido);
		mv.addObject("id", pedido.getId());
		return mv;
	}

	/**
	 * Permite efetuar a pesquisa de um pedido através do código do mesmo
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/pesquisar", method = RequestMethod.POST)
	public ModelAndView pesquisar(@RequestParam Long id) {
		ModelAndView mv = new ModelAndView("backoffice/pedido/LiberarPedido");

		Pedido pedido;
		try {
			pedido = pedidoRepository.findOne(id);

			if (pedido == null) {
				return pesquisarComErro(new Pedido());
			}

			mv.addObject("pedido", pedido);
			mv.addObject("id", id);
			return mv;

		} catch (Exception e) {
			return pesquisarComErro(new Pedido());
		}
	}

	/**
	 * Exibe o formulário de status do pedido com a mensagem de erro
	 * 
	 * @param pedido
	 * @return
	 */
	private ModelAndView liberarComErro(Pedido pedido) {
		ModelAndView mv = new ModelAndView("backoffice/pedido/LiberarPedido");
		mv.addObject("mensagemErroLiberar", "Existem campos obrigatórios sem preechimento");
		mv.addObject("pedido", pedido);
		mv.addObject("id", pedido.getId());
		return mv;
	}

	/**
	 * Permite atualizar o status do pedido pesquisado
	 * 
	 * @param id
	 * @param status
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/liberar", method = RequestMethod.POST)
	public ModelAndView liberar(@RequestParam Long id, @RequestParam String status, RedirectAttributes attributes) {

		try {
			pedidoService.alterarStatus(id, status);
		} catch (StatusNaoSelecionadoException e) {
			return this.liberarComErro(new Pedido());
		} catch (Exception e) {
			return this.liberarComErro(new Pedido());
		}

		attributes.addFlashAttribute("mensagem", "Dados alterados com sucesso!");
		return new ModelAndView("redirect:/admin/pedidos/pesquisar/");
	}
}