package consulta.cnpj.model;

import java.util.Date;

/**
 * Classe que representa o cadastro de pessoa jurídica.
 * 
 * @author André Felipe Bürger (andre.burger@publica.inf.br)
 *
 */
public class PessoaJuridica {
	
	// Dados da pessoa.
	private String cnpj;
	private String razaoSocial;
	private String nomeFantasia;
	private Date dataAbertura;
	
	// Dados do Endereço da pessoa.
	private String rua;
	private String numero;
	private String complemento;
	private String cep;
	private String bairro;
	private String municipio;
	private String estado;
	
	public String getCnpj() {
		return cnpj;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public String getRazaoSocial() {
		return razaoSocial;
	}
	
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	
	public Date getDataAbertura() {
		return dataAbertura;
	}
	
	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}
	
	public String getRua() {
		return rua;
	}
	
	public void setRua(String rua) {
		this.rua = rua;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getComplemento() {
		return complemento;
	}
	
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	public String getCep() {
		return cep;
	}
	
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	public String getBairro() {
		return bairro;
	}
	
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public String getMunicipio() {
		return municipio;
	}
	
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("CNPJ: ")
		  .append(this.getCnpj())
		  .append("\n")
		  .append("Razão Social: ")
		  .append(this.getRazaoSocial())
		  .append("\n")
		  .append("Nome Fantasia: ")
		  .append(this.getNomeFantasia())
		  .append("\n")
		  .append("Endereço: ")
		  .append(this.getRua())
		  .append(", ")
		  .append(this.getNumero())
		  .append(" ")
		  .append(this.getComplemento())
		  .append("\n")
		  .append("Bairro: ")
		  .append(this.getBairro())
		  .append("\n")
		  .append("Cidade: ")
		  .append(this.getMunicipio())
		  .append("\n")
		  .append("Estado: ")
		  .append(this.getEstado())
		  ;
		
		return sb.toString();
	}
	
}
