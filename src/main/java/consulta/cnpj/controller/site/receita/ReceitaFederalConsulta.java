package consulta.cnpj.controller.site.receita;

import consulta.cnpj.model.PessoaJuridica;

public class ReceitaFederalConsulta {

	private String captchaBase64;
	private byte[] captcha;
	private String respostaCaptcha;
	
	private String htmlResultaConsulta;
	private PessoaJuridica fornecedor;
	
	public String getCaptchaBase64() {
		return captchaBase64;
	}
	
	public void setCaptchaBase64(String captchaBase64) {
		this.captchaBase64 = captchaBase64;
	}
	
	public void setCaptcha(byte[] captcha) {
		this.captcha = captcha;
	}

	public byte[] getCaptcha() {
		return this.captcha;
	}
	
	public String getRespostaCaptcha() {
		return respostaCaptcha;
	}
	
	public void setRespostaCaptcha(String respostaCaptcha) {
		this.respostaCaptcha = respostaCaptcha;
	}
	
	public String getHtmlResultaConsulta() {
		return htmlResultaConsulta;
	}
	
	public void setHtmlResultaConsulta(String htmlResultaConsulta) {
		this.htmlResultaConsulta = htmlResultaConsulta;
	}
	
	public PessoaJuridica getFornecedor() {
		return fornecedor;
	}
	
	public void setFornecedor(PessoaJuridica fornecedor) {
		this.fornecedor = fornecedor;
	}
}