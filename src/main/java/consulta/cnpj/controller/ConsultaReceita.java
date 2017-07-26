package consulta.cnpj.controller;

import consulta.cnpj.model.PessoaJuridica;

public interface ConsultaReceita {

	/**
	 * Realiza a consulta do CNPJ
	 * @param cnpj - CNPJ
	 * @return
	 * @throws Exception
	 */
	public PessoaJuridica consulta(String cnpj) throws Exception; 
}
