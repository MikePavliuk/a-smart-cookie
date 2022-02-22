package com.a_smart_cookie.service.impl;

import com.a_smart_cookie.dao.DaoFactory;
import com.a_smart_cookie.dao.EntityTransaction;
import com.a_smart_cookie.dao.GenreDao;
import com.a_smart_cookie.dao.PublicationDao;
import com.a_smart_cookie.dto.catalog.CountRowsParameters;
import com.a_smart_cookie.dto.catalog.FilterParameters;
import com.a_smart_cookie.dto.catalog.PublicationsWithAllUsedGenres;
import com.a_smart_cookie.exception.DaoException;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.PublicationService;
import org.apache.log4j.Logger;

/**
 * Serves publication related entities and uses Daos and EntityTransaction for that purpose.
 *
 */
public class PublicationServiceImpl implements PublicationService {

	private static final Logger LOG = Logger.getLogger(PublicationServiceImpl.class);

	@Override
	public PublicationsWithAllUsedGenres findPublicationsByFilterParameters(FilterParameters filterParameters) {
		LOG.debug("PublicationServiceImpl starts finding publications by filter parameters");

		EntityTransaction transaction = new EntityTransaction();
		PublicationsWithAllUsedGenres publicationsAndGenres = new PublicationsWithAllUsedGenres();

		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();
			GenreDao genreDao = DaoFactory.getInstance().getGenreDao();

			transaction.initTransaction(publicationDao, genreDao);

			publicationsAndGenres.setPublications(publicationDao.findPublicationsByFilterParameters(filterParameters));
			publicationsAndGenres.setGenres(genreDao.findAllUsedInPublicationsGenres());

			transaction.commit();
			LOG.debug("PublicationServiceImpl finished finding publications by filter parameters");
			return publicationsAndGenres;
		} catch (DaoException e) {
			transaction.rollback();
			LOG.error("Can't find all publications by filter parameters so made rollback", e);
			throw new ServiceException("Can't find genres and publications with " + filterParameters, e);
		} finally {
			transaction.endTransaction();
		}
	}

	@Override
	public int getTotalNumberOfRequestedQueryRows(CountRowsParameters countRowsParameters) {
		LOG.debug("PublicationServiceImpl starts getting total number of requested rows by parameters");

		EntityTransaction transaction = new EntityTransaction();
		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();
			transaction.init(publicationDao);

			LOG.debug("PublicationServiceImpl finished getting total number of requested rows by parameters");
			return publicationDao.getTotalNumberOfRequestedQueryRows(countRowsParameters);
		} catch (DaoException e) {
			LOG.error("Can't get number requested publications with " + countRowsParameters, e);
			throw new ServiceException("Can't get number requested publications with " + countRowsParameters, e);
		} finally {
			transaction.end();
		}
	}
}
