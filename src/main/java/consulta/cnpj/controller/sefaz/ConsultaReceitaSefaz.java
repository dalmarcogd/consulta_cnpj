package consulta.cnpj.controller.sefaz;

import consulta.cnpj.controller.ConsultaReceita;
import consulta.cnpj.model.PessoaJuridica;

public class ConsultaReceitaSefaz implements ConsultaReceita {

	public ConsultaReceitaSefaz(String certificadoDigital) {
	}

	@Override
	public PessoaJuridica consulta(String cnpj) {
		return null;
	}
}