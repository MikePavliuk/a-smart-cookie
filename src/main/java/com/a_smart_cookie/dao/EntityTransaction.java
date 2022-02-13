package com.a_smart_cookie.dao;

import com.a_smart_cookie.dao.pool.StandardConnectionPool;
import com.a_smart_cookie.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;

public final class EntityTransaction {

    private Connection connection;

    public void init(AbstractDao dao) throws DaoException {
		initializeConnection();
		dao.setConnection(connection);
    }

	public void end() {
        if (connection != null) {
            ResourceReleaser.close(connection);
        }
    }

    public void initTransaction(AbstractDao dao, AbstractDao... daos) {
		initializeConnection();

		try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
			//log
        }

        dao.setConnection(connection);
        for (AbstractDao daoElement : daos) {
            daoElement.setConnection(connection);
        }

    }

    public void endTransaction() {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
				//log
            }
            ResourceReleaser.close(connection);
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
			//log
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
			//log
        }
    }

	private void initializeConnection() {
		if (connection == null) {
			try {
				connection = StandardConnectionPool.getConnection();
			} catch (SQLException e) {
				//log
			}
		}
	}

}
