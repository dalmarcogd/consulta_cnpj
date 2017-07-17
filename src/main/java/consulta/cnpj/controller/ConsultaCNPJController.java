package consulta.cnpj.controller;

import consulta.cnpj.model.Fornecedor;

/**
 * Classe responsável por consultar o CNPJ.
 *  
 */
public final class ConsultaCNPJController {

	public Fornecedor consultaSefaz(String cnpj, String certificadoDigital) throws Exception {
		return null;
	}
	
	public Fornecedor consultaReceitaWS(String cnpj) throws Exception {
		return null;
	}
	
	public Fornecedor consultaSiteReceita(String cnpj) throws Exception {
		return null;
	}
	
	public Fornecedor consulta(String cnpj, String certificadoDigital) {
		Fornecedor consultaFornecedor = null;
		try {
			consultaFornecedor = this.consultaSefaz(cnpj, certificadoDigital);
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