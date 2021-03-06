var interval = null;

$(document).ready(function() {
	// Navbar
	$(".button-collapse").sideNav();

	// Remove o active no menu
	$('.remove-indicator .tab a').removeClass('active');

	// Carousel
	$('.carousel.carousel-slider').carousel({
		fullWidth : true
	});
	playInterval();

	// Adiciona funções aos eventos do carousel
	$('.carousel.carousel-slider').on({
		mouseenter : function() {
			stopInterval();
		},
		mouseleave : function() {
			playInterval();
		}
	});

	// Função para iniciar o carousel
	function playInterval() {
		interval = setInterval(function() {
			$('.carousel.carousel-slider').carousel('next');
		}, 4500)
	}

	// Função para parar o carousel
	function stopInterval() {
		clearInterval(interval);
	}

	// Inicia o parallax nas páginas
	$('.parallax').parallax();

	// Inicia os campos selects com a formatação do materialize
	$('select').material_select();

	// Esconde o botão voltar ao topo
	$('.btn-voltar-topo').hide();

	// Verifica o scroll da página para mostrar o botão voltar ao topo
	$(window).scroll(function() {
		if ($(this).scrollTop() > 300) {
			$('.btn-voltar-topo').fadeIn();
		} else {
			$('.btn-voltar-topo').fadeOut();
		}
	});

	// Atribui ao botão a função de voltar ao topo
	$('.btn-voltar-topo').click(function() {
		$('html, body').animate({
			scrollTop : 0
		}, 2000);
	});

	// Código para fechar os alertas do materialize
	$('body').on('click', '.tc-alert .tc-close', function() {
		$(this).parent().fadeOut(300, function() {
			$(this).remove();
		});
	});
	

	// Função para destacar que o cliente pode adicionar o produto direto no carrinho
	btnAddCarrinhoDireto();
});

// Função para destacar que o cliente pode adicionar o produto direto no carrinho
function btnAddCarrinhoDireto() {
	var produto = $('.produto');
	
	produto.on('mouseover', function() {
		var precoAvista = $(this).find('.js-preco-avista-add-carrinho');
		var textoAddCarrinho = $(this).find('.js-texto-add-carrinho');
		var formAddCarrinho = $(this).find('.js-form-add-carrinho');
		var iconAddCarrinho =  $(this).find('.js-icon-add-carrinho');
		
		precoAvista.addClass('hide');
		textoAddCarrinho.removeClass('hide');
		formAddCarrinho.removeClass('grey lighten-2').addClass('orange lighten-2');
		iconAddCarrinho.text('add');
	});
	
	produto.on('mouseout', function() {
		var precoAvista = $(this).find('.js-preco-avista-add-carrinho');
		var textoAddCarrinho = $(this).find('.js-texto-add-carrinho');
		var formAddCarrinho = $(this).find('.js-form-add-carrinho');
		var iconAddCarrinho =  $(this).find('.js-icon-add-carrinho');
		
		precoAvista.removeClass('hide');
		textoAddCarrinho.addClass('hide');
		formAddCarrinho.removeClass('orange lighten-2').addClass('grey lighten-2');
		iconAddCarrinho.text('add_shopping_cart');
	});
}