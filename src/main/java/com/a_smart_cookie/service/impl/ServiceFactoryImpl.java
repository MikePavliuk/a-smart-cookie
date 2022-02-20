package com.a_smart_cookie.service.impl;

import com.a_smart_cookie.service.*;

/**
 * Regular implementation of ServiceFactory.
 *
 */
public class ServiceFactoryImpl extends ServiceFactory {

	private PublicationService publicationService;
	private UserService userService;
	private PaymentService paymentService;
	private SubscriptionService subscriptionService;

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

	@Override
	public SubscriptionService getSubscriptionService() {
		if (subscriptionService == null) {
			subscriptionService = new SubscriptionServiceImpl();
		}
		return subscriptionService;
	}


}
