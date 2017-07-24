package consulta.cnpj.controller.receita.ws;

import consulta.cnpj.controller.ConsultaCNPJController;

public final class Runner {

	public static void main(String[] args) {
		try {
			new ConsultaCNPJController().consultaReceitaWS("83648477001853");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
