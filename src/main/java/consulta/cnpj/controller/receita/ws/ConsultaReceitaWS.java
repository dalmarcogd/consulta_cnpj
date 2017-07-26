package consulta.cnpj.controller.receita.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.google.gson.Gson;

import consulta.cnpj.controller.ConsultaReceita;
import consulta.cnpj.model.PessoaJuridica;

/**
 * Classe respons�vel por consultar um CNPJ no dom�nio receitaws
 * 
 * @author Jeison Dandolini (jeison@publica.inf.br)
 *
 */
public class ConsultaReceitaWS implements ConsultaReceita {

	@Override
	public PessoaJuridica consulta(String cnpj) throws IOException {
		if (cnpj == null) {
			return null;
		}
		URL url = new URL("http://www.receitaws.com.br/v1/cnpj/" + cnpj);
		
		URLConnection openConnection = url.openConnection();
		if (openConnection == null) {
			throw new IOException("N�o foi poss�vel conectar com o servi�o de consulta de CNPJ receitaws");
		}
		InputStream is = openConnection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		
		Gson gson = new Gson();
		
		//Transforma o resultado vindo em um objeto
		ReceitaWSCNPJ obj = gson.fromJson(reader, ReceitaWSCNPJ.class);
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
		pessoa.setEnderecoEletronico(obj.getEmail());
		pessoa.setTelefone(obj.getTelefone());
		
		return pessoa;
	}
}