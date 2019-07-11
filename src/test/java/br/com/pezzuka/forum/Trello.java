package br.com.pezzuka.forum;

//Classe MODELO que irá receber os dados da API do Trello
public class Trello {

	private String id;
	private String name;
	private String desc;
	private String iddescData;
	private String closed;
	private String idOrganization;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getIddescData() {
		return iddescData;
	}
	public void setIddescData(String iddescData) {
		this.iddescData = iddescData;
	}
	public String getClosed() {
		return closed;
	}
	public void setClosed(String closed) {
		this.closed = closed;
	}
	public String getIdOrganization() {
		return idOrganization;
	}
	public void setIdOrganization(String idOrganization) {
		this.idOrganization = idOrganization;
	}
	
}
