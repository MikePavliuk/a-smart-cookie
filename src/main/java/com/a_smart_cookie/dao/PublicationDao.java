package com.a_smart_cookie.dao;

import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.exception.DaoException;
import com.a_smart_cookie.util.sorting.SortingDirection;
import com.a_smart_cookie.util.sorting.SortingParameter;

import java.util.List;

public abstract class PublicationDao extends AbstractDao<Publication> {

	public abstract List<Publication> findAllByLanguage(Language language) throws DaoException;

	public abstract List<Publication> findLimitedWithOffsetByLanguage(int itemsPerPage, int offset, Language  language) throws DaoException;

	public abstract List<Publication> findLimitedWithOffsetByLanguageAndWithSortingParameters(
			int itemsPerPage,
			int offset,
			Language language,
			SortingParameter sortingParameter,
			SortingDirection sortingDirection) throws DaoException;

	public abstract List<Publication> findBySearchedTitleLimitedWithOffsetByLanguageAndWithSortingParameters(
			String searchTitle,
			int itemsPerPage,
			int offset,
			Language language,
			SortingParameter sortingParameter,
			SortingDirection sortingDirection) throws DaoException;

	public abstract int getTotalNumberOfPublications() throws DaoException;

	public abstract int getNumberOfFoundedPublicationsByLanguageAndSearchedTitle(Language language, String title) throws DaoException;

}
