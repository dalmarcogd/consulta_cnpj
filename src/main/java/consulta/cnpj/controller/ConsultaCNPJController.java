
package consulta.cnpj.controller;
import javax.swing.JOptionPane;

import consulta.cnpj.controller.receita.ws.ConsultaReceitaWS;
import consulta.cnpj.controller.sefaz.ConsultaReceitaSefaz;
import consulta.cnpj.controller.site.receita.ConsultaReceitaSite;
import consulta.cnpj.model.PessoaJuridica;

/**
 * Classe responsável por consultar o CNPJ.
 *  
 */
public final class ConsultaCNPJController {

	/**
	 * Realiza a consulta no site do sefaz
	 */
	public PessoaJuridica consultaSefaz(String cnpj, String certificadoDigital, String url) throws Exception {
		ConsultaReceitaSefaz consultaReceitaSite = new ConsultaReceitaSefaz(certificadoDigital);
		return consultaReceitaSite.consulta(cnpj);
	}
	
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
	 * Realiza a consulta no site da receita.
	 */
	public PessoaJuridica consultaCnpj(String cnpj) throws Exception {
		PessoaJuridica pessoa = null;
		try {
//			pessoa = this.consultaReceitaWS(cnpj);			
		} catch (Exception e) {
			pessoa = null;
		}
		if (pessoa == null) {
			try {
				pessoa = this.consultaSiteReceita(cnpj);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Problema", JOptionPane.ERROR_MESSAGE);
			}
		}
		return pessoa;
	}
	
	/**
	 * Realiza todas as consultas até encontrar o fornecedor.
	 * 1 - Sefaz se existir ceritifcado digital.
	 * 2 - ReceitaWS, serviço privado.
	 * 3 - Site da receita porem solicita captcha. 
	 */
	public PessoaJuridica consulta(String cnpj, String certificadoDigital, String url) {
		PessoaJuridica consultaFornecedor = null;
		try {
			consultaFornecedor = this.consultaSefaz(cnpj, certificadoDigital, url);
			if (consultaFornecedor != null) {
				return consultaFornecedor;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			consultaFornecedor = this.consultaReceitaWS(cnpj);
			if (consultaFornecedor != null) {
				return consultaFornecedor;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			consultaFornecedor = this.consultaSiteReceita(cnpj);
			if (consultaFornecedor != null) {
				return consultaFornecedor;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return consultaFornecedor;
	}
}