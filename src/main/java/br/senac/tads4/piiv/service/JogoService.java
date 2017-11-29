package br.senac.tads4.piiv.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.senac.tads4.piiv.model.ItemPedido;
import br.senac.tads4.piiv.model.Jogo;
import br.senac.tads4.piiv.model.enumerated.TipoMovimentacao;
import br.senac.tads4.piiv.model.enumerated.TipoProduto;
import br.senac.tads4.piiv.repository.ItemPedidoRepository;
import br.senac.tads4.piiv.repository.JogoRepository;
import br.senac.tads4.piiv.service.event.historico.GerarHistoricoEvent;
import br.senac.tads4.piiv.service.event.produto.ProdutoSalvoEvent;
import br.senac.tads4.piiv.service.exception.DescricaoDaImagemPassaLimiteCaractesException;
import br.senac.tads4.piiv.service.exception.DescricaoDaImagemVaziaException;
import br.senac.tads4.piiv.service.exception.ListaDeImagensVaziasException;
import br.senac.tads4.piiv.service.exception.ProdutoComPedidoRealizadoException;

/**
 * Classe responsável por persistir os dados no banco de dados na tabela jogo
 * 
 * @author Elton
 *
 */
@Service
public class JogoService extends ProdutoService {

	@Autowired
	private JogoRepository jogoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	/**
	 * Método para salvar um produto do tipo jogo
	 * 
	 * @param jogo
	 */
	public void salvar(Jogo jogo) {
		if (StringUtils.isEmpty(jogo.getImagens().get(0).getNome().trim())) {
			throw new ListaDeImagensVaziasException("O campo imagem é obrigatório");
		}
		
		if (StringUtils.isEmpty(jogo.getImagens().get(0).getDescricao().trim())) {
			throw new DescricaoDaImagemVaziaException("O campo descrição é obrigatório");
		}
		
		if (jogo.getImagens().get(0).getDescricao().trim().length() > 2000) {
			throw new DescricaoDaImagemPassaLimiteCaractesException("A descrição deve ter no máximo 2000 caracteres");
		}
		
		jogo.setTipoProduto(TipoProduto.JOGO);
		jogo.getImagens().get(0).setProduto(jogo);
		
		jogoRepository.save(jogo);
		
		publisher.publishEvent(new ProdutoSalvoEvent(jogo));
		publisher.publishEvent(new GerarHistoricoEvent(jogo, TipoMovimentacao.ENTRADA));
	}
	
	/**
	 * Método para alterar os dados do jogo
	 * 
	 * @param jogo
	 */
	public void alterar(Jogo jogo) {
		if (StringUtils.isEmpty(jogo.getImagens().get(0).getNome().trim())) {
			throw new ListaDeImagensVaziasException("O campo imagem é obrigatório");
		}
		
		if (StringUtils.isEmpty(jogo.getImagens().get(0).getDescricao().trim())) {
			throw new DescricaoDaImagemVaziaException("O campo descrição é obrigatório");
		}
		
		if (jogo.getImagens().get(0).getDescricao().trim().length() > 2000) {
			throw new DescricaoDaImagemPassaLimiteCaractesException("A descrição deve ter no máximo 2000 caracteres");
		}
		
		Jogo j = jogoRepository.findOne(jogo.getIdProduto());
		jogo.setHistoricoProdutos(j.getHistoricoProdutos());
		jogo.setTipoProduto(TipoProduto.JOGO);
		jogo.getImagens().get(0).setProduto(jogo);
		
		jogoRepository.save(jogo);
	}
	
	/**
	 * Excluir um registro
	 * 
	 * @param id
	 */
	public void excluir(Long id) {
		Jogo jogo = jogoRepository.findOne(id);
				
		Optional<ItemPedido> itemPedidoOptional = itemPedidoRepository.findByProduto(jogo);
		
		if (itemPedidoOptional.isPresent()) {
			throw new ProdutoComPedidoRealizadoException("Existem pedidos com este produto, então não é possível excluí-lo!");
		}
		
		jogoRepository.delete(jogo);
	}
}
