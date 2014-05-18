package br.edu.unb.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import br.edu.unb.dao.impl.ManagerConnection;


public class ManagerConnectionListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ManagerConnection.startGraphDbService();
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		ManagerConnection.shutdown();
	}

}
