package application;

import java.awt.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

import dao.DAO;
import model.Agencement;
import model.Huteur;
import model.IoTClassNames;
import model.Movement;
import model.Scenario;
import model.Thing;

public class VocaController {

	private Huteur huteurActif;
	private VocaView vocaView;

	Scenario creerScenario() {
		// TODO Auto-generated method stub
		Scenario sc = new Scenario();
		sc.setCreationDate(LocalDateTime.now().toString());
		DAO.getScenarioDAO().persist(sc);
		
		sc.setName("Scénario "+sc.getOid());
		
		huteurActif.addScenario(sc);
		
		return sc;
	}

	public Huteur creerHuteur() {
		// TODO Auto-generated method stub
		Huteur h = new Huteur();
		huteurActif=h;
		DAO.getHuteurDAO().persist(h);
		
		h.setName("Huteur"+h.getOid());
		
		h.setObjets(new ArrayList<Thing>());
		h.setScenarios(new ArrayList<Scenario>());

		return h;
		
	}

	public Thing creerThing(int selectedIoTtool, double x, double y) {
		// TODO Auto-generated method stub
		Thing t = new Thing();
		t.setType(IoTClassNames.names[selectedIoTtool]);
		t.setX(x);
		t.setY(y);
		t.setCreationDate(LocalDateTime.now().toString());
		
		DAO.getThingDAO().persist(t);
		
		huteurActif.addObjet(t);
		

		return t;
	}

	public void supprimerThing(Thing model) {
		// TODO Auto-generated method stub
		model.setDestroyed(true);
		model.setDestructionDate(LocalDateTime.now().toString());
		//FBI : on garde tout l'historique
		//huteurActif.removeObjet(model);
	}

	public void deactivate(Thing model) {
		// TODO Auto-generated method stub
		model.setActivations(model.getActivations()-1);
	    vocaView.currentScenario.dissociate(model);
	    creerAgencement(vocaView.currentScenario,false,model);
	}
	
	private void creerAgencement(Scenario currentScenario, boolean b, Thing model) {
		// TODO Auto-generated method stub
		Agencement ag = new Agencement();
		ag.setScenario(currentScenario);
		ag.setActive(b);
		ag.setIot(model);
		ag.setDate(LocalDateTime.now().toString());
		DAO.getAgencementDAO().persist(ag);
	}

	public void activate(Thing model) {
		// TODO Auto-generated method stub
		model.setActivations(model.getActivations()+1);
		vocaView.currentScenario.associate(model);
		creerAgencement(vocaView.currentScenario,true,model);
	}

	public void setView(VocaView view) {
		// TODO Auto-generated method stub
		this.vocaView=view;
	}

	public void supprimerScenario(Scenario currentScenario) {
		// TODO Auto-generated method stub
		for (Thing t : currentScenario.getThings())
			t.setActivations(t.getActivations()-1);
		currentScenario.setDestructionDate(LocalDateTime.now().toString());
		//FBI itou
		//huteurActif.removeScenario(currentScenario);
	}

	public void moveThing(Thing model, double x, double y) {
		// TODO Auto-generated method stub
		Movement mv = new Movement();
		mv.setX(model.getX());
		mv.setY(model.getY());
		mv.setDate(LocalDateTime.now().toString());
		mv.setThing(model);
		
		DAO.getDAOMovement().persist(mv);
		
		model.setX(x);
		model.setY(y);
		
	}

	
	
}
