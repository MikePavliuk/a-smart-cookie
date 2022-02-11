package com.a_smart_cookie.service;

import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.impl.ServiceFactoryImpl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class ServiceFactory {

	private static ServiceFactory instance;
	private static final Lock LOCK = new ReentrantLock();
	private static final String serviceFactoryFCN;

	static {
		serviceFactoryFCN = ServiceFactoryImpl.class.getName();
	}

	protected ServiceFactory() {
	}

	public static ServiceFactory getInstance() throws ServiceException {
		try {
			LOCK.lock();
			if (instance == null) {
				try {
					Class<?> clazz = Class.forName(ServiceFactory.serviceFactoryFCN);
					instance = (ServiceFactory) clazz.getDeclaredConstructor().newInstance();
				} catch (ReflectiveOperationException ex) {
					throw new ServiceException("Can't obtain the instance ServiceFactory with " + serviceFactoryFCN + " FCN", ex);
				}
			}
		} finally {
			LOCK.unlock();
		}
		return instance;
	}

	public abstract PublicationService getPublicationService();

}
