package com.a_smart_cookie.service;

import com.a_smart_cookie.dto.catalog.CountRowsParameters;
import com.a_smart_cookie.dto.catalog.FilterParameters;
import com.a_smart_cookie.dto.catalog.PublicationsWithAllUsedGenres;
import com.a_smart_cookie.exception.ServiceException;

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
	PublicationsWithAllUsedGenres findPublicationsByFilterParameters(FilterParameters filterParameters) throws ServiceException;

	/**
	 * Method for getting number of requested rows by countRowsParameters.
	 *
	 * @param countRowsParameters Parameters of searched publications
	 * @return Number of founded rows.
	 */
	int getTotalNumberOfRequestedQueryRows(CountRowsParameters countRowsParameters) throws ServiceException;

}
