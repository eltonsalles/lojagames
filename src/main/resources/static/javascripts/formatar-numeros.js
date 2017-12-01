$(function() {
	var decimal = $('.js-decimal');
	decimal.maskMoney({decimal: ',', thousands: '.', allowEmpty: true});
	
	var int = $('.js-int');
	int.maskMoney({precision: 0, thousands: '.', allowEmpty: true});
	
	var codigo = $('.js-codigo');
	codigo.maskMoney({precision: 0, thousands: '', allowEmpty: true});
});