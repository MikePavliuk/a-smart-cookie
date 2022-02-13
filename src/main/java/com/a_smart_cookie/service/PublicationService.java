package com.a_smart_cookie.service;

import com.a_smart_cookie.adapter.filtering_data.catalog.CountRowsParameters;
import com.a_smart_cookie.adapter.filtering_data.catalog.FilterParameters;
import com.a_smart_cookie.dto.PublicationsWithAllUsedGenres;
import com.a_smart_cookie.exception.ServiceException;

public interface PublicationService {

	PublicationsWithAllUsedGenres findPublicationsByFilterParameters(FilterParameters filterParameters) throws ServiceException;

	int getTotalNumberOfRequestedQueryRows(CountRowsParameters countRowsParameters) throws ServiceException;

}
