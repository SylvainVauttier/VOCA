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

import model.Huteur;
import model.Objet;



public class DAO {
	
	static DAO dao;
	
	static public DAO getDAO() {
		if (dao==null) dao = new DAO();
		return dao;
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
		server.stop();
	}

	public EntityManager getEM() {
		// TODO Auto-generated method stub
		return em;
	}
	
	public void test() {
		Huteur h =  new Huteur();
		Objet o = new Objet();
		List<Objet> lo = new ArrayList<Objet>();
		lo.add(o);
		h.setObjets(lo);
		//h.addObjet(o);
		em.getTransaction().begin();
		em.persist(h);
		em.getTransaction().commit();
	}

}
