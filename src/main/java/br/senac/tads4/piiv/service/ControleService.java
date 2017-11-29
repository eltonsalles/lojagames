package br.senac.tads4.piiv.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.senac.tads4.piiv.model.Controle;
import br.senac.tads4.piiv.model.ItemPedido;
import br.senac.tads4.piiv.model.enumerated.TipoMovimentacao;
import br.senac.tads4.piiv.model.enumerated.TipoProduto;
import br.senac.tads4.piiv.repository.ControleRepository;
import br.senac.tads4.piiv.repository.ItemPedidoRepository;
import br.senac.tads4.piiv.service.event.historico.GerarHistoricoEvent;
import br.senac.tads4.piiv.service.event.produto.ProdutoSalvoEvent;
import br.senac.tads4.piiv.service.exception.DescricaoDaImagemPassaLimiteCaractesException;
import br.senac.tads4.piiv.service.exception.DescricaoDaImagemVaziaException;
import br.senac.tads4.piiv.service.exception.ListaDeImagensVaziasException;
import br.senac.tads4.piiv.service.exception.ProdutoComPedidoRealizadoException;

/**
 * Classe responsável por persistir os dados no banco de dados na tabela controle
 * 
 * @author Elton
 *
 */
@Service
public class ControleService extends ProdutoService {

	@Autowired
	private ControleRepository controles;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	/**
	 * Salva os dados do produto do tipo controle
	 * 
	 * @param controle
	 */
	public void salvar(Controle controle) {
		if (StringUtils.isEmpty(controle.getImagens().get(0).getNome().trim())) {
			throw new ListaDeImagensVaziasException("O campo imagem é obrigatório");
		}
		
		if (StringUtils.isEmpty(controle.getImagens().get(0).getDescricao().trim())) {
			throw new DescricaoDaImagemVaziaException("O campo descrição é obrigatório");
		}
		
		if (controle.getImagens().get(0).getDescricao().trim().length() > 2000) {
			throw new DescricaoDaImagemPassaLimiteCaractesException("A descrição deve ter no máximo 2000 caracteres");
		}
		
		controle.setTipoProduto(TipoProduto.CONTROLE);
		controle.getImagens().get(0).setProduto(controle);
		controles.save(controle);
		
		publisher.publishEvent(new ProdutoSalvoEvent(controle));
		publisher.publishEvent(new GerarHistoricoEvent(controle, TipoMovimentacao.ENTRADA));
	}
	
	/**
	 * Alterar os dados do produto do tipo controle
	 * 
	 * @param controle
	 */
	public void alterar(Controle controle) {
		if (StringUtils.isEmpty(controle.getImagens().get(0).getNome().trim())) {
			throw new ListaDeImagensVaziasException("O campo imagem é obrigatório");
		}
		
		if (StringUtils.isEmpty(controle.getImagens().get(0).getDescricao().trim())) {
			throw new DescricaoDaImagemVaziaException("O campo descrição é obrigatório");
		}
		
		if (controle.getImagens().get(0).getDescricao().trim().length() > 2000) {
			throw new DescricaoDaImagemPassaLimiteCaractesException("A descrição deve ter no máximo 2000 caracteres");
		}
		
		Controle c = controles.findOne(controle.getIdProduto());
		controle.setHistoricoProdutos(c.getHistoricoProdutos());
		controle.setTipoProduto(TipoProduto.CONTROLE);
		controle.getImagens().get(0).setProduto(controle);
		
		controles.save(controle);
	}
	
	/**
	 * Excluir um registro
	 * 
	 * @param id
	 */
	public void excluir(Long id) {
		Controle controle = controles.findOne(id);
				
		Optional<ItemPedido> itemPedidoOptional = itemPedidoRepository.findByProduto(controle);
		
		if (itemPedidoOptional.isPresent()) {
			throw new ProdutoComPedidoRealizadoException("Existem pedidos com este produto, então não é possível excluí-lo!");
		}
		
		controles.delete(controle);
	}
}
