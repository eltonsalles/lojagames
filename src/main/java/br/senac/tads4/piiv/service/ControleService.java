package br.senac.tads4.piiv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.senac.tads4.piiv.model.Controle;
import br.senac.tads4.piiv.model.enumerated.TipoProduto;
import br.senac.tads4.piiv.repository.ControleRepository;
import br.senac.tads4.piiv.service.event.produto.ProdutoSalvoEvent;
import br.senac.tads4.piiv.service.exception.DescricaoDaImagemPassaLimiteCaractesException;
import br.senac.tads4.piiv.service.exception.DescricaoDaImagemVaziaException;
import br.senac.tads4.piiv.service.exception.ListaDeImagensVaziasException;

/**
 * Classe responsável por persistir os dados no banco de dados na tabela controle
 * 
 * @author Elton
 *
 */
@Service
public class ControleService {

	@Autowired
	private ControleRepository controles;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
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
	}
}
