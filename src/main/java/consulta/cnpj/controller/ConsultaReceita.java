package consulta.cnpj.controller;

import consulta.cnpj.model.PessoaJuridica;

/**
 * Interface com a definição padrão da consulta de CNPJ
 * 
 * @author André Felipe Bürger (andre.burger@publica.inf.br)
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
