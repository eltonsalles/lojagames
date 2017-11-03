$(function() {
	$('select').material_select();

	// Máscara para o campo cartão de crédito
	$('#numero-cartao').mask("0000.0000.0000.0000");

	// Aparece e esconde com os campos de cartão de crédito
	$('input[name=forma-pagamento]').on('click', function() {
		var selecionado = $('input[name=forma-pagamento]:checked').val();
		var pagamentoCartao = $('.js-pagamento-cartao');

		if (selecionado == 'cartao-credito') {
			pagamentoCartao.removeClass('hide');
		} else {
			pagamentoCartao.addClass('hide');
		}
	});
});