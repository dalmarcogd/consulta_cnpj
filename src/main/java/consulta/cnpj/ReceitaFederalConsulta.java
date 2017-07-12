package consulta.cnpj;

public class ReceitaFederalConsulta {

	private String captchaBase64;
	private String captcha;

	public void setImagemCaptcha(String captchaBase64) {
		this.captchaBase64 = captchaBase64;
	}

	public void setCaptcha(String object) {
		this.captcha = object;
	}

	public String getCaptcha() {
		return this.captcha;
	}

}
