package com.a_smart_cookie.service;

import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.dto.admin.PublicationDto;
import com.a_smart_cookie.dto.catalog.CountRowsParameters;
import com.a_smart_cookie.dto.catalog.FilterParameters;
import com.a_smart_cookie.dto.catalog.PublicationsWithAllUsedGenres;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.exception.ServiceException;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Interface for creating concrete representation of PublicationService.
 *
 */
public interface PublicationService {

	/**
	 * Method for getting publications with all used in app genres by filter parameters.
	 *
	 * @param filterParameters Parameters of searched publications
	 * @return List of publications and list of used genres.
	 * @throws ServiceException Thrown as wrapper for possible DaoException.
	 */
	PublicationsWithAllUsedGenres findPublicationsByFilterParameters(FilterParameters filterParameters) throws ServiceException;

	/**
	 * Method for getting number of requested rows by countRowsParameters.
	 *
	 * @param countRowsParameters Parameters of searched publications
	 * @return Number of founded rows.
	 * @throws ServiceException Thrown as wrapper for possible DaoException.
	 */
	int getTotalNumberOfRequestedQueryRows(CountRowsParameters countRowsParameters) throws ServiceException;

	/**
	 * Gets all limited number of publications for management.
	 *
	 * @param requestedPage Page to be got.
	 * @param itemsPerPage Items per page.
	 * @param language Language to be translated publication into.
	 * @return List of publications.
	 * @throws ServiceException Thrown as wrapper for possible DaoException.
	 */
	List<Publication> getLimitedPublicationsByLanguage(int requestedPage, int itemsPerPage, Language language) throws ServiceException;

	/**
	 * Gets total number of all publications.
	 *
	 * @return Number of publications.
	 * @throws ServiceException Thrown as wrapper for possible DaoException.
	 */
	int getTotalNumberOfPublications() throws ServiceException;

	/**
	 * Deletes publication by id.
	 *
	 * @param publicationId Id of publication.
	 * @throws ServiceException Thrown as wrapper for possible DaoException or ResultSet it empty.
	 */
	void deletePublication(int publicationId) throws ServiceException;

	/**
	 * Gets publication by id translated into all languages.
	 *
	 * @param publicationId Id of publication.
	 * @return Map with language key and publication translated value.
	 * @throws ServiceException Thrown as wrapper for possible DaoException.
	 */
	Map<Language, Publication> getPublicationInAllLanguagesById(int publicationId) throws ServiceException;

	/**
	 * Saving updates on publication.
	 *
	 * @param publicationDto All needed info for updating publication.
	 * @throws ServiceException Thrown when transaction for editing translation records were not committed.
	 */
	void editPublicationWithInfo(PublicationDto publicationDto) throws ServiceException;

	/**
	 * Creates publication.
	 *
	 * @param publicationDto All needed info for creating new publication.
	 * @throws ServiceException Thrown when transaction for creating translation records were not committed.
	 */
	void createPublicationWithInfo(PublicationDto publicationDto) throws ServiceException;

	/**
	 * Performs validation of publication by its dto and sets result in session if needed.
	 *
	 * @param session Session for setting parameters.
	 * @param publicationDto Input publication dto.
	 * @return Null if validation is correct and HttpPath - if validation fails.
	 */
	HttpPath performValidationMechanism(HttpSession session, PublicationDto publicationDto);

}
