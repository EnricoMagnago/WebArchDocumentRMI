package it.unitn.webarch.storage;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Project: Assignment_5
 * Created by en on 04/11/17.
 */
public class SessionManager{
	private static SessionFactory sessionFactory = null;

	public static Session getSession(){
		if(sessionFactory == null){
			sessionFactory = new Configuration().configure().buildSessionFactory();
		}
		/* using a pool of session would be better */
		return sessionFactory.openSession();
	}

	public static void closeFactory(){
		sessionFactory.close();
		sessionFactory = null;
	}
}
