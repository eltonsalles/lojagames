$(function() {
	var btnOrdenar = $('.ordenar').find('.select-dropdown').find('li');
	var options = $('.js-option-ordenar');
	
	btnOrdenar.on('click', function() {
		options.each((i) => {
			if (options.eq(i).text() == $(this).text()) {
				var href = $(location).attr('href');
				href = href.replace('/ordem-alfabetica', '');
				href = href.replace('/menor-preco', '');
				href = href.replace('/maior-preco', '');
				
				$(location).attr('href', href + '/' + options.eq(i).val());
			}
		});
	});
});