package consulta.cnpj.controller;

import consulta.cnpj.model.PessoaJuridica;

/**
 * Interface com a defini��o padr�o da consulta de CNPJ
 * 
 * @author Andr� Felipe B�rger (andre.burger@publica.inf.br)
 * @author Guilherme Dalmarco (guilherme.dalmarco@publica.inf.br)
 * @author Jeison Dandolini (jeison@publica.inf.br)
 */
public interface ConsultaReceita {

	/**
	 * Realiza a consulta do CNPJ
	 * @param cnpj - CNPJ
	 * @return
	 * @throws Exception
	 */
	public PessoaJuridica consulta(String cnpj) throws Exception; 
}
