package com.a_smart_cookie.dao;

import com.a_smart_cookie.dao.mysql.MysqlDaoFactory;
import com.a_smart_cookie.exception.DaoException;
import org.apache.log4j.Logger;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Main interface for implementing Factory pattern for producing daos.
 * Produces requested dao family with use of daoFactoryFCN.
 * Uses also Singleton pattern.
 */
public abstract class DaoFactory {

	private static final Logger LOG = Logger.getLogger(DaoFactory.class);

	private static DaoFactory instance;
	private static final Lock LOCK = new ReentrantLock();
	private static final String daoFactoryFCN;

	static {
		daoFactoryFCN = MysqlDaoFactory.class.getName();
		LOG.trace("Instantiated daoFactoryFCN --> " + daoFactoryFCN);
	}

	protected DaoFactory() {
	}

	/**
	 * Gets singleton instance of DaoFactory. Initializes this instance lazily.
	 *
	 * @return Instance of DaoFactory
	 */
	public static DaoFactory getInstance() throws DaoException {
		LOG.debug("DaoFactory starts getting instance");
		try {
			LOCK.lock();
			LOG.trace("Performed lock on DaoFactory");

			if (instance == null) {
				LOG.trace("First initializing of instance starts.");
				try {
					Class<?> clazz = Class.forName(DaoFactory.daoFactoryFCN);
					instance = (DaoFactory) clazz.getDeclaredConstructor().newInstance();
					LOG.trace("First initializing of instance finished.");
				} catch (ReflectiveOperationException ex) {
					LOG.error("Can't obtain the instance DaoFactory with " + daoFactoryFCN + " FCN", ex);
					throw new DaoException("Can't obtain the instance DaoFactory with " + daoFactoryFCN + " FCN", ex);
				}
			}
		} finally {
			LOCK.unlock();
			LOG.trace("Performed unlock on DaoFactory");
		}
		LOG.debug("DaoFactory finished getting instance");
		return instance;
	}

	/**
	 * Method for getting instance of PublicationDao.
	 *
	 * @return Instance of requested implementation of PublicationDao.
	 */
	public abstract PublicationDao getPublicationDao();

	/**
	 * Method for getting instance of GenreDao.
	 *
	 * @return Instance of requested implementation of GenreDao.
	 */
	public abstract GenreDao getGenreDao();

}
