package com.a_smart_cookie.service.impl;

import com.a_smart_cookie.adapter.filtering_data.catalog.CountRowsParameters;
import com.a_smart_cookie.adapter.filtering_data.catalog.FilterParameters;
import com.a_smart_cookie.dao.DaoFactory;
import com.a_smart_cookie.dao.EntityTransaction;
import com.a_smart_cookie.dao.PublicationDao;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.exception.DaoException;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.PublicationService;

import java.util.List;

public class PublicationServiceImpl implements PublicationService {

	@Override
	public List<Publication> findPublicationsByFilterParameters(FilterParameters filterParameters) throws ServiceException {
		EntityTransaction transaction = new EntityTransaction();
		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();
			transaction.init(publicationDao);
			return publicationDao.findPublicationsByFilterParameters(filterParameters);
		} catch (DaoException e) {
			throw new ServiceException("Can't find publication with " + filterParameters, e);
		} finally {
			transaction.end();
		}
	}

	@Override
	public int getTotalNumberOfRequestedQueryRows(CountRowsParameters countRowsParameters) throws ServiceException {
		EntityTransaction transaction = new EntityTransaction();
		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();
			transaction.init(publicationDao);
			return publicationDao.getTotalNumberOfRequestedQueryRows(countRowsParameters);
		} catch (DaoException e) {
			throw new ServiceException("Can't get number requested publications with " + countRowsParameters, e);
		} finally {
			transaction.end();
		}
	}
}
