$(function() {
	var decimal = $('.js-decimal');
	decimal.maskMoney({decimal: ',', thousands: '.'});
	
	var int = $('.js-int');
	int.maskMoney({precision: 0, thousands: '.'});
});