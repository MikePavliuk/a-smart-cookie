package com.a_smart_cookie.service.impl;

import com.a_smart_cookie.dto.catalog.CountRowsParameters;
import com.a_smart_cookie.dto.catalog.FilterParameters;
import com.a_smart_cookie.dao.DaoFactory;
import com.a_smart_cookie.dao.EntityTransaction;
import com.a_smart_cookie.dao.GenreDao;
import com.a_smart_cookie.dao.PublicationDao;
import com.a_smart_cookie.dto.catalog.PublicationsWithAllUsedGenres;
import com.a_smart_cookie.exception.DaoException;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.PublicationService;

public class PublicationServiceImpl implements PublicationService {

	@Override
	public PublicationsWithAllUsedGenres findPublicationsByFilterParameters(FilterParameters filterParameters) throws ServiceException {
		EntityTransaction transaction = new EntityTransaction();
		PublicationsWithAllUsedGenres publicationsAndGenres = new PublicationsWithAllUsedGenres();

		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();
			GenreDao genreDao = DaoFactory.getInstance().getGenreDao();

			transaction.initTransaction(publicationDao, genreDao);

			publicationsAndGenres.setPublications(publicationDao.findPublicationsByFilterParameters(filterParameters));
			publicationsAndGenres.setGenres(genreDao.findAllUsedInPublicationsGenres());

			transaction.commit();
			return publicationsAndGenres;
		} catch (DaoException e) {
			transaction.rollback();
			throw new ServiceException("Can't find genres and publications with " + filterParameters, e);
		} finally {
			transaction.endTransaction();
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
