var interval = null;
var lastScroll = 0;

$(document).ready(function(){
  // Navbar
  $(".button-collapse").sideNav();

  // Remove o active no menu
  $('.remove-indicator .tab a').removeClass('active');

  // Carousel
  $('.carousel.carousel-slider').carousel({fullWidth: true});
  playInterval();

  // Adiciona funções aos eventos do carousel
  $('.carousel.carousel-slider').on({
    mouseenter: function() {
      stopInterval();
    },
    mouseleave: function() {
      playInterval();
    }
  });

  // Função para iniciar o carousel
  function playInterval() {
    interval = setInterval(function() {
      $('.carousel.carousel-slider').carousel('next');
    }, 4500)
  }

  // Função para parar o carousel
  function stopInterval() {
    clearInterval(interval);
  }

  // Inicia o parallax nas páginas
  $('.parallax').parallax();

  // Inicia os campos selects com a formatação do materialize
  $('select').material_select();

  // Esconde o botão voltar ao topo
  $('.btn-voltar-topo').hide();

  // Verifica o scroll da página para mostrar o botão voltar ao topo
  $(window).scroll(function() {
    var pos = $(this).scrollTop();

    if (pos > lastScroll) {
      $('.menu-desktop').slideUp(400, function() {
        $('.ocultar-navbar').slideUp(200);
      });
    } else {
      $('.ocultar-navbar').slideDown(400, function() {
        $('.menu-desktop').slideDown(200);
      });
    }

    lastScroll = pos;

    if ($(this).scrollTop() > 300) {
      $('.btn-voltar-topo').fadeIn();
    } else {
      $('.btn-voltar-topo').fadeOut();
    }
  });

  // Atribui ao botão a função de voltar ao topo
  $('.btn-voltar-topo').click(function() {
    $('html, boby').animate({scrollTop: 0}, 2000);
  });
});