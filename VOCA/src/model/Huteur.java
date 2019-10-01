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
	
private String gender;
	

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	private int age;
	

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	private String zipcode;
	

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	private int fear;
	
	public int getFear() {
		return fear;
	}

	public void setFear(int fear) {
		this.fear = fear;
	}

	private int admiration;
	
	public int getAdmiration() {
		return admiration;
	}

	public void setAdmiration(int admiration) {
		this.admiration = admiration;
	}
	
	private int anxiety;

	public int getAnxiety() {
		return anxiety;
	}

	public void setAnxiety(int anxiety) {
		this.anxiety = anxiety;
	}

	private int interest;

	public int getInterest() {
		return interest;
	}

	public void setInterest(int interest) {
		this.interest = interest;
	}

	private int aversion;
	
	public int getAversion() {
		return aversion;
	}

	public void setAversion(int aversion) {
		this.aversion = aversion;
	}

	private int curiosity;
	

	public int getCuriosity() {
		return curiosity;
	}

	public void setCuriosity(int curiosity) {
		this.curiosity = curiosity;
	}
	
	public void setHuteur (String nickname, String gender, int age, String zip, int[] answers)
	{
		setName(nickname);
		setGender(gender);
		setAge(age);
		setZipcode(zip);
		setFear(answers[0]);
		setAdmiration(answers[1]);
		setAnxiety(answers[2]);
		setInterest(answers[3]);
		setAversion(answers[4]);
		setCuriosity(answers[5]);
	}

}
