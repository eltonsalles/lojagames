$(function() {
	var cep = $('.js-cep');
	var textoFrete = $('.texto-frete');
	var btnComprar = $('.btn-comprar');
	
	// Coloca a máscara no campo CEP
	cep.mask('00000-000');

	// Atribui uma função ao campo e faz a requisição quando tiver 9 caracteres
	cep.on("keyup", function() {
		textoFrete.html('');
		if (cep.val().length == 9) {
			calcularFrete($(this).val());
		}
	});
	
	// Calcula o frete e o prazo de entrega no WebService dos Correios
	function calcularFrete(cep) {
		cep = cep.replace(/\D/, "");
		
		var parametros = "";
		var servicos = [];
		
		servicos[40010] = 'SEDEX';
		servicos[40215] = 'SEDEX 10';
		servicos[41106] = 'PAC';

		// Parametros obrigatórios mesmo quando vazios
		parametros = 
		"nCdEmpresa=" +
		"&sDsSenha=" +
		"&nCdServico=40010,40215,41106" +
		"&sCepOrigem=04696000" +
		"&sCepDestino=" + cep +
		"&nVlPeso=1" +
		"&nCdFormato=1" +
		"&nVlComprimento=20" +
		"&nVlAltura=6" +
		"&nVlLargura=20" +
		"&nVlDiametro=0" +
		"&sCdMaoPropria=n" +
		"&nVlValorDeclarado=0" +
		"&sCdAvisoRecebimento=s" +
		"&StrRetorno=xml" +
		"&nIndicaCalculo=3";
		
		$.ajax({
			method: "POST",
			url: "http://ws.correios.com.br/calculador/CalcPrecoPrazo.aspx?" + parametros,
			dataType: "xml",
			contentType: "application/xml",
			success: function (xml) {
				var finalizarCompra = $('.js-finalizar-compra');
				
				textoFrete.html('');
				btnComprar.focus();
				finalizarCompra.focus();
				
				// Percorre o retorno para montar as informações que serão exibidas
				$(xml).find('cServico').each(function () {
					var codigo = servicos[$(this).find('Codigo').text()];
					var valor = $(this).find('Valor').text();
					var prazo = $(this).find('PrazoEntrega').text();
					
					if (valor != 0 && prazo != 0) {
						if (btnComprar.length) {
							textoFrete.append('<p>' + "Serviço: " + codigo + " - Valor: R$ " + valor + " - Prazo: " + prazo + " dia(s)" + '</p>');
						} else {
							textoFrete.append("<p><input class='with-gap tc-radio-site js-valor-frete' name='valor-frete' value='" + valor + "' type='radio' id='" + codigo + "' required /><label for='" + codigo + "'>Serviço: " + codigo + " - Valor: R$ " + valor + " - Prazo: " + prazo + " dia(s)" + "</label></p>");
						}
					}
				});
				
				// Coloca uma função nas opções de frete para atualizar o valor total da compra
				$('.js-valor-frete').on('click', function() {
					var subtotal = $('#subtotal');
					var total = $('#total');
					
					$('#valor-frete').val('R$ ' + $(this).val());
					total.val('R$ ' + converterParaReal(converterParaDecimal(subtotal.val()) + converterParaDecimal($(this).val())));
				});
			},
			error: function () {
				console.log("Deu erro!!");
			},
			beforeSend: function () {
				// Apresenta o gif até o fim da requisição
				$('.gif-carregando').removeClass('hide');
				
				if (btnComprar.length) {
					textoFrete.removeClass('s12 m8');
					textoFrete.addClass('s10 m6');
				} else {
					textoFrete.removeClass('s4 m8');
					textoFrete.addClass('s3 m7');
				}
			},
			complete: function () {
				// Retira o gif após a requisição
				$('.gif-carregando').addClass('hide');
				
				if (btnComprar.length) {
					textoFrete.removeClass('s10 m6');
					textoFrete.addClass('s12 m8');
				} else {
					textoFrete.removeClass('s3 m7');
					textoFrete.addClass('s4 m8');
				}
				
			}
		})
	}
	
	// Caso tenha um CEP ao carregar a página, então faz uma requisição para calcular prazo e frete
	if (cep.val().length == 9) {
		textoFrete.html('');
		calcularFrete(cep.val());
	}
});