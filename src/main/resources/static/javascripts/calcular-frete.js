$(function() {
	var cep = $('.js-cep');
	cep.mask('00000-000');

	cep.on("keyup", function() {
		if (cep.val().length == 9) {
			calcularFrete($(this).val());
		}
	});
	
	function calcularFrete(cep) {
		cep = cep.replace(/\D/, "");
		
		var parametros = "";
		var servicos = [];
		
		servicos[40010] = 'SEDEX';
		servicos[40215] = 'SEDEX 10';
		servicos[41106] = 'PAC';

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
				var textoFrete = $('.texto-frete');
				var btnComprar = $('.btn-comprar');
				textoFrete.html('');
				
				$(xml).find('cServico').each(function () {
					var codigo = servicos[$(this).find('Codigo').text()];
					var valor = $(this).find('Valor').text();
					var prazo = $(this).find('PrazoEntrega').text();
					
					if (valor != 0 && prazo != 0) {
						if (btnComprar.length) {
							textoFrete.append('<p>' + "Serviço: " + codigo + " - Valor: R$ " + valor + " - Prazo: " + prazo + " dia(s)" + '</p>');
						} else {
							textoFrete.append("<p><input class='with-gap' name='valor-frete' value='" + valor + "' type='radio' id='" + codigo + "' /><label for='" + codigo + "'>Serviço: " + codigo + " - Valor: R$ " + valor + " - Prazo: " + prazo + " dia(s)" + "</label></p>");
						}
					}
				});
				
				btnComprar.focus();
			},
			error: function () {
				console.log("Deu erro!!");
			}
		})
	}
});