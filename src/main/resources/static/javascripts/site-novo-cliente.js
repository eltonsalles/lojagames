$(function() {
	var diaSemana = [ 'Domingo', 'Segunda-Feira', 'Terca-Feira', 'Quarta-Feira', 'Quinta-Feira', 'Sexta-Feira', 'Sabado' ];
	var mesAno = [ 'Janeiro', 'Fevereiro', 'Marco', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro' ];

	$(".datepicker").pickadate(
			{
				monthsFull : mesAno,
				monthsShort : [ 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez' ],
				weekdaysFull : diaSemana,
				weekdaysShort : [ 'Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab' ],
				weekdaysLetter : [ 'D', 'S', 'T', 'Q', 'Q', 'S', 'S' ],
				selectMonths : true,
				selectYears : 100,
				clear : false,
				format : 'yyyy-mm-dd',
				today : "Hoje",
				close : "X",
				max : new Date(),
				closeOnSelect : true
			});

	$("#dataNascimento").click(function(event) {
		event.stopPropagation();
		$(".datepicker").first().pickadate("picker").open();
	});
	
	$('.js-cpf').mask('000.000.000-00');
	
	$('.js-cep').mask('00000-000');
	
	$('.js-numero-endereco').mask('099999999');
	
	$('.js-uf').mask('SS');
	
	var maskTelefone = function (val) {
		return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
	},
		options = {onKeyPress: function(val, e, field, options) {
			field.mask(maskTelefone.apply({}, arguments), options);
		}
	};
		 
	$('.js-telefone').mask(maskTelefone, options);
});