package br.senac.tads4.piiv.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senac.tads4.piiv.model.Produto;
import br.senac.tads4.piiv.repository.ProdutoRepository;
import br.senac.tads4.piiv.service.exception.DescontoAteAntesDeHojeException;
import br.senac.tads4.piiv.service.exception.DescontoAteInvalidoException;
import br.senac.tads4.piiv.service.exception.PercentualDescontoInvalidoException;
import br.senac.tads4.piiv.service.exception.PercentualDescontoMaiorQueCemException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	// 10% de desconto
	private Double percentualDesconto = 0.9;
	
	// Número máximo de parcelas
	private Integer maximoParcelas = 10;

	public Double getPercentualDesconto() {
		return percentualDesconto;
	}

	public void setPercentualDesconto(Double percentualDesconto) {
		this.percentualDesconto = percentualDesconto;
	}

	public Integer getMaximoParcelas() {
		return maximoParcelas;
	}

	public void setMaximoParcelas(Integer maximoParcelas) {
		this.maximoParcelas = maximoParcelas;
	}
	
	/**
	 * Método para atualizar os descontos dos produtos
	 * 
	 * @param produtos
	 * @param desconto
	 * @param data
	 */
	public void salvarDescontos(List<Produto> produtos, BigDecimal desconto, LocalDate data) {
		if (desconto == null) {
			throw new PercentualDescontoInvalidoException("O campo percentual de desconto é obrigatório");
		}
		
		if (data == null) {
			throw new DescontoAteInvalidoException("O campo desconto até é obrigatório");
		}
		
		if (desconto.compareTo(new BigDecimal(100)) >= 0) {
			throw new PercentualDescontoMaiorQueCemException("O valor do desconto não pode ser igual ou maior que 100%");
		}
		
		if (data.isBefore(LocalDate.now())) {
			throw new DescontoAteAntesDeHojeException("A data do campo desconto até não pode ser antes de hoje");
		}
		
		produtos.forEach(p -> {
			p.setPercentualDesconto(desconto);
			p.setDescontoAte(data);
		});
		
		produtoRepository.save(produtos);
	}
}
