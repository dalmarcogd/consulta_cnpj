
package consulta.cnpj.controller;
import javax.swing.JOptionPane;

import consulta.cnpj.controller.receita.ws.ConsultaReceitaWS;
import consulta.cnpj.controller.site.receita.ConsultaReceitaSite;
import consulta.cnpj.model.PessoaJuridica;

/**
 * Classe responsável por consultar o CNPJ.
 *  
 */
public final class ConsultaCNPJController {

	/**
	 * Realiza a consulta no site https://receitaws.com.br/
	 */
	public PessoaJuridica consultaReceitaWS(String cnpj) throws Exception {
		ConsultaReceitaWS consultaReceitaWS = new ConsultaReceitaWS();
		return consultaReceitaWS.consulta(cnpj);
	}
	
	/**
	 * Realiza a consulta no site da receita.
	 */
	public PessoaJuridica consultaSiteReceita(String cnpj) throws Exception {
		ConsultaReceitaSite consultaReceitaSite = new ConsultaReceitaSite();
		return consultaReceitaSite.consulta(cnpj);
	}

	/**
	 * Realiza todas as consultas até encontrar o cadastro.
	 * 1 - ReceitaWS - serviço privado, porém, não necessita de captcha 
	 * 2 - Site da receita, porém solicita captcha. 
	 */
	public PessoaJuridica consulta(String cnpj) {
		cnpj = cnpj.replaceAll("[^0123456789]", "");
		
		try {
			PessoaJuridica consultaPessoa = this.consultaReceitaWS(cnpj);
			if (consultaPessoa != null) {
				return consultaPessoa;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			PessoaJuridica consultaPessoa = this.consultaSiteReceita(cnpj);
			if (consultaPessoa != null) {
				return consultaPessoa;
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Problema", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
}