package consulta.cnpj;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class ConsultaCnpj {

	public static void main(String[] args) throws FailingHttpStatusCodeException, IOException {
		// Criando o cliente
        DefaultHttpClient cliente = new DefaultHttpClient();
        // Adicionando um sistema de redireção
        cliente.setRedirectStrategy(new LaxRedirectStrategy());
        // Mantendo a conexão sempre ativa
        cliente.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
        // Criando o container de cookies
        BasicCookieStore cookie = new BasicCookieStore();
        // Criando o contexto de conexão
        BasicHttpContext contexto = new BasicHttpContext();
        // Adicionando o coockie store no contexto de conexão
        contexto.setAttribute(ClientContext.COOKIE_STORE, cookie);
        // Criando o método de acesso
        HttpGet requisição1 = new HttpGet("http://www.receita.fazenda.gov.br/pessoajuridica/cnpj/cnpjreva/cnpjreva_solicitacao2.asp");
        // Resposta
        HttpResponse resposta = null;
        try {
            resposta = cliente.execute(requisição1, contexto);
        } catch (IOException exception) {
            // Ocultado
        }
        // Buscando a entidade
        HttpEntity entidade = resposta.getEntity();
        // Transformando o conteúdo em uma string
        String html = null;
        try {
            html = EntityUtils.toString(entidade);
        } catch (IOException | ParseException exception) {
            // Ocultado
        }
        // Busco o documento estruturado
        HTMLDocument document = (new DocumentoHtml()).getHTMLDocument(html);
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
//                imgcaptcha = "http://www.receita.fazenda.gov.br" + ((String) element.getAttributes().getAttribute(HTML.Attribute.SRC)).replaceAll("amp", "");
            	imgcaptcha = "http://www.receita.fazenda.gov.br/pessoajuridica/cnpj/cnpjreva/captcha/gerarCaptcha.asp";
            }
        }
        // Crio a segunda requisição
        HttpGet requisição2 = new HttpGet(imgcaptcha);
        // Resposta
        try {
            resposta = cliente.execute(requisição2, contexto);
        } catch (IOException exception) {
            // Ocultado
        }
        // Crio o captcha que vai receber o código
        String captcha = null;
        // Buscando a entidade
        entidade = resposta.getEntity();
        try {
            // Dê algum jeito de mostrar isso para o usuário e pegar o retorno
            JFrame frame = new JFrame();
            // Esse código CRIA A IMAGEM DO CAPTCHA
	        JLabel label = new JLabel(new ImageIcon(EntityUtils.toByteArray(entidade)));
	        frame.getContentPane().add(label, BorderLayout.CENTER);
	        frame.pack();
	        frame.setVisible(true);
	        //entrada do texto do captcha
	        captcha = JOptionPane.showInputDialog("Digite o captcha");
	        frame.setVisible(false);
	        frame = null;
        } catch (IOException exception) {
            // Ocultado
        }
        // Se o captcha for nulo, pare tudo
        if (captcha == null) {
            // Ocultado
        }
        if (captcha.isEmpty()) {
            // Ocultado
        }
        // Criando o método de acesso
        HttpPost requisição3 = new HttpPost("http://www.receita.fazenda.gov.br/pessoajuridica/cnpj/cnpjreva/valida.asp");
        // Lista de parâmetros
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        // Adicionando os parâmetros
        nameValuePairs.add(new BasicNameValuePair("origem", "comprovante"));
        nameValuePairs.add(new BasicNameValuePair("search_type", "cnpj"));
        nameValuePairs.add(new BasicNameValuePair("cnpj", "95836771000120"));
        // Veja que o Captcha é necessário nos parâmetros
        nameValuePairs.add(new BasicNameValuePair("captcha", captcha));
        nameValuePairs.add(new BasicNameValuePair("captchaAudio", ""));
        nameValuePairs.add(new BasicNameValuePair("submit1", "Consultar"));
        nameValuePairs.add(new BasicNameValuePair("viewstate", viewstate));
        // Encapsulando
        UrlEncodedFormEntity urlEncodedFormEntity = null;
        try {
            // Pelo fonte da página da receita o sistema adequado de parâmetros é em UTF-8
            urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
        } catch (UnsupportedEncodingException exception) {
            // Ocultado
        }
        // A adição dos parâmetros
        requisição3.setEntity(urlEncodedFormEntity);
        try {
            // Resposta
            resposta = cliente.execute(requisição3, contexto);
        } catch (IOException exception) {
            // Ocultado
        }
        // Buscando a entidade
        entidade = resposta.getEntity();
        // Pego o código fonte e jogo na string
        try {
            html = EntityUtils.toString(entidade);
        } catch (IOException | ParseException exception) {
            // Ocultado
        }
        // Busco o documento estruturado
        document = (new DocumentoHtml()).getHTMLDocument(html);
        // Busco todos os elementos em forma de iterador
        elementIterator = new ElementIterator(document);
        // Enquanto existir próximo elemento
        while ((element = elementIterator.next()) != null) {
            // Dentro dos elementos estão as informações para se pegar, porém não tenho autorização de divulgá-las.
            // Use a criatividade que você irá recuperar todos os dados necessários dentro deste While.
        }
	}
		
}
