package com.a_smart_cookie.dao;

import java.sql.Connection;

/**
 * Main interface for implementing Dao pattern on method layer.
 *
 */
public abstract class AbstractDao {

	protected Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
