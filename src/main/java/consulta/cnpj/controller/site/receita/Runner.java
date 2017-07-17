package consulta.cnpj.controller.site.receita;

import consulta.cnpj.controller.ConsultaCNPJController;

public final class Runner {

	public static void main(String[] args) {
		try {
			new ConsultaCNPJController().consultaSiteReceita("83648477001853");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
