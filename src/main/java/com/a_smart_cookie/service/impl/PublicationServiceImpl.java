package com.a_smart_cookie.service.impl;

import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
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
import com.a_smart_cookie.util.validation.publication.PublicationValidator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Serves publication related entities and uses Daos and EntityTransaction for that purpose.
 */
public class PublicationServiceImpl implements PublicationService {

	private static final Logger LOG = Logger.getLogger(PublicationServiceImpl.class);

	@Override
	public PublicationsWithAllUsedGenres findPublicationsByFilterParameters(FilterParameters filterParameters) throws ServiceException {
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
			throw new ServiceException("Can't find publications by " + filterParameters, e);
		} finally {
			transaction.endTransaction();
		}
	}

	@Override
	public int getTotalNumberOfRequestedQueryRows(CountRowsParameters countRowsParameters) throws ServiceException {
		LOG.debug("Method starts");

		EntityTransaction transaction = new EntityTransaction();
		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();
			transaction.init(publicationDao);

			LOG.debug("Method finished");
			return publicationDao.getTotalNumberOfRequestedQueryRows(countRowsParameters);
		} catch (DaoException e) {
			throw new ServiceException("Can't get number requested publications with " + countRowsParameters, e);
		} finally {
			transaction.end();
		}
	}

	@Override
	public List<Publication> getLimitedPublicationsByLanguage(int requestedPage, int itemsPerPage, Language language) throws ServiceException {
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
			throw new ServiceException("Can't get publications", e);
		} finally {
			transaction.end();
		}
	}

	@Override
	public int getTotalNumberOfPublications() throws ServiceException {
		LOG.debug("Method starts");

		EntityTransaction transaction = new EntityTransaction();
		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();
			transaction.init(publicationDao);

			LOG.debug("Method finished");
			return publicationDao.getTotalNumberOfPublications();
		} catch (DaoException e) {
			throw new ServiceException("Can't get number of publications", e);
		} finally {
			transaction.end();
		}
	}

	@Override
	public void deletePublication(int publicationId) throws ServiceException {
		LOG.debug("Method starts");

		EntityTransaction transaction = new EntityTransaction();
		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();
			transaction.init(publicationDao);

			LOG.debug("Method finished");

			if (!publicationDao.deletePublicationById(publicationId)) {
				throw new ServiceException("Result set is empty");
			}

		} catch (DaoException | ServiceException e) {
			throw new ServiceException("Can't delete publication by id '" + publicationId + "'", e);
		} finally {
			transaction.end();
		}
	}

	@Override
	public Map<Language, Publication> getPublicationInAllLanguagesById(int publicationId) throws ServiceException {
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
				throw new ServiceException("Can't get publication by id '" + publicationId + "'", e);
			} finally {
				transaction.end();
		}
	}

	@Override
	public void editPublicationWithInfo(PublicationDto publicationDto) throws ServiceException {
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
			throw new ServiceException("Can't insert publication with translations", e);
		} finally {
			transaction.endTransaction();
		}
	}

	@Override
	public HttpPath performValidationMechanism(HttpSession session, PublicationDto publicationDto) {
		Map<String, Boolean> validationResult = PublicationValidator.getValidationResults(publicationDto);

		if (validationResult.containsValue(false)) {
			session.setAttribute("isValidPricePerMonth", validationResult.get(EntityColumn.Publication.PRICE_PER_MONTH.getName()));

			for (Language language: Language.values()) {

				session.setAttribute("isValidTitle_" + language.getAbbr(),
						validationResult.get(EntityColumn.PublicationInfo.TITLE.getName() + "_" + language.getAbbr()));

				session.setAttribute("isValidDescription_" + language.getAbbr(),
						validationResult.get(EntityColumn.PublicationInfo.DESCRIPTION.getName() + "_" + language.getAbbr()));
			}

			addOldFieldValuesToSession(session, publicationDto);

			LOG.debug("Command finished with not valid user");
			return new HttpPath(WebPath.Command.ADMIN_PUBLICATION_EDIT.getValue() + "&item=" + publicationDto.getId(), HttpHandlerType.SEND_REDIRECT);
		}

		return null;
	}

	private void addOldFieldValuesToSession(HttpSession session, PublicationDto publicationDto) {
		session.setAttribute("oldPricePerMonth", publicationDto.getPricePerMonth());
		session.setAttribute("oldGenre", publicationDto.getGenre());

		for (Language language: Language.values()) {
			session.setAttribute("oldTitle_" + language.getAbbr(), publicationDto.getTitles().get(language));
			session.setAttribute("oldDescription_" + language.getAbbr(), publicationDto.getDescriptions().get(language));
		}
	}

}
