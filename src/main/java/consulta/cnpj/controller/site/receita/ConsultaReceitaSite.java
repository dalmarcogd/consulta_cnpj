package consulta.cnpj.controller.site.receita;

import java.awt.BorderLayout;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.xerces.impl.dv.util.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import consulta.cnpj.controller.ConsultaReceita;
import consulta.cnpj.model.PessoaJuridica;

/**
 * Classe de consulta da receita.
 * 
 * @author guilherme.dalmarco
 */
public class ConsultaReceitaSite implements ConsultaReceita {

	HttpClientBuilder cliente = null;
	BasicCookieStore cookie = null;
	BasicHttpContext contexto = null;
	HttpResponse resposta = null;

	public ConsultaReceitaSite() {
	}

	@Override
	public PessoaJuridica consulta(String cnpj) {
		configCliente();
		ReceitaFederalConsulta captcha = getCaptcha();
		// Dê algum jeito de mostrar isso para o usuário e pegar o retorno
		JFrame frame = new JFrame();
		// Esse código CRIA A IMAGEM DO CAPTCHA
		JLabel label = new JLabel(new ImageIcon(captcha.getCaptcha()));
		frame.getContentPane().add(label, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
		// entrada do texto do captcha
		String captchaStr = JOptionPane.showInputDialog("Digite o captcha");
		frame.setVisible(false);
		frame = null;
		captcha.setRespostaCaptcha(captchaStr);

		ReceitaFederalConsulta resultadoConsulta = getResultadoConsulta(captcha, cliente, contexto, resposta);

		PessoaJuridica fornecedor = processaResultaConsulta(resultadoConsulta);

		return fornecedor;
	}

	private PessoaJuridica processaResultaConsulta(ReceitaFederalConsulta resultadoConsulta) {
		String htmlResultaConsulta = resultadoConsulta.getHtmlResultaConsulta();
		Document doc = Jsoup.parse(htmlResultaConsulta, "UTF-8");

		String texto = doc.text();
		System.out.println(texto);
		int index = 0;
		// CNPJ
		int pos1 = texto.indexOf("NÚMERO DE INSCRIÇÃO", index);
		index = pos1;
		int pos2 = texto.indexOf("COMPROVANTE DE INSCRIÇÃO E DE SITUAÇÃO CADASTRAL", index);
		index = pos2;
		String cnpj = texto.substring(pos1 + "NÚMERO DE INSCRIÇÃO".length(), pos2);
		System.out.println("CNPJ: " + cnpj);
		// Data de abertura
		pos1 = texto.indexOf("DATA DE ABERTURA", index);
		index = pos1;
		pos2 = texto.indexOf("NOME EMPRESARIAL", index);
		index = pos2;
		String dataAbertura = texto.substring(pos1 + "DATA DE ABERTURA".length(), pos2);
		// Razao social
		pos1 = texto.indexOf("NOME EMPRESARIAL", index);
		index = pos1;
		pos2 = texto.indexOf("TÍTULO DO ESTABELECIMENTO (NOME DE FANTASIA)", index);
		index = pos2;
		String razaoSocial = texto.substring(pos1 + "NOME EMPRESARIAL".length(), pos2);
		System.out.println("Razao social: " + razaoSocial);
		// Nome fantasia
		pos1 = texto.indexOf("TÍTULO DO ESTABELECIMENTO (NOME DE FANTASIA)", index);
		index = pos1;
		pos2 = texto.indexOf("CÓDIGO E DESCRIÇÃO DA ATIVIDADE ECONÔMICA PRINCIPAL", index);
		index = pos2;
		String nomeFantasia = texto.substring(pos1 + "TÍTULO DO ESTABELECIMENTO (NOME DE FANTASIA)".length(), pos2);
		System.out.println("Nome fantasia: " + nomeFantasia);
		// Endereço
		// Rua
		pos1 = texto.indexOf("LOGRADOURO", index);
		index = pos1;
		pos2 = texto.indexOf("NÚMERO", index);
		index = pos2;
		String rua = texto.substring(pos1 + "LOGRADOURO".length(), pos2);
		System.out.println("Rua: " + rua);
		// Numero
		pos1 = texto.indexOf("NÚMERO", index);
		index = pos1;
		pos2 = texto.indexOf("COMPLEMENTO", index);
		index = pos2;
		String numero = texto.substring(pos1 + "NÚMERO".length(), pos2);
		System.out.println("Número: " + numero);
		// Complemento
		pos1 = texto.indexOf("COMPLEMENTO", index);
		index = pos1;
		pos2 = texto.indexOf("CEP", index);
		index = pos2;
		String complemento = texto.substring(pos1 + "COMPLEMENTO".length(), pos2);
		System.out.println("Complemento: " + complemento);
		// Cep
		pos1 = texto.indexOf("CEP", index);
		index = pos1;
		pos2 = texto.indexOf("BAIRRO/DISTRITO", index);
		index = pos2;
		String cep = texto.substring(pos1 + "CEP".length(), pos2);
		System.out.println("CEP: " + cep);
		// Bairro
		pos1 = texto.indexOf("BAIRRO/DISTRITO", index);
		index = pos1;
		pos2 = texto.indexOf("MUNICÍPIO", index);
		index = pos2;
		String bairro = texto.substring(pos1 + "BAIRRO/DISTRITO".length(), pos2);
		System.out.println("Bairro: " + bairro);
		// Municipio
		pos1 = texto.indexOf("MUNICÍPIO", index);
		index = pos1;
		pos2 = texto.indexOf("UF", index);
		index = pos2;
		String municipio = texto.substring(pos1 + "MUNICÍPIO".length(), pos2);
		System.out.println("Municipio: " + municipio);
		// UF
		pos1 = texto.indexOf("UF", index);
		index = pos1;
		pos2 = texto.indexOf("ENDEREÇO ELETRÔNICO", index);
		index = pos2;
		String uf = texto.substring(pos1 + "UF".length(), pos2);
		System.out.println("UF: " + uf);
		// ENDEREÇO ELETRÔNICO
		pos1 = texto.indexOf("ENDEREÇO ELETRÔNICO", index);
		index = pos1;
		pos2 = texto.indexOf("TELEFONE", index);
		index = pos2;
		String enderecoEletronico = texto.substring(pos1 + "ENDEREÇO ELETRÔNICO".length(), pos2);
		System.out.println("Endereço eletronico: " + enderecoEletronico);
		// Telefone
		pos1 = texto.indexOf("TELEFONE", index);
		index = pos1;
		pos2 = texto.indexOf("ENTE FEDERATIVO RESPONSÁVEL", index);
		index = pos2;
		String telefone = texto.substring(pos1 + "TELEFONE".length(), pos2);
		System.out.println("Telefone:" + telefone);
		
		
		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		pessoaJuridica.setCnpj(cnpj.trim());
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			pessoaJuridica.setDataAbertura(formatter.parse(dataAbertura.trim().substring(0, 10)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		pessoaJuridica.setRazaoSocial(razaoSocial.trim());
		pessoaJuridica.setNomeFantasia(nomeFantasia.trim());
		pessoaJuridica.setRua(rua.trim());
		pessoaJuridica.setNumero(numero.trim());
		pessoaJuridica.setComplemento(complemento.trim());
		pessoaJuridica.setCep(cep.trim());
		pessoaJuridica.setBairro(bairro.trim());
		pessoaJuridica.setMunicipio(municipio.trim());
		pessoaJuridica.setEstado(uf.trim());

		return pessoaJuridica;
	}

	/**
	 * Configura o cliente para comunicação.
	 */
	private void configCliente() {
		RequestConfig.Builder requestBuilder = RequestConfig.custom();

		requestBuilder = requestBuilder.setConnectTimeout(1000000000 * 10000).setSocketTimeout(1000000000 * 10000)
				.setConnectionRequestTimeout(1000000000 * 10000).setCircularRedirectsAllowed(true);
		// Criando o cliente
		cliente = HttpClientBuilder.create();
		cliente.setDefaultRequestConfig(requestBuilder.build());

		// Adicionando um sistema de redireção
		cliente.setRedirectStrategy(new LaxRedirectStrategy());
		// Mantendo a conexão sempre ativa
		cliente.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
		// Criando o container de cookies
		cookie = new BasicCookieStore();
		// Criando o contexto de conexão
		contexto = new BasicHttpContext();
		// Adicionando o coockie store no contexto de conexão
		contexto.setAttribute(HttpClientContext.COOKIE_STORE, cookie);
	}

	/**
	 * Recupera o captcha para autenticação da consulta.
	 */
	private ReceitaFederalConsulta getCaptcha() {
		ReceitaFederalConsulta receitaFederalConsulta = new ReceitaFederalConsulta();
		try {
			// Criando o método de acesso
			HttpGet requisicao1 = new HttpGet(
					"http://www.receita.fazenda.gov.br/pessoajuridica/cnpj/cnpjreva/cnpjreva_solicitacao2.asp");
			// Resposta
			resposta = cliente.build().execute(requisicao1, contexto);
			// Buscando a entidade
			HttpEntity entidade = resposta.getEntity();
			// http://www.receita.fazenda.gov.br/pessoajuridica/cnpj/cnpjreva/captcha/gerarCaptcha.asp
			// Crio a segunda requisição
			HttpGet requisicao2 = new HttpGet(
					"http://www.receita.fazenda.gov.br/pessoajuridica/cnpj/cnpjreva/captcha/gerarCaptcha.asp");
			// resposta
			resposta = cliente.build().execute(requisicao2, contexto);
			// Buscando a entidade
			entidade = resposta.getEntity();
			// obtendo os bytes da imagem
			byte[] captcha = EntityUtils.toByteArray(entidade);
			// converte os bytes da imagem para base64
			String captchaBase64 = Base64.encode(captcha);
			// atribui o valor da imagem ao objeto que sera reotnrado
			receitaFederalConsulta.setCaptchaBase64(captchaBase64);
			receitaFederalConsulta.setCaptcha(captcha);
		} catch (Exception e) {
			System.err.println(e);
		}
		return receitaFederalConsulta;
	}

	/**
	 * Utiliza o captcha e realiza a consulta.
	 */
	private ReceitaFederalConsulta getResultadoConsulta(ReceitaFederalConsulta receitaFederalConsulta,
			HttpClientBuilder cliente, BasicHttpContext contexto, HttpResponse resposta) {
		try {
			HttpPost requisicaoConsulta = new HttpPost(
					"http://www.receita.fazenda.gov.br/pessoajuridica/cnpj/cnpjreva/valida.asp");
			// Lista de parâmetros
			List<NameValuePair> nameValuePairs = new ArrayList<>();
			// Adicionando os parâmetros
			nameValuePairs.add(new BasicNameValuePair("origem", "comprovante"));
			nameValuePairs.add(new BasicNameValuePair("search_type", "cnpj"));
			nameValuePairs.add(new BasicNameValuePair("cnpj", "82647165001196"));
			nameValuePairs.add(new BasicNameValuePair("txtTexto_captcha_serpro_gov_br",
					receitaFederalConsulta.getRespostaCaptcha().toLowerCase()));
			nameValuePairs.add(new BasicNameValuePair("submit1", "Consultar"));

			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");

			requisicaoConsulta.setEntity(urlEncodedFormEntity);

			resposta = cliente.build().execute(requisicaoConsulta, contexto);

			HttpEntity entidade = resposta.getEntity();

			String html = EntityUtils.toString(entidade);

			receitaFederalConsulta.setHtmlResultaConsulta(html);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return receitaFederalConsulta;
	}
}