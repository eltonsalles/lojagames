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
		$('html, boby').animate({
			scrollTop : 0
		}, 2000);
	});
	
	$('.js-btn-excluir-produto').on('click', function() {
		var produto = $(this).attr('class').split(' ')[0];
		var div = $("div." + produto)[0];
		
		div.remove();
	});
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