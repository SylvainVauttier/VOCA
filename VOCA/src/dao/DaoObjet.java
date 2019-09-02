package dao;

import javax.persistence.EntityManager;

import model.Thing;

public class DaoObjet {
	
	static DaoObjet dao = null;
	
	static public DaoObjet getDao()
	{
		if (dao==null) dao = new DaoObjet();
		return dao;
	}
	
	EntityManager em = null;
	
	public DaoObjet() {
		em = DAO.getDAO().getEM();
	}
	
	public void persist(Thing o)
	{
		em.getTransaction().begin();
		em.persist(o);
		em.getTransaction().commit();
	}

}
