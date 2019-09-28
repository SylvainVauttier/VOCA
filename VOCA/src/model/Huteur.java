package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Huteur {
	
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
	
	
	public List<Thing> getObjets() {
		return objets;
	}

	public void setObjets(List<Thing> objets) {
		this.objets = objets;
	}

	private List<Thing> objets;
	
	public void addObjet(Thing o)
	{
		getObjets().add(o);
	}
	
	public void removeObjet(Thing t)
	{
		getObjets().remove(t);
	}
	
	private List<Scenario> scenarios;

	public List<Scenario> getScenarios() {
		return scenarios;
	}

	public void setScenarios(List<Scenario> scenarios) {
		this.scenarios = scenarios;
	}
	
	public void addScenario(Scenario s)
	{
		getScenarios().add(s);
	}
	
	public void removeScenario(Scenario s)
	{
		getScenarios().remove(s);
	}

}
