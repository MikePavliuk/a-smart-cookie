package com.a_smart_cookie.service.impl;

import com.a_smart_cookie.dao.*;
import com.a_smart_cookie.dto.admin.PublicationDto;
import com.a_smart_cookie.dto.catalog.CountRowsParameters;
import com.a_smart_cookie.dto.catalog.FilterParameters;
import com.a_smart_cookie.dto.catalog.PublicationsWithAllUsedGenres;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.exception.DaoException;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.PublicationService;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Override
	public Map<Language, Publication> getPublicationInAllLanguagesById(int publicationId) {
		LOG.debug("Method starts");

			EntityTransaction transaction = new EntityTransaction();
			try {
				PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();
				transaction.init(publicationDao);

				Map<Language, Publication> publicationMap = new HashMap<>();

				for (Language language : Language.values()) {
					publicationMap.put(language, publicationDao.getPublicationWithInfoByIdAndLanguage(publicationId, language));
					LOG.trace("language --> " + language.getAbbr() + ", publication -->" + publicationMap.get(language));
				}
				LOG.debug("Method finished");
				return publicationMap;

			} catch (DaoException e) {
				LOG.error("Can't get publication by id '" + publicationId + "'", e);
				throw new ServiceException("Can't get publication by id '" + publicationId + "'", e);
			} finally {
				transaction.end();
		}
	}

	@Override
	public void editPublicationWithInfo(PublicationDto publicationDto) {
		LOG.debug("Method starts");

		EntityTransaction transaction = new EntityTransaction();

		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();

			transaction.initTransaction(publicationDao);

			publicationDao.updatePublicationGenreAndPricePerMonthById(
					publicationDto.getGenre(),
					publicationDto.getPricePerMonth(),
					publicationDto.getId()
			);

			for (Language language : Language.values()) {
				publicationDao.updatePublicationInfoByLanguage(
						publicationDto.getTitles().get(language),
						publicationDto.getDescriptions().get(language),
						publicationDto.getId(),
						language
				);
			}

			transaction.commit();

			LOG.debug("Method finished");

		} catch (DaoException e) {
			transaction.rollback();
			LOG.error("Can't edit publication", e);
			throw new ServiceException("Can't edit publication", e);
		} finally {
			transaction.endTransaction();
		}
	}

	@Override
	public void createPublicationWithInfo(PublicationDto publicationDto) throws ServiceException {
		LOG.debug("Method starts");

		EntityTransaction transaction = new EntityTransaction();

		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();
			GenreDao genreDao = DaoFactory.getInstance().getGenreDao();
			LanguageDao languageDao = DaoFactory.getInstance().getLanguageDao();

			transaction.initTransaction(publicationDao, genreDao, languageDao);

			int genreId = genreDao.getGenreIdByName(publicationDto.getGenre().name().toLowerCase());
			int publicationId = publicationDao.createPublication(genreId, publicationDto.getPricePerMonth());

			Map<Integer, String> titles = new HashMap<>();
			Map<Integer, String> descriptions = new HashMap<>();

			for (Map.Entry<Language, String> languageTitleEntry : publicationDto.getTitles().entrySet()) {
				titles.put(languageDao.getLanguageIdByName(languageTitleEntry.getKey().name().toLowerCase()), languageTitleEntry.getValue());
			}

			for (Map.Entry<Language, String> languageDescriptionEntry : publicationDto.getDescriptions().entrySet()) {
				descriptions.put(languageDao.getLanguageIdByName(languageDescriptionEntry.getKey().name().toLowerCase()), languageDescriptionEntry.getValue());
			}

			publicationDao.createPublicationInfos(
					publicationId,
					titles,
					descriptions
			);

			transaction.commit();

			LOG.debug("Method finished");

		} catch (DaoException e) {
			transaction.rollback();
			LOG.error("Can't insert publication with translations", e);
			throw new ServiceException("Can't insert publication with translations", e);
		} finally {
			transaction.endTransaction();
		}
	}

}
