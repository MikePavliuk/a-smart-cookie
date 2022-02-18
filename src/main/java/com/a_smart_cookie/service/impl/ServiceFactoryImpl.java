package com.a_smart_cookie.service.impl;

import com.a_smart_cookie.service.PaymentService;
import com.a_smart_cookie.service.PublicationService;
import com.a_smart_cookie.service.ServiceFactory;
import com.a_smart_cookie.service.UserService;

/**
 * Regular implementation of ServiceFactory.
 *
 */
public class ServiceFactoryImpl extends ServiceFactory {

	private PublicationService publicationService;
	private UserService userService;
	private PaymentService paymentService;

	@Override
	public PublicationService getPublicationService() {
		if (publicationService == null) {
			publicationService = new PublicationServiceImpl();
		}
		return publicationService;
	}

	@Override
	public UserService getUserService() {
		if (userService == null) {
			userService = new UserServiceImpl();
		}
		return userService;
	}

	@Override
	public PaymentService getPaymentService() {
		if (paymentService == null) {
			paymentService = new PaymentServiceImpl();
		}
		return paymentService;
	}

}
