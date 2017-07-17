package consulta.cnpj.controller;

import consulta.cnpj.model.Fornecedor;

public interface ConsultaReceita {

	public Fornecedor consulta(String cnpj, String... param); 
}
