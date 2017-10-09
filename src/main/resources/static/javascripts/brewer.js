var Brewer = Brewer || {};

Brewer.MaskMoney = (function() {
	// Construtor
	function MaskMoney() {
		this.decimal = $('.js-decimal');
		this.int = $('.js-int');
	}
	
	MaskMoney.prototype.enable = function() {
		this.decimal.maskMoney({decimal: ',', thousands: '.'});
		this.int.maskMoney({precision: 0, thousands: '.'});
	}
	
	return MaskMoney;
})();

$(function() {
	var maskMoney = new Brewer.MaskMoney();
	maskMoney.enable();
});