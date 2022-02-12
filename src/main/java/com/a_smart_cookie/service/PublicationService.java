package com.a_smart_cookie.service;

import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.util.sorting.SortingDirection;
import com.a_smart_cookie.util.sorting.SortingParameter;

import java.util.List;

public interface PublicationService {

	List<Publication> findAllByLanguage(Language language) throws ServiceException;

	List<Publication> findLimitedWithOffsetByLanguage(int limit, int offset, Language language) throws ServiceException;

	List<Publication> findLimitedWithOffsetByLanguageAndWithSortingParameters(
			int itemsPerPage,
			int offset,
			Language language,
			SortingParameter sortingParameter,
			SortingDirection sortingDirection) throws ServiceException;

	int getTotalNumberOfPublications() throws ServiceException;

}
