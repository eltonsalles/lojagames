$(function() {
	// Inicia os campos selects usando o materialize
	$('select').material_select();

	// Máscara para o campo cartão de crédito
	$('#numero-cartao').mask("0000.0000.0000.0000");

	// Aparece e esconde com os campos de cartão de crédito
	$('input[name=tipoPagamento]').on('click', function() {
		var selecionado = $('input[name=tipoPagamento]:checked').val();
		var pagamentoCartao = $('.js-pagamento-cartao');
		var camposPagtoCartao = $('.js-pagto-cartao-credito');

		if (selecionado == 'CARTAO_CREDITO') {
			pagamentoCartao.removeClass('hide');

			camposPagtoCartao.each(function(index, c) {
				$(c).attr('required', 'required');
			})

			atualizarValoresParcelas();
		} else {
			pagamentoCartao.addClass('hide');

			camposPagtoCartao.each(function(index, c) {
				$(c).removeAttr('required');
			});
		}

		atualizarSubTotalFreteTotal(selecionado);
		addEventoClickOpcaoFrete(selecionado);
	});

	// Adiciona o evento para atualizar os valores também no click das opções de frete
	function addEventoClickOpcaoFrete(selecionado) {
		$('.js-valor-frete').on('click', function() {
			if (selecionado == 'CARTAO_CREDITO') {
				atualizarSubTotalFreteTotal('CARTAO_CREDITO');
			} else {
				atualizarSubTotalFreteTotal('BOLETO');
			}

			console.log('pagamento');
		});
	}

	// Atualiza os valores conforme a opção de pagamento
	function atualizarSubTotalFreteTotal(pagamento) {
		var subTotalHidden = converterParaDecimal($('#subtotal-hidden').val());
		var valorFrete = $('#valor-frete');
		var total = $('#total');

		if (pagamento == 'BOLETO') {
			total.val('R$ '
					+ converterParaReal((subTotalHidden * 0.9)
							+ converterParaDecimal(valorFrete.val())));
		} else {
			total.val('R$ '
					+ converterParaReal(subTotalHidden
							+ converterParaDecimal(valorFrete.val())));
		}
	}
});