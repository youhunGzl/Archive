package com.archive.utility;

import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Transaction;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class HibernateUtility {
	private static Logger logger = Logger.getLogger(HibernateUtility.class.getName());
	/** 
     * Location of hibernate.cfg.xml file.
     * Location should be on the classpath as Hibernate uses  
     * #resourceAsStream style lookup for its configuration file. 
     * The default classpath location of the hibernate config file is 
     * in the default package. Use #setConfigFile() to update 
     * the location of the configuration file for the current session.   
     */
    private static org.hibernate.SessionFactory sessionFactory;
    
	
    private static Configuration configuration = new Configuration();
    private static ServiceRegistry serviceRegistry; 

	static {
    	try {
			configuration.configure();
			serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
    }

	 public static Session getSession() throws HibernateException {
				/*if (sessionFactory == null) {
					rebuildSessionFactory();
				}
				session = (sessionFactory != null) ? sessionFactory.openSession()
						: null;*/
				Session session = sessionFactory.openSession();
				beginTransaction(session);
				
			
			return session;
	}

	public static void rollbackTransaction(Session session){
			session.close();
		session.getTransaction().rollback();
	}
	 
	public static void beginTransaction(Session session){
		
		session.beginTransaction();
	}
	
	public static void commitTransaction(Session session){
		
		session.getTransaction().commit();
	}
	
	

	public static void rebuildSessionFactory() {
		try {
			configuration.configure();
			serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}


	
	public static void exceptionOutput(Exception e,HttpServletRequest request, HttpServletResponse response){
		String result = null;
		JsonObject jobj = new JsonObject();
		jobj.addProperty("message", e.toString());
		Gson gson = new Gson();
		result = gson.toJson(jobj);
		
		response.setContentType("type=text/html;charset=utf-8");		
		PrintWriter pw = null;
		try{
		    pw = response.getWriter();
			pw.print(result); 
			pw.close();
		}catch(Exception e1){
			logger.log(Level.SEVERE, e1.toString());
			e1.printStackTrace();
		}
	}
}
