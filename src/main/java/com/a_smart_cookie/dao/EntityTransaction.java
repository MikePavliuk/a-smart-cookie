package com.a_smart_cookie.dao;

import com.a_smart_cookie.dao.pool.StandardConnectionPool;
import com.a_smart_cookie.entity.Entity;
import com.a_smart_cookie.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;

public final class EntityTransaction {

    private Connection connection;

    public void init(AbstractDao<? extends Entity<?>> dao) throws DaoException {
		initializeConnection();
		dao.setConnection(connection);
    }

	public void end() {
        if (connection != null) {
            ResourceReleaser.close(connection);
        }
    }

    @SafeVarargs
    public final <T extends Entity<?>> void initTransaction(AbstractDao<T> dao, AbstractDao<T>... daos) throws DaoException {
		initializeConnection();

		try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException("Exception occurred while setting auto commit to false", e);
        }

        dao.setConnection(connection);
        for (AbstractDao<? extends Entity<?>> daoElement : daos) {
            daoElement.setConnection(connection);
        }

    }

    public void endTransaction() throws DaoException {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DaoException("Exception occurred while setting auto commit to true", e);
            }
            ResourceReleaser.close(connection);
        }
    }

    public void commit() throws DaoException {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException("Can't commit changes because of occurred exception", e);
        }
    }

    public void rollback() throws DaoException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DaoException("Can't make a rollback because of occurred exception", e);
        }
    }

	private void initializeConnection() throws DaoException {
		if (connection == null) {
			try {
				connection = StandardConnectionPool.getConnection();
			} catch (SQLException e) {
				throw new DaoException("Can't get connection from pool for initializing transaction", e);
			}
		}
	}

}
