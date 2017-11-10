package br.senac.tads4.piiv.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.caelum.stella.boleto.Banco;
import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Endereco;
import br.com.caelum.stella.boleto.Pagador;
import br.com.caelum.stella.boleto.bancos.BancoDoBrasil;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;
import br.senac.tads4.piiv.model.Pedido;
import br.senac.tads4.piiv.repository.PedidoRepository;

@Controller
@RequestMapping("/boleto")
public class BoletoController {

	public static final Long VENCIMENTO_BOLETO = 5L;
	
	public static final Long PAGO_ATE = 30L;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	/**
	 * Método para visualizar o boleto bancário
	 * 
	 * @param codigo
	 * @return
	 */
	@RequestMapping(value = "/visualizar/{codigo}")
	public ResponseEntity<byte[]> visualizarBoleto(@PathVariable Long codigo) {
		Pedido pedido = pedidoRepository.findOne(codigo);
		
		// Não gerar boleto se já tiver passado o prazo de pagamento
		if (pedido.getDataPedido().plusDays(VENCIMENTO_BOLETO + PAGO_ATE).isBefore(LocalDate.now())) {
			return null;
		}
		
		byte[] boleto = this.gerarBoletoPedido(pedido);
		
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		
		return new ResponseEntity<byte[]>(boleto, headers, HttpStatus.CREATED);
	}
	
	/**
	 * Gera um boleto bancário com as informações do pedido
	 * 
	 * @param pedido
	 * @return
	 */
	private byte[] gerarBoletoPedido(Pedido pedido) {
		LocalDate vencimento = pedido.getDataPedido().plusDays(VENCIMENTO_BOLETO);
		
		Datas datas = Datas.novasDatas()
                .comDocumento(pedido.getDataPedido().getDayOfMonth(), pedido.getDataPedido().getMonthValue(), pedido.getDataPedido().getYear())
                .comProcessamento(pedido.getDataPedido().getDayOfMonth(), pedido.getDataPedido().getMonthValue(), pedido.getDataPedido().getYear())
                .comVencimento(vencimento.getDayOfMonth(), vencimento.getMonthValue(), vencimento.getYear());

        Endereco enderecoBeneficiario = Endereco.novoEndereco()
        		.comLogradouro("Rua Teste, 123, 10º Andar")
        		.comBairro("Bairro Teste")
        		.comCep("01234-678")
        		.comCidade("São Paulo")
        		.comUf("SP");

        // Quem emite o boleto
        Beneficiario beneficiario = Beneficiario.novoBeneficiario()
                .comNomeBeneficiario("The Code - Loja de Games")
                .comAgencia("1824").comDigitoAgencia("4")
                .comCodigoBeneficiario("76000")
                .comDigitoCodigoBeneficiario("5")
                .comNumeroConvenio("1207113")
                .comCarteira("18")
                .comEndereco(enderecoBeneficiario)
                .comNossoNumero("9000206");

        String logradouroPagador = "";
        if (!StringUtils.isEmpty(pedido.getCliente().getEnderecos().get(0).getComplemento())) {
        	logradouroPagador = pedido.getCliente().getEnderecos().get(0).getLogradouro() + ", " + pedido.getCliente().getEnderecos().get(0).getNumero() + " - " + pedido.getCliente().getEnderecos().get(0).getComplemento(); 
        } else {
        	logradouroPagador = pedido.getCliente().getEnderecos().get(0).getLogradouro() + ", " + pedido.getCliente().getEnderecos().get(0).getNumero();
        }
        
        Endereco enderecoPagador = Endereco.novoEndereco()
        		.comLogradouro(logradouroPagador)
        		.comBairro(pedido.getCliente().getEnderecos().get(0).getBairro())
        		.comCep(pedido.getCliente().getEnderecos().get(0).getCep())
        		.comCidade(pedido.getCliente().getEnderecos().get(0).getCidade())
        		.comUf(pedido.getCliente().getEnderecos().get(0).getUf());
        
        // Quem paga o boleto
        Pagador pagador = Pagador.novoPagador()
                .comNome(pedido.getCliente().getNome())
                .comDocumento(pedido.getCliente().getCpf())
                .comEndereco(enderecoPagador);

        Banco banco = new BancoDoBrasil();
        
        LocalDate pagoAte = vencimento.plusDays(PAGO_ATE);
        
        Boleto boleto = Boleto.novoBoleto()
                .comBanco(banco)
                .comDatas(datas)
                .comBeneficiario(beneficiario)
                .comPagador(pagador)
                .comValorBoleto(pedido.getValorTotal().toString())
                .comNumeroDoDocumento(pedido.getId().toString())
                .comInstrucoes("Não receber após " + pagoAte.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), "Após o vencimento cobrar multa de 2,00%", "Após o vencimento cobrar juros de mora de 1,00% ao mês")
                .comLocaisDePagamento("Pagável em qualquer agência bancária");

        GeradorDeBoleto gerador = new GeradorDeBoleto(boleto);
        
        gerador.geraPDF("BancoDoBrasil.pdf");
  
        byte[] bPDF = gerador.geraPDF();
        
        return bPDF;
	}
	
	
}
