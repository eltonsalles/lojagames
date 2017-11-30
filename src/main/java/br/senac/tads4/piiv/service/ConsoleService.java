package br.senac.tads4.piiv.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.senac.tads4.piiv.model.Console;
import br.senac.tads4.piiv.model.ItemPedido;
import br.senac.tads4.piiv.model.enumerated.TipoMovimentacao;
import br.senac.tads4.piiv.model.enumerated.TipoProduto;
import br.senac.tads4.piiv.repository.ConsoleRepository;
import br.senac.tads4.piiv.repository.ItemPedidoRepository;
import br.senac.tads4.piiv.service.event.historico.HistoricoEvent;
import br.senac.tads4.piiv.service.event.produto.ProdutoSalvoEvent;
import br.senac.tads4.piiv.service.exception.DescricaoDaImagemPassaLimiteCaractesException;
import br.senac.tads4.piiv.service.exception.DescricaoDaImagemVaziaException;
import br.senac.tads4.piiv.service.exception.ListaDeImagensVaziasException;
import br.senac.tads4.piiv.service.exception.ProdutoComPedidoRealizadoException;

/**
 * Classe responsável por persistir os dados no banco de dados na tabela console
 * 
 * @author Elton
 *
 */
@Service
public class ConsoleService extends ProdutoService {

	@Autowired
	private ConsoleRepository consoles;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	/**
	 * Salva os dados do produto do tipo console
	 * 
	 * @param console
	 */
	public void salvar(Console console) {
		if (StringUtils.isEmpty(console.getImagens().get(0).getNome().trim())) {
			throw new ListaDeImagensVaziasException("O campo imagem é obrigatório");
		}
		
		if (StringUtils.isEmpty(console.getImagens().get(0).getDescricao().trim())) {
			throw new DescricaoDaImagemVaziaException("O campo descrição é obrigatório");
		}
		
		if (console.getImagens().get(0).getDescricao().trim().length() > 2000) {
			throw new DescricaoDaImagemPassaLimiteCaractesException("A descrição deve ter no máximo 2000 caracteres");
		}
		
		console.setTipoProduto(TipoProduto.CONSOLE);
		console.getImagens().get(0).setProduto(console);
		
		consoles.save(console);
		
		publisher.publishEvent(new ProdutoSalvoEvent(console));
		publisher.publishEvent(new HistoricoEvent(console, TipoMovimentacao.ENTRADA));
	}
	
	/**
	 * Alterar os dados do produto do tipo console
	 * 
	 * @param console
	 */
	public void alterar(Console console) {
		if (StringUtils.isEmpty(console.getImagens().get(0).getNome().trim())) {
			throw new ListaDeImagensVaziasException("O campo imagem é obrigatório");
		}
		
		if (StringUtils.isEmpty(console.getImagens().get(0).getDescricao().trim())) {
			throw new DescricaoDaImagemVaziaException("O campo descrição é obrigatório");
		}
		
		if (console.getImagens().get(0).getDescricao().trim().length() > 2000) {
			throw new DescricaoDaImagemPassaLimiteCaractesException("A descrição deve ter no máximo 2000 caracteres");
		}
		
		Console c = consoles.findOne(console.getIdProduto());
		console.setHistoricoProdutos(c.getHistoricoProdutos());
		console.setTipoProduto(TipoProduto.CONSOLE);
		console.getImagens().get(0).setProduto(console);
		
		consoles.save(console);
	}
	
	/**
	 * Excluir um registro
	 * 
	 * @param id
	 */
	public void excluir(Long id) {
		Console console = consoles.findOne(id);
				
		Optional<ItemPedido> itemPedidoOptional = itemPedidoRepository.findByProduto(console);
		
		if (itemPedidoOptional.isPresent()) {
			throw new ProdutoComPedidoRealizadoException("Existem pedidos com este produto, então não é possível excluí-lo!");
		}
		
		consoles.delete(console);
	}
}
