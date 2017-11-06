$(function() {
	var categoria = window.location.href.split("/")[6]; 
	var genero = $('.js-genero');

	if (categoria == "JOGO"){
		genero.removeClass('hide');
	} else {
		genero.addClass('hide');
	}
});