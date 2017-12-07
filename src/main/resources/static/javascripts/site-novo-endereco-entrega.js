var salvouEnd = false;

$(function() {
	$('.modal').modal();
	
	var logradouro = $('#endereco');
	var numeroLogradouro = $('#numero-endereco');
	var complemento = $('#complemento');
	var divComplemento = $('.js-complemento');
	var bairro = $('#bairro');
	var cidade = $('#cidade');
	var uf = $('#uf');
	var cep = $('#cep');
	var referencia = $('#referencia');
	var opcoes = $('.js-opcoes-endereco-entrega');
	var btnSalvar = $('.js-salvar-endereco-entrega');
	
	$('#novo-end-numero-logradouro').mask('099999999');
	$('#novo-end-uf').mask('SS');
	$('#novo-end-cep').mask('00000-000');
	
	// Coloca uma função ao click do radio para alterar o endereço da entrega
	// Aqui são as opções de endereço que cliente já tem cadastrado
	opcoes.each((i) => {
		opcoes.eq(i).on('click', function() {
			var opcao = $(this).val();			
			var endereco = $('label[for=opcao-' + opcao + ']').text().split(', ');

			logradouro.val(endereco[0]);
			numeroLogradouro.val(endereco[1]);
			if (endereco.length > 7) {
				complemento.val(endereco[2]);
				bairro.val(endereco[3]);
				cidade.val(endereco[4]);
				uf.val(endereco[5]);
				cep.val(endereco[6]).trigger('change');
				referencia.text(endereco[7]);
				
				divComplemento.removeClass('hide');
			} else {
				bairro.val(endereco[2]);
				cidade.val(endereco[3]);
				uf.val(endereco[4]);
				cep.val(endereco[5]).trigger('change');
				referencia.text(endereco[6]);
				
				divComplemento.addClass('hide');
			}
			
			// Para garantir que os calculos da venda se mantenha corretos
			$('input[name=tipoPagamento]:checked').prop('checked', false);
			
			// Necessário para o efeito dos inputs do materialize
			Materialize.updateTextFields();
			return true;
		});
	});
	
	// Função para caso o cliente queira cadastrar um novo endereço
	btnSalvar.on('click', function() {
		var inputs = $('.js-input-novo-endereco');
		var salvarEnd = true;
		
		// Validação dos campos
		inputs.each((i) => {
			if (inputs.eq(i).val() == '') {
				var name = inputs.eq(i).attr('name').split('-');
				
				if (name[2] != 'complemento') {
					if (name[2] == 'numero') {
						name[2] = 'número';
					} else if(name[2] == 'referencia') {
						name[2] = 'referência';
					}
					
					alert('Preencha o campo ' + name[2] + '.');
					salvarEnd = false;
					return false;
				}
			}
		});
		
		// Faz a requisição ajax
		if (salvarEnd) {
			var salvouEnd = false;
			var url = $('#inserir-endereco-entrega').data('url');
			
			$.ajax({
				url: url,
				method: 'POST',
				contentType: 'application/json',
				async : false,
				data: JSON.stringify({
					cliente: {
						id: $('.js-input-novo-endereco-cliente').val()
					},
					logradouro: inputs.eq(1).val(),
					numero: inputs.eq(2).val(),
					complemento: inputs.eq(3).val(),
					bairro: inputs.eq(4).val(),
					cidade: inputs.eq(5).val(),
					uf: inputs.eq(6).val(),
					cep: inputs.eq(0).val(),
					referencia: inputs.eq(7).val()
				}),
				success: function() {
					salvouEnd = true;
				},
				error: function(xhr) {
					alert(xhr.responseText);
					salvouEnd = false;
				}
			});

			// Se a requisição for bem sucedida então muda o endereço na tela de finalizar pedido
			if (salvouEnd) {
				logradouro.val(inputs.eq(1).val());
				numeroLogradouro.val(inputs.eq(2).val());
				if (inputs.eq(3).val() != '') {
					complemento.val(inputs.eq(3).val());
					divComplemento.removeClass('hide');
				} else {
					complemento.val('');
					divComplemento.addClass('hide');
				}
				bairro.val(inputs.eq(4).val());
				cidade.val(inputs.eq(5).val());
				uf.val(inputs.eq(6).val());
				cep.val(inputs.eq(0).val()).trigger('change');
				referencia.text(inputs.eq(7).val());
				
				// Para garantir que os calculos da venda se mantenha corretos
				$('input[name=tipoPagamento]:checked').prop('checked', false);
				
				// Necessário para o efeito dos inputs do materialize
				Materialize.updateTextFields();
			}
			
			return salvouEnd;
		}

		return false;
	});
});