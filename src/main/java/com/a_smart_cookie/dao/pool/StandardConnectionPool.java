package com.a_smart_cookie.dao.pool;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Provides with method to get Connection from pool.
 * Also initializes dataSource.
 *
 */
public class StandardConnectionPool {

	private static final Logger LOG = Logger.getLogger(StandardConnectionPool.class);

	private static final String DATASOURCE_NAME = "jdbc/SmartCookieDB";
	private static DataSource dataSource;

	static {
		LOG.debug("StandardConnectionPool starts static initializing dataSource");
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup(DATASOURCE_NAME);
			LOG.debug("StandardConnectionPool starts finished initializing dataSource");
		} catch (NamingException e) {
			LOG.error("Can't initialize datasource", e);
		}
	}

	private StandardConnectionPool() {
	}

	/**
	 * Gets connection from connection pool.
	 *
	 * @return Connection
	 */
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

}
