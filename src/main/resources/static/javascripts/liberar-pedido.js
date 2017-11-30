$(function() {
	var opcaoCancelar = $('.js-opcao-cancelar-pedido');
	
	if (opcaoCancelar.length) {
		opcaoCancelar.on('click', function() {
			var status = $('.js-opcao-cancelar-pedido:checked').val();
			var inputs = $('.js-cancelar-pedido');
			
			if (status == 'CANCELADO') {
				inputs.each((i) => {
					inputs.eq(i).removeAttr('required');
				});
			} else {
				inputs.each((i) => {
					inputs.eq(i).attr('required', 'required');
				});
			}
		});
	}
});