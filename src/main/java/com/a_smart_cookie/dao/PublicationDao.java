package com.a_smart_cookie.dao;

import com.a_smart_cookie.dto.catalog.CountRowsParameters;
import com.a_smart_cookie.dto.catalog.FilterParameters;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * Interface for creating concrete representation for operating on Publication entity.
 *
 */
public abstract class PublicationDao extends AbstractDao {

	/**
	 * Method for getting publications by filterParameters.
	 *
	 * @param filterParameters Parameters of searched publications.
	 * @return List of publications.
	 */
	public abstract List<Publication> findPublicationsByFilterParameters(FilterParameters filterParameters) throws DaoException;

	/**
	 * Method for getting number of requested rows by countRowsParameters.
	 *
	 * @param countRowsParameters Parameters of searched publications.
	 * @return Number of founded rows.
	 */
	public abstract int getTotalNumberOfRequestedQueryRows(CountRowsParameters countRowsParameters) throws DaoException;

	/**
	 * Gets publication without info by its id.
	 *
	 * @param id Publication's id.
	 * @return Optional of publication if publication was found, otherwise - of empty.
	 */
	public abstract Optional<Publication> getPublicationWithoutInfoById(int id) throws DaoException;

	/**
	 * Gets publications with info by its id and language.
	 *
	 * @param publicationId Publication's id.
	 * @param language Requested publication info language.
	 * @return Optional of publication if publication was found, otherwise - of empty.
	 */
	public abstract Optional<Publication> getPublicationWithInfoByIdAndLanguage(int publicationId, Language language) throws DaoException;

}
