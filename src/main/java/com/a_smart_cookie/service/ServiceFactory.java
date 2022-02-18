package com.a_smart_cookie.service;

import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.impl.ServiceFactoryImpl;
import org.apache.log4j.Logger;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Main interface for implementing Factory pattern for producing services.
 * Produces requested service family with use of serviceFactoryFCN.
 * Uses also Singleton pattern.
 */
public abstract class ServiceFactory {

	private static final Logger LOG = Logger.getLogger(ServiceFactory.class);

	private static ServiceFactory instance;
	private static final Lock LOCK = new ReentrantLock();
	private static final String serviceFactoryFCN;

	static {
		serviceFactoryFCN = ServiceFactoryImpl.class.getName();
		LOG.trace("Instantiated serviceFactoryFCN --> " + serviceFactoryFCN);
	}

	protected ServiceFactory() {
	}

	/**
	 * Gets singleton instance of ServiceFactory. Initializes this instance lazily.
	 *
	 * @return Instance of DaoFactory
	 */
	public static ServiceFactory getInstance() throws ServiceException {
		LOG.debug("ServiceFactory starts getting instance");
		try {
			LOCK.lock();
			if (instance == null) {
				try {
					Class<?> clazz = Class.forName(ServiceFactory.serviceFactoryFCN);
					instance = (ServiceFactory) clazz.getDeclaredConstructor().newInstance();
				} catch (ReflectiveOperationException ex) {
					LOG.error("Can't obtain the instance ServiceFactory with " + serviceFactoryFCN + " FCN", ex);
					throw new ServiceException("Can't obtain the instance ServiceFactory with " + serviceFactoryFCN + " FCN", ex);
				}
			}
		} finally {
			LOCK.unlock();
			LOG.trace("Performed unlock on DaoFactory");
		}
		LOG.debug("ServiceFactory finished getting instance");
		return instance;
	}

	/**
	 * Method for getting instance of PublicationService.
	 *
	 * @return Instance of requested implementation of PublicationService.
	 */
	public abstract PublicationService getPublicationService();

	/**
	 * Method for getting instance of UserService.
	 *
	 * @return Instance of requested implementation of UserService.
	 */
	public abstract UserService getUserService();

	/**
	 * Method for getting instance of PaymentService.
	 *
	 * @return Instance of requested implementation of PaymentService.
	 */
	public abstract PaymentService getPaymentService();

}
