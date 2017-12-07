$(document).ready(function() {

            function limpa_formulário_cep() {
                // Limpa valores do formulário de cep.
                $("#novo-end-logradouro").val("");
                $("#novo-end-bairro").val("");
                $("#novo-end-cidade").val("");
                $("#novo-end-uf").val("");
                
            }
            
            //Quando o campo cep perde o foco.
            $("#novo-end-cep").blur(function() {
           

                //Nova variável "cep" somente com dígitos.
                var cep = $(this).val().replace(/\D/g, '');

                //Verifica se campo cep possui valor informado.
                if (cep != "") {

                    //Expressão regular para validar o CEP.
                    var validacep = /^[0-9]{8}$/;

                    //Valida o formato do CEP.
                    if(validacep.test(cep)) {

                        //Preenche os campos com "..." enquanto consulta webservice.
                        $("#novo-end-logradouro").val("");
                        $("#novo-end-bairro").val("");
                        $("#novo-end-cidade").val("");
                        $("#novo-end-uf").val("");

                        //Consulta o webservice viacep.com.br/
                        $.getJSON("//viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {

                            if (!("erro" in dados)) {
                                //Atualiza os campos com os valores da consulta.
                                $("#novo-end-logradouro").val(dados.logradouro);
                                $("#novo-end-bairro").val(dados.bairro);
                                $("#novo-end-cidade").val(dados.localidade);
                                $("#novo-end-uf").val(dados.uf);
                                Materialize.updateTextFields();

                                	
                            } //end if.
                            else {
                                //CEP pesquisado não foi encontrado.
                                limpa_formulário_cep();
                                alert("CEP não encontrado.");
                                
                            }
                        });
                    } //end if.

                } //end if.
                else {
                    //cep sem valor, limpa formulário.
                    limpa_formulário_cep();
                }
            });
        });