package com.a_smart_cookie.service.impl;

import com.a_smart_cookie.dao.DaoFactory;
import com.a_smart_cookie.dao.EntityTransaction;
import com.a_smart_cookie.dao.PublicationDao;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.exception.DaoException;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.PublicationService;

import java.util.List;

public class PublicationServiceImpl implements PublicationService {

	@Override
	public List<Publication> findAllPublicationsByLanguage(Language language) throws ServiceException {
		EntityTransaction transaction = new EntityTransaction();
		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();
			transaction.init(publicationDao);
			return publicationDao.findAllByLanguage(language);
		} catch (DaoException e) {
			throw new ServiceException("Can't find all publications in " + language.name().toLowerCase(), e);
		} finally {
			transaction.end();
		}
	}

}
