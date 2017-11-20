package br.senac.tads4.piiv.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.senac.tads4.piiv.model.Cliente;
import br.senac.tads4.piiv.model.Pedido;
import br.senac.tads4.piiv.repository.PedidoRepository;
import br.senac.tads4.piiv.repository.TipoConsoleRepository;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private TipoConsoleRepository tipoConsoleRepository;

	@Autowired
	private PedidoRepository pedidoRepository;
	
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
		mv.addObject("pedido", pedido);
		mv.addObject("tiposConsole", tipoConsoleRepository.findAll());

		// Se constar pagamento é informado a data de previsão de entrega
		if (pedido.getDataPagamento() != null) {
			LocalDate previsaoEntrega = pedido.getDataPagamento().plusDays(pedido.getDiasEntrega());
			mv.addObject("previsaoEntrega", previsaoEntrega);
		}

		// Só exibe o botão para visualizar o boleto se não tiver passado o prazo de pagamento
		if (!pedido.getDataPedido().plusDays(BoletoController.VENCIMENTO_BOLETO + BoletoController.PAGO_ATE)
				.isBefore(LocalDate.now())) {
			mv.addObject("botaoBoleto", true);
		}

		return mv;
	}
	
	@RequestMapping(value = "/pedidos")
	public ModelAndView pedidos() {
		ModelAndView mv = new ModelAndView("site/pedido/MeusPedidos");
		
		List<Pedido> pedidos = pedidoRepository.findAll();
		Cliente cliente = login.recuperarUsuario().getCliente();
		
		for(int i =0; i<pedidos.size();i++) {
			long idClient =pedidos.get(i).getCliente().getId();
			
			if( idClient != cliente.getId()) {
				pedidos.remove(i);
			}
		}
		
		mv.addObject("pedidos", pedidos);
		mv.addObject("cliente", cliente);
		
		return mv;
	}
}