var lastScroll = 0;

$(document).ready(function() {
	var carrinho = $('#carrinho-compra');

	if (carrinho.length) {
		Materialize.toast('Produto adicionado com sucesso!', 4000, 'green');
	}

	// Verifica o scroll da página para mostrar o botão voltar ao topo
	$(window).scroll(function() {
		var pos = $(this).scrollTop();

		if (pos > lastScroll) {
			$('.ocultar-navbar').slideUp(400);
		} else {
			$('.ocultar-navbar').slideDown(400);
		}

		lastScroll = pos;

		if ($(this).scrollTop() > 300) {
			$('.btn-voltar-topo').fadeIn();
		} else {
			$('.btn-voltar-topo').fadeOut();
		}
	});

	// Atribui ao botão a função de voltar ao topo
	$('.btn-voltar-topo').click(function() {
		$('html, boby').animate({
			scrollTop : 0
		}, 2000);
	});
});