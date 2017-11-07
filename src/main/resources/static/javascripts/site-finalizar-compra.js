$(function() {
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
			})
		}
	});
});