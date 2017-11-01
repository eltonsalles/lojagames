var lastScroll = 0;

$(document).ready(function() {
	var carrinho = $("div[class^='produto-id']");
	var msgProdutoCarrinho = $('.mensagem-produto-carrinho');

	if (msgProdutoCarrinho.length) {
		Materialize.toast(msgProdutoCarrinho.text(), 4000, 'green');
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
		$('html, body').animate({
			scrollTop : 0
		}, 2000);
	});

	// Verifica a quantidade em estoque do produto
	$('.js-quantidade').on('click', function() {
		var id = $(this).attr('id').split('-')[2];
		verificarEstoque(id, $(this).val(), $(this));
	});
	
	// Verifica a quantidade em estoque do produto
	$('.js-quantidade').on('change', function() {
		var id = $(this).attr('id').split('-')[2];
		verificarEstoque(id, $(this).val(), $(this));
	});

	function verificarEstoque(id, quantidade, input) {
		var url = input.data('alterar-quantidade');

		$.ajax({
			url: url,
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({
				id: id,
				qtde: quantidade
			}),
			error: function(obj) {
				$('.toast').remove();
				Materialize.toast('O produto não tem o estoque desejado!', 4000, 'red');
				
				var estoque = obj.responseText;
				input.val(estoque);
			},
			success: function(mensagem) {}
		});
	}
});

// Converte string em decimal
function converterParaDecimal(valor) {
	valor = valor.replace("R$ ", '').replace(/\./, '').replace(/,/, '.');
	return parseFloat(valor);
}

// Converte decimal em string (R$)
function converterParaReal(valor) {
	var inteiro = null, decimal = null, c = null, j = null;
	var aux = new Array();

	valor = "" + valor;
	c = valor.indexOf(".", 0);
	// Encontrou o ponto na string
	if (c > 0) {
		// Separa as partes em inteiro e decimal
		inteiro = valor.substring(0, c);
		decimal = valor.substring(c + 1, valor.length);

		if (decimal.length === 1) {
			decimal += "0";
		}
	} else {
		inteiro = valor;
	}

	// Pega a parte inteiro de 3 em 3 partes
	for (j = inteiro.length, c = 0; j > 0; j -= 3, c++) {
		aux[c] = inteiro.substring(j - 3, j);
	}

	// Percorre a string acrescentando os pontos
	inteiro = "";
	for (c = aux.length - 1; c >= 0; c--) {
		inteiro += aux[c] + '.';
	}

	// Retirando o ultimo ponto e finalizando a parte inteiro
	inteiro = inteiro.substring(0, inteiro.length - 1);

	decimal = parseInt(decimal);
	if (isNaN(decimal)) {
		decimal = "00";
	} else {
		decimal = "" + decimal;
		if (decimal.length === 1) {
			decimal = "0" + decimal;
		}
	}
	valor = inteiro + "," + decimal;
	return valor;
}