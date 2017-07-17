package consulta.cnpj.controller;

import consulta.cnpj.controller.receita.ws.ConsultaReceitaWS;
import consulta.cnpj.controller.sefaz.ConsultaReceitaSefaz;
import consulta.cnpj.controller.site.receita.ConsultaReceitaSite;
import consulta.cnpj.model.Fornecedor;

/**
 * Classe respons�vel por consultar o CNPJ.
 *  
 */
public final class ConsultaCNPJController {

	/**
	 * Realiza a consulta no site do sefaz
	 */
	public Fornecedor consultaSefaz(String cnpj, String certificadoDigital, String url) throws Exception {
		ConsultaReceitaSefaz consultaReceitaSite = new ConsultaReceitaSefaz(certificadoDigital);
		return consultaReceitaSite.consulta(cnpj);
	}
	
	/**
	 * Realiza a consulta no site https://receitaws.com.br/
	 */
	public Fornecedor consultaReceitaWS(String cnpj) throws Exception {
		ConsultaReceitaWS consultaReceitaWS = new ConsultaReceitaWS();
		return consultaReceitaWS.consulta(cnpj);
	}
	
	/**
	 * Realiza a consulta no site da receita.
	 */
	public Fornecedor consultaSiteReceita(String cnpj) throws Exception {
		ConsultaReceitaSite consultaReceitaSite = new ConsultaReceitaSite();
		return consultaReceitaSite.consulta(cnpj);
	}
	
	/**
	 * Realiza todas as consultas at� encontrar o fornecedor.
	 * 1 - Sefaz se existir ceritifcado digital.
	 * 2 - ReceitaWS, servi�o privado.
	 * 3 - Site da receita porem solicita captcha. 
	 */
	public Fornecedor consulta(String cnpj, String certificadoDigital, String url) {
		Fornecedor consultaFornecedor = null;
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