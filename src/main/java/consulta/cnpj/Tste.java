package consulta.cnpj;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.text.Element;
import javax.swing.text.ElementIterator;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.xerces.impl.dv.util.Base64;
import org.htmlparser.lexer.Source;
import org.htmlparser.lexer.StringSource;

import net.sourceforge.htmlunit.corejs.javascript.json.JsonParser;

public class Tste {

	private static HttpClientBuilder cliente;
    private static BasicCookieStore cookie;
    private static BasicHttpContext contexto;
    private static HttpResponse resposta;

    public static void main(String[] args) {
		init();
		ReceitaFederalConsulta formInfo = getFormInfo();
		try {
			getValues(formInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    public static void init() {
    	RequestConfig.Builder requestBuilder = RequestConfig.custom();
    	requestBuilder = requestBuilder.setConnectTimeout(1000000000*10000).setSocketTimeout(1000000000*10000).setConnectionRequestTimeout(1000000000*10000);

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

    public static ReceitaFederalConsulta getFormInfo() {
        ReceitaFederalConsulta receitaFederalConsulta = new ReceitaFederalConsulta();
        try {
            // Criando o método de acesso  
            HttpGet requisicao1 = new HttpGet("http://www.receita.fazenda.gov.br/pessoajuridica/cnpj/cnpjreva/cnpjreva_solicitacao2.asp");
            // Resposta
            resposta = cliente.build().execute(requisicao1, contexto);
            // Buscando a entidade
            HttpEntity entidade = resposta.getEntity();
            // Transformando o conteúdo em uma string
            String html = EntityUtils.toString(entidade);
            // Busco o documento estruturado
            HTMLDocument document = DocumentoHtml.getHTMLDocument(html);
            // Busco todos os elementos em forma de iterador
            ElementIterator elementIterator = new ElementIterator(document);
            // Crio o elemento que vai recepcionar
            Element element;
            //Crio o viewstate para receber um valor para o método post futuro
            String viewstate = "";
            // Crio o imgcaptcha para receber um valor do link do captcha
            String imgcaptcha = "";
            // Enquanto existir próximo elemento
            while ((element = elementIterator.next()) != null) {
                 // Se for um input
                 if (element.getName().equals(HTML.Tag.INPUT.toString()) && ((String) element.getAttributes().getAttribute(HTML.Attribute.NAME)).equalsIgnoreCase("viewstate")) {
                     // Passo para a variável o valor do viewstate
                     viewstate = (String) element.getAttributes().getAttribute(HTML.Attribute.VALUE);
                 }
                 // Se for um img
                 if (element.getName().equals(HTML.Tag.IMG.toString()) && ((String) element.getAttributes().getAttribute(HTML.Attribute.ID)).equalsIgnoreCase("imgcaptcha")) {
                     // Passo para a variável o valor do imgcaptcha
                     imgcaptcha = "http://www.receita.fazenda.gov.br" + ((String) element.getAttributes().getAttribute(HTML.Attribute.SRC)).replaceAll("amp", "");
                 }
            }
            //http://www.receita.fazenda.gov.br/pessoajuridica/cnpj/cnpjreva/captcha/gerarCaptcha.asp
            // Crio a segunda requisição  
            HttpGet requisicao2 = new HttpGet("http://www.receita.fazenda.gov.br/pessoajuridica/cnpj/cnpjreva/captcha/gerarCaptcha.asp");
            // resposta
            
            resposta = cliente.build().execute(requisicao2, contexto);
            
            // Buscando a entidade  
            entidade = resposta.getEntity();
            // obtendo os bytes da imagem
            byte[] captcha = EntityUtils.toByteArray(entidade);
            new Base64();
			// converte os bytes da imagem para base64
            String captchaBase64 = Base64.encode(captcha);
            // atribui o valor da imagem ao objeto que sera reotnrado
            receitaFederalConsulta.setImagemCaptcha(captchaBase64);
            
            // Dê algum jeito de mostrar isso para o usuário e pegar o retorno
            JFrame frame = new JFrame();
            // Esse código CRIA A IMAGEM DO CAPTCHA
	        JLabel label = new JLabel(new ImageIcon(captcha));
	        frame.getContentPane().add(label, BorderLayout.CENTER);
	        frame.pack();
	        frame.setVisible(true);
	        //entrada do texto do captcha
	        String captchaStr = JOptionPane.showInputDialog("Digite o captcha");
	        frame.setVisible(false);
	        frame = null;
            
            receitaFederalConsulta.setCaptcha(captchaStr);
        } catch (Exception e) {
            System.err.println(e);
        }
        return receitaFederalConsulta;
    }
    public static ReceitaFederalConsulta getValues(ReceitaFederalConsulta receitaFederalConsulta) throws Exception {
        // Criando o método de acesso  
        HttpPost requisicao3 = new HttpPost("http://www.receita.fazenda.gov.br/pessoajuridica/cnpj/cnpjreva/valida.asp");
        // Lista de parâmetros  
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        // Adicionando os parâmetros  
        nameValuePairs.add(new BasicNameValuePair("origem", "comprovante"));  
        nameValuePairs.add(new BasicNameValuePair("search_type", "cnpj"));  
        nameValuePairs.add(new BasicNameValuePair("cnpj", "8264716501196"));    
        nameValuePairs.add(new BasicNameValuePair("txtTexto_captcha_serpro_gov_br", receitaFederalConsulta.getCaptcha().toLowerCase()));
        nameValuePairs.add(new BasicNameValuePair("submit1", "Consultar"));
        // Encapsulando  
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
        requisicao3.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        // definindo a entidade
        requisicao3.setEntity(urlEncodedFormEntity);  
        // resposta
        resposta = cliente.build().execute(requisicao3, contexto);  
        // Buscando a entidade  
        HttpEntity entidade = resposta.getEntity();  
        // Transformando o conteúdo em uma string  
        String html = EntityUtils.toString(entidade);
        
        String sourceUrlString="data/test.html";
        if (html.length()==0)
          System.err.println("Using default argument of \""+sourceUrlString+'"');
        else
            sourceUrlString=html;
        if (sourceUrlString.indexOf(':')==-1) sourceUrlString="file:"+sourceUrlString;
        Source source=new StringSource(html);
        String renderedText=source.getEncoding().toString();
        System.out.println("\nSimple rendering of the HTML document:\n");
        System.out.println(renderedText);
        
        System.out.println(html);
        // Busco o documento estruturado  
        HTMLDocument document = DocumentoHtml.getHTMLDocument(html); 
        System.out.println(html.replaceAll("\\<.*?>.",""));
        // Busco todos os elementos em forma de iterador  
        ElementIterator elementIterator = new ElementIterator(document);
        setValues(elementIterator, receitaFederalConsulta);
//        if (StringUtils.isBlank(receitaFederalConsulta.getNumeroDeInscricao())) {
//            throw new Exception("Caracteres inválido, uma nova imagem foi gerada.");
//        }
//        if (StringUtils.isNotBlank(receitaFederalConsulta.getCidade()) && StringUtils.isNotBlank(receitaFederalConsulta.getUf())) {
//            receitaFederalConsulta.setCidadeEntity(cidadeService.getByNomeAndUf(receitaFederalConsulta.getCidade(), receitaFederalConsulta.getUf()));
//        }
        return receitaFederalConsulta;
    }

	private static void setValues(ElementIterator elementIterator, ReceitaFederalConsulta receitaFederalConsulta) {
		Element element;
		while ((element = elementIterator.next()) != null) {
//			System.err.println(element);
		}
	}
} 
