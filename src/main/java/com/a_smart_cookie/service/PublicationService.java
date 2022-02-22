package com.a_smart_cookie.service;

import com.a_smart_cookie.dto.catalog.CountRowsParameters;
import com.a_smart_cookie.dto.catalog.FilterParameters;
import com.a_smart_cookie.dto.catalog.PublicationsWithAllUsedGenres;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.entity.Publication;

import java.util.List;

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
	 */
	PublicationsWithAllUsedGenres findPublicationsByFilterParameters(FilterParameters filterParameters);

	/**
	 * Method for getting number of requested rows by countRowsParameters.
	 *
	 * @param countRowsParameters Parameters of searched publications
	 * @return Number of founded rows.
	 */
	int getTotalNumberOfRequestedQueryRows(CountRowsParameters countRowsParameters);

	/**
	 * Gets all limited number of publications for management.
	 *
	 * @param requestedPage Page to be got.
	 * @param itemsPerPage Items per page.
	 * @param language Language to be translated publication into.
	 * @return List of publications.
	 */
	List<Publication> getLimitedPublicationsByLanguage(int requestedPage, int itemsPerPage, Language language);

	/**
	 * Gets total number of all publications.
	 *
	 * @return Number of publications.
	 */
	int getTotalNumberOfPublications();

}
