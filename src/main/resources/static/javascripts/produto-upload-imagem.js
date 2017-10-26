var TheCode = TheCode || {};

TheCode.UploadImagem = (function() {
	function UploadImagem() {
		this.inputNomeImagem = $('#js-nome-imagem');
		this.inputContentType = $('#js-content-type-imagem');

		this.htmlImagemProdutoTemplate = $('#imagem-produto').html();
		this.template = Handlebars.compile(this.htmlImagemProdutoTemplate);
		this.container = $('.js-container-imagem-produto');

		this.uploadDrop = $('#upload-drop');
	}

	UploadImagem.prototype.iniciar = function() {
		var settings = {
			type : 'json',
			filelimit : 1,
			allow : '*.(jpg|jpeg|png)',
			action : this.container.data('url-imagens'),
			complete : onUploadCompleto.bind(this)
		}

		UIkit.uploadSelect($('#upload-select'), settings);
		UIkit.uploadDrop(this.uploadDrop, settings);

		if (this.inputNomeImagem.val()) {
			onUploadCompleto.call(this, {
				nome : this.inputNomeImagem.val(),
				contentType : this.inputContentType.val()
			});
		}
	}

	function onUploadCompleto(resposta) {
		this.inputNomeImagem.val(resposta.nome);
		this.inputContentType.val(resposta.contentType);

		this.uploadDrop.addClass('hidden');
		var htmlImagemProduto = this.template({
			nomeImagem : resposta.nome
		});
		this.container.append(htmlImagemProduto);

		$('.js-remove-imagem').on('click', onRemoverImagem.bind(this));
	}

	function onRemoverImagem() {
		$('.js-imagem-produto').remove();
		this.uploadDrop.removeClass('hidden');
		this.inputNomeImagem.val('');
		this.inputContentType.val('');
	}

	return UploadImagem;
})();

$(function() {
	var uploadImagem = new TheCode.UploadImagem();
	uploadImagem.iniciar();
});
