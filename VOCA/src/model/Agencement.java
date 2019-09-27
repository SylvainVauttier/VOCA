package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Agencement {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int oid;
	
	private boolean active = true;
	
	private Thing iot;
	
	private String date;
	
	private Scenario scenario;
	
	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Thing getIot() {
		return iot;
	}

	public void setIot(Thing iot) {
		this.iot = iot;
	}
}
