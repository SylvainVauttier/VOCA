package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.h2.tools.Server;

import model.Agencement;
import model.Huteur;
import model.Scenario;
import model.Thing;



public class DAO {
	
	static DAO dao;
	static DAOGen<Scenario> scdao;
	static DAOGen<Huteur> hdao;
	private static DAOGen<Thing> tdao;
	private static DAOGen<Agencement> agdao;

	
	static public DAO getDAO() {
		if (dao==null) dao = new DAO();
		return dao;
	}
	
	static public DAOGen<Scenario> getScenarioDAO() {
		if (scdao==null) scdao = new DAOGen<Scenario>();
		return scdao;
	}
	
	public static DAOGen<Huteur> getHuteurDAO() {
		// TODO Auto-generated method stub
		if (hdao==null) hdao = new DAOGen<Huteur>();
		return hdao;	
		}
	
	public static DAOGen<Thing> getThingDAO() {
		// TODO Auto-generated method stub
		if (tdao==null) tdao = new DAOGen<Thing>();
		return tdao;	
	}
	
	public static DAOGen<Agencement> getAgencementDAO() {
		// TODO Auto-generated method stub
		if (agdao==null) agdao = new DAOGen<Agencement>();
		return agdao;
	}
	

	Server server = null;
	Connection conn = null;
	EntityManager em;
	
	public DAO() {
		
		try {
			server = Server.createTcpServer(null).start();
			//conn = DriverManager.getConnection("jdbc:h2:~/voca", "sa", "sa");
			//conn.close();
			//server.stop();
			
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("VOCA");
			
			em = factory.createEntityManager();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			server.stop();
		}
	}

	public void stopServer() {
		// TODO Auto-generated method stub
//		try {
//			conn.close();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		// on laisse une petite chance aux modifications d'être écrites dans la base avant fermeture
		em.getTransaction().begin();
		em.getTransaction().commit();
		server.stop();
	}

	public EntityManager getEM() {
		// TODO Auto-generated method stub
		return em;
	}
	
	public void test() {
		Huteur h =  new Huteur();
		Thing o = new Thing();
		List<Thing> lo = new ArrayList<Thing>();
		lo.add(o);
		h.setObjets(lo);
		//h.addObjet(o);
		em.getTransaction().begin();
		em.persist(h);
		em.getTransaction().commit();
	}
}
