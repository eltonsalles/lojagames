package br.senac.tads4.piiv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.senac.tads4.piiv.model.Jogo;
import br.senac.tads4.piiv.model.enumerated.TipoProduto;
import br.senac.tads4.piiv.repository.JogoRepository;
import br.senac.tads4.piiv.service.event.produto.ProdutoSalvoEvent;
import br.senac.tads4.piiv.service.exception.DescricaoDaImagemPassaLimiteCaractesException;
import br.senac.tads4.piiv.service.exception.DescricaoDaImagemVaziaException;
import br.senac.tads4.piiv.service.exception.ListaDeImagensVaziasException;

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
	private ApplicationEventPublisher publisher;
	
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
	}
}
