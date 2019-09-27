package dao;

import javax.persistence.EntityManager;

import model.Thing;

public class DAOGen<T> {
	
	EntityManager em = null;
	
	public DAOGen() {
		em = DAO.getDAO().getEM();
	}
	
	public void persist(T o)
	{
		em.getTransaction().begin();
		em.persist(o);
		em.getTransaction().commit();
	}

}
