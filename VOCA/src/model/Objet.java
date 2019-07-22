package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Objet {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int oid;
	
	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
