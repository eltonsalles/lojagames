var lastScroll = 0;

$(document).ready(function() {
	var carrinho = $("div[class^='produto-id']");
	var msgProdutoCarrinho = $('.mensagem-produto-carrinho');

	// Verifica a necessidade de apresentar uma mensagem de que o produto foi adicionado ao carrinho
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
	
	// Código para fechar os alertas do materialize
	$('body').on('click', '.tc-alert .tc-close', function() {
		$(this).parent().fadeOut(300, function() {
			$(this).remove();
		});
	});
	
	// Arruma a string para a submissão (Barra Superior)
	$('#carrinho-compra-barra-superior').submit(submeterForm);
	
	// Arruma a string para a submissão
	$('.js-arrumar-valor-frete').submit(submeterForm);
	
	$('.js-arrumar-valor-frete-finalizar-compra').submit(function() {
		var subTotal = $('#subtotal');
		var valorFrete = $('#valor-frete');
		var total = $('#total');
		
		subTotal.val(subTotal.val().replace('R$ ', ''));
		valorFrete.val(valorFrete.val().replace('R$ ', ''));
		total.val(total.val().replace('R$ ', ''));
	});
	
	function submeterForm() {
		var parametros = $(this).serializeArray();
		var submeter = true;
		
		parametros.forEach(p => {
			if(p.value == '') {
				submeter = false;
			}
		});
		
		if (!submeter) {
			return false;
		}
		
		var subTotal = $('#subtotal');
		var valorFrete = $('#valor-frete');
		var total = $('#total');
		
		subTotal.val(subTotal.val().replace('R$ ', ''));
		valorFrete.val(valorFrete.val().replace('R$ ', ''));
		total.val(total.val().replace('R$ ', ''));
	}
	
	var campos = $('.js-preecher-form-barra-superior');
	
	for (var i = 0; i < campos.length; i++) {
		campos.eq(i).on('keyup', function() {
			$('#' + $(this).attr('id') + '-barra-superior').val($(this).val());
		});
	}

	// Verifica a quantidade em estoque do produto quando o cliente clica
	$('.js-quantidade').on('click', function() {
		var id = $(this).attr('id').split('-')[2];
		verificarEstoque(id, $(this).val(), $(this));
	});
	
	// Verifica a quantidade em estoque do produto quando o cliente digita
	$('.js-quantidade').on('change', function() {
		var id = $(this).attr('id').split('-')[2];
		verificarEstoque(id, $(this).val(), $(this));
	});

	// Função que verifica a quantidade em estoque do produto
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
				atualizarValores(id, estoque);
			},
			success: function(mensagem) {
				atualizarValores(id, quantidade);
			}
		});
	}
	
	// Função que atualiza a quantidade do item conforme o estoque
	function atualizarValores(id, quantidade) {
		var produtos = $('.js-container-item-carrinho');
		var preco = $('#preco-produto-' + id).val();
		var subTotal = 0;
		var total = $('#total');
		var frete = $('#valor-frete').val();
		
		$('#subtotal-produto-' + id).val('R$ ' + converterParaReal(converterParaDecimal(preco) * quantidade));
		produtos.each(function(index, p) {
			subTotal += converterParaDecimal($(p).find('.js-subtotal-produto').val());
		})
		
		$('#subtotal').val('R$ ' + converterParaReal(subTotal));
		
		if (frete != '') {
			total.val('R$ ' + converterParaReal(converterParaDecimal(frete) + subTotal));
		} else {
			total.val('R$ ' + converterParaReal(subTotal));
		}
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

	valor = "" + valor.toFixed(2);
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
	valor = inteiro + "," + decimal; //.substring(0, 2);
	return valor;
}

// Atualizar valores das parcelas
function atualizarValoresParcelas() {
	var parcelas = $('.js-parcelas');
	var maximoParcelas = $('#parcelas').data('maximo-parcelas');
	var total = converterParaDecimal($('#total').val());
	
	parcelas.material_select('destroy');
	parcelas.html('');
	parcelas.append("<option value='' disabled selected>Selecione o nº de parcelas</option>");
	for (var i = 1; i <= maximoParcelas; i++) {
		parcelas.append("<option value='" + i + "'>" + i + "x de R$ " + converterParaReal(total / i) + " sem juros</option>");
	}
	
	parcelas.material_select();
}