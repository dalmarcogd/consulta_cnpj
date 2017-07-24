package consulta.cnpj.controller.receita.ws;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import consulta.cnpj.controller.ConsultaReceita;
import consulta.cnpj.model.PessoaJuridica;

public class ConsultaReceitaWS implements ConsultaReceita {

	@Override
	public PessoaJuridica consulta(String cnpj) {
		try {

			URL url = new URL("https://www.receitaws.com.br/v1/cnpj/82647165001196");
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			
			conn.connect();
			
			System.out.println(conn.getResponseMessage());

			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
		
		return null;
	}
}