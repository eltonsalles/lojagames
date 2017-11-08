var diaSemana = [ 'Domingo', 'Segunda-Feira', 'Terca-Feira', 'Quarta-Feira', 'Quinta-Feira', 'Sexta-Feira', 'Sabado' ];
  var mesAno = [ 'Janeiro', 'Fevereiro', 'Marco', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro'           , 'Dezembro' ];
  var data = new Date();
  var hoje = diaSemana[data.getDay()] + ', ' + mesAno[data.getMonth()] + ' de ' + data.getFullYear();
  $("#dataNascimento").attr("value", hoje);
  $(".datepicker").pickadate({
    monthsFull: mesAno,
    monthsShort: [ 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez' ],
    weekdaysFull: diaSemana,
    weekdaysShort: [ 'Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab' ],
    weekdaysLetter: [ 'D', 'S', 'T', 'Q', 'Q', 'S', 'S' ],
    selectMonths: true,
    selectYears: true,
    clear: true,
    format: 'yyyy/mm/dd',
    today: "Hoje",
    close: "Fechar",
    min: new Date(data.getFullYear() - 1, 0, 1),
    max: new Date(data.getFullYear() + 1, 11, 31),
    closeOnSelect: true
  });

  $("#dataNascimento").click(function (event) {
      event.stopPropagation();
      $(".datepicker").first().pickadate("picker").open();
  });