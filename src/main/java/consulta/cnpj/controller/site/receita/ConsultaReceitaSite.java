package consulta.cnpj.controller.site.receita;

import java.awt.BorderLayout;
import java.io.IOException;
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
import org.jsoup.select.Elements;

import consulta.cnpj.controller.ConsultaReceita;
import consulta.cnpj.model.Fornecedor;

public class ConsultaReceitaSite implements ConsultaReceita {

	HttpClientBuilder cliente = null;
	BasicCookieStore cookie = null;
	BasicHttpContext contexto = null;
	HttpResponse resposta = null;
	
	public ConsultaReceitaSite() {
	}

	@Override
	public Fornecedor consulta(String cnpj) {
		
		configCliente();
		ReceitaFederalConsulta captcha = getCaptcha();
		// Dê algum jeito de mostrar isso para o usuário e pegar o retorno
		JFrame frame = new JFrame();
		// Esse código CRIA A IMAGEM DO CAPTCHA
		JLabel label = new JLabel(new ImageIcon(captcha.getCaptcha()));
		frame.getContentPane().add(label, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
		//entrada do texto do captcha
		String captchaStr = JOptionPane.showInputDialog("Digite o captcha");
		frame.setVisible(false);
		frame = null;
		captcha.setRespostaCaptcha(captchaStr);
		
		ReceitaFederalConsulta resultadoConsulta = getResultadoConsulta(captcha, cliente, contexto, resposta);
		
		
		Fornecedor fornecedor  = processaResultaConsulta(resultadoConsulta);
		
		
		return fornecedor;
	}
	
	private Fornecedor processaResultaConsulta(ReceitaFederalConsulta resultadoConsulta) {
		String htmlResultaConsulta = resultadoConsulta.getHtmlResultaConsulta();
		Document doc = Jsoup.parse(htmlResultaConsulta, "UTF-8");
		Elements element = doc.getElementsContainingOwnText("form");
		
		System.out.println(element.text());
		System.out.println(htmlResultaConsulta);
		return null;
	}

	/**
	 * Configura o cliente para comunicação. 
	 */
    private void configCliente() {
    	RequestConfig.Builder requestBuilder = RequestConfig.custom();
    	
    	requestBuilder = requestBuilder.setConnectTimeout(1000000000*10000)
    								   .setSocketTimeout(1000000000*10000)
    								   .setConnectionRequestTimeout(1000000000*10000)
    								   .setCircularRedirectsAllowed(true);
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
            HttpGet requisicao1 = new HttpGet("http://www.receita.fazenda.gov.br/pessoajuridica/cnpj/cnpjreva/cnpjreva_solicitacao2.asp");
            // Resposta
            resposta = cliente.build().execute(requisicao1, contexto);
            // Buscando a entidade
            HttpEntity entidade = resposta.getEntity();
            //http://www.receita.fazenda.gov.br/pessoajuridica/cnpj/cnpjreva/captcha/gerarCaptcha.asp
            // Crio a segunda requisição  
            HttpGet requisicao2 = new HttpGet("http://www.receita.fazenda.gov.br/pessoajuridica/cnpj/cnpjreva/captcha/gerarCaptcha.asp");
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
    private ReceitaFederalConsulta getResultadoConsulta(ReceitaFederalConsulta receitaFederalConsulta, HttpClientBuilder cliente, BasicHttpContext contexto, HttpResponse resposta) {
    	try {
	    	HttpPost requisicaoConsulta = new HttpPost("http://www.receita.fazenda.gov.br/pessoajuridica/cnpj/cnpjreva/valida.asp");
	        // Lista de parâmetros  
	        List<NameValuePair> nameValuePairs = new ArrayList<>();
	        // Adicionando os parâmetros  
	        nameValuePairs.add(new BasicNameValuePair("origem", "comprovante"));  
	        nameValuePairs.add(new BasicNameValuePair("search_type", "cnpj"));  
	        nameValuePairs.add(new BasicNameValuePair("cnpj", "8264716501196"));    
	        nameValuePairs.add(new BasicNameValuePair("txtTexto_captcha_serpro_gov_br", receitaFederalConsulta.getRespostaCaptcha().toLowerCase()));
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