package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Scenario {
	
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
	
	private List<Thing> things = new ArrayList<Thing>();
	

	public List<Thing> getThings() {
		return things;
	}

	public void setThings(List<Thing> things) {
		this.things = things;
	}

	public void dissociate(Thing model) {
		// TODO Auto-generated method stub
		getThings().remove(model);
	}

	public void associate(Thing model) {
		// TODO Auto-generated method stub
		getThings().add(model);
	}
	
	private String creationDate;
	
	private String destructionDate;

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getDestructionDate() {
		return destructionDate;
	}

	public void setDestructionDate(String destructionDate) {
		this.destructionDate = destructionDate;
	}

}
