package com.a_smart_cookie.listener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Represents the context listener class, which initializes all necessary components.
 *
 */
public class ContextListener implements ServletContextListener {

	private static final Logger LOG = Logger.getLogger(ContextListener.class);

	public void contextDestroyed(ServletContextEvent event) {
		log("Servlet context destruction starts");
		// do nothing
		log("Servlet context destruction finished");
	}

	public void contextInitialized(ServletContextEvent event) {
		log("Servlet context initialization starts");

		ServletContext servletContext = event.getServletContext();
		initLog4J(servletContext);
		initCommandContext();

		log("Servlet context initialization finished");
	}

	/**
	 * Initializes log4j framework.
	 *
	 * @param servletContext External servlet context
	 */
	private void initLog4J(ServletContext servletContext) {
		log("Log4J initialization started");
		try {
			PropertyConfigurator.configure(servletContext.getRealPath(
					"WEB-INF/log4j.properties"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		log("Log4J initialization finished");
	}

	/**
	 * Initializes CommandContext.
	 */
	private void initCommandContext() {
		LOG.debug("Command container initialization started");

		// initialize commands container
		// just load class to JVM
		try {
			Class.forName("com.a_smart_cookie.controller.command.CommandContext");
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}

		LOG.debug("Command container initialization finished");
	}

	private void log(String msg) {
		System.out.println("[ContextListener] " + msg);
	}

}
