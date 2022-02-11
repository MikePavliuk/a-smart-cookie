package com.a_smart_cookie.service.impl;

import com.a_smart_cookie.service.PublicationService;
import com.a_smart_cookie.service.ServiceFactory;

public class ServiceFactoryImpl extends ServiceFactory {

	private PublicationService publicationService;

	@Override
	public PublicationService getPublicationService() {
		if (publicationService == null) {
			publicationService = new PublicationServiceImpl();
		}
		return publicationService;
	}

}
