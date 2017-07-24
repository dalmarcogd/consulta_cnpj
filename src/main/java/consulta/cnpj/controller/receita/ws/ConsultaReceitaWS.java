package consulta.cnpj.controller.receita.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;

import consulta.cnpj.controller.ConsultaReceita;
import consulta.cnpj.model.PessoaJuridica;

public class ConsultaReceitaWS implements ConsultaReceita {

	@Override
	public PessoaJuridica consulta(String cnpj) {
		try {

//			URL url = new URL("http://www.receitaws.com.br/v1/cnpj/82647165001196");
			URL url = new URL("http://www.receitaws.com.br/v1/cnpj/" + cnpj);
			
			InputStream is = url.openConnection().getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			
			Gson gson = new Gson();
			
			CNPJ obj = gson.fromJson(reader, CNPJ.class);
			PessoaJuridica pessoa = new PessoaJuridica();
			pessoa.setBairro(obj.getBairro());
			pessoa.setCep(obj.getCep());
			pessoa.setCnpj(obj.getCnpj());
			pessoa.setComplemento(obj.getComplemento());
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			try {
				pessoa.setDataAbertura(formatter.parse(obj.getAbertura().trim().substring(0, 10)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			pessoa.setEstado(obj.getUf());
			pessoa.setMunicipio(obj.getMunicipio());
			pessoa.setNomeFantasia(obj.getFantasia());
			pessoa.setNumero(obj.getNumero());
			pessoa.setRazaoSocial(obj.getNome());
			pessoa.setRua(obj.getLogradouro());
			
			return pessoa;
			
//			System.out.println(obj);
			
//			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
//			conn.setRequestMethod("GET");
//			
//			conn.connect();
//			
//			System.out.println(conn.getResponseMessage());
//
//			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
		
		return null;
	}
}