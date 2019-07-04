package br.com.pezzuka.forum.model.enums;

public enum PerfilEnum {
	
	 ADMIN("Administrador"),
	 BPO("BPO"),
	 AUX("aux");
	 
	private String descricao;
	 
	PerfilEnum(String descricao) {
        this.descricao = descricao;
    }
 
    public String getDescricao() {
        return descricao;
    }
	
}
