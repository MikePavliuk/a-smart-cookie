package com.a_smart_cookie.service.impl;

import com.a_smart_cookie.dao.DaoFactory;
import com.a_smart_cookie.dao.EntityTransaction;
import com.a_smart_cookie.dao.GenreDao;
import com.a_smart_cookie.dao.PublicationDao;
import com.a_smart_cookie.dto.catalog.CountRowsParameters;
import com.a_smart_cookie.dto.catalog.FilterParameters;
import com.a_smart_cookie.dto.catalog.PublicationsWithAllUsedGenres;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.exception.DaoException;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.PublicationService;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Serves publication related entities and uses Daos and EntityTransaction for that purpose.
 */
public class PublicationServiceImpl implements PublicationService {

	private static final Logger LOG = Logger.getLogger(PublicationServiceImpl.class);

	@Override
	public PublicationsWithAllUsedGenres findPublicationsByFilterParameters(FilterParameters filterParameters) {
		LOG.debug("Method starts");

		EntityTransaction transaction = new EntityTransaction();
		PublicationsWithAllUsedGenres publicationsAndGenres = new PublicationsWithAllUsedGenres();

		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();
			GenreDao genreDao = DaoFactory.getInstance().getGenreDao();

			transaction.initTransaction(publicationDao, genreDao);

			publicationsAndGenres.setPublications(publicationDao.findPublicationsByFilterParameters(filterParameters));
			publicationsAndGenres.setGenres(genreDao.findAllUsedInPublicationsGenres());

			transaction.commit();
			LOG.debug("Method finished");
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
		LOG.debug("Method starts");

		EntityTransaction transaction = new EntityTransaction();
		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();
			transaction.init(publicationDao);

			LOG.debug("Method finished");
			return publicationDao.getTotalNumberOfRequestedQueryRows(countRowsParameters);
		} catch (DaoException e) {
			LOG.error("Can't get number requested publications with " + countRowsParameters, e);
			throw new ServiceException("Can't get number requested publications with " + countRowsParameters, e);
		} finally {
			transaction.end();
		}
	}

	@Override
	public List<Publication> getLimitedPublicationsByLanguage(int requestedPage, int itemsPerPage, Language language) {
		LOG.debug("Starts method");

		EntityTransaction transaction = new EntityTransaction();

		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();

			transaction.init(publicationDao);

			return publicationDao.getPublicationsWithLimitByLanguage(
					itemsPerPage * (requestedPage - 1),
					itemsPerPage,
					language
			);

		} catch (DaoException e) {
			LOG.error("Can't get publications", e);
			throw new ServiceException("Can't get publications", e);
		} finally {
			transaction.end();
		}
	}

	@Override
	public int getTotalNumberOfPublications() {
		LOG.debug("Method starts");

		EntityTransaction transaction = new EntityTransaction();
		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();
			transaction.init(publicationDao);

			LOG.debug("Method finished");
			return publicationDao.getTotalNumberOfPublications();
		} catch (DaoException e) {
			LOG.error("Can't get number of publications", e);
			throw new ServiceException("Can't get number of publications", e);
		} finally {
			transaction.end();
		}
	}

	@Override
	public void deletePublication(int publicationId) {
		LOG.debug("Method starts");

		EntityTransaction transaction = new EntityTransaction();
		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();
			transaction.init(publicationDao);

			LOG.debug("Method finished");

			if (!publicationDao.deletePublicationById(publicationId)) {
				throw new ServiceException("Result set is empty");
			}

		} catch (DaoException e) {
			LOG.error("Can't delete publication by id '" + publicationId + "'", e);
			throw new ServiceException("Can't delete publication by id '" + publicationId + "'", e);
		} finally {
			transaction.end();
		}
	}
}
