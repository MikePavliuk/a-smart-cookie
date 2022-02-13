package com.a_smart_cookie.service;

import com.a_smart_cookie.adapter.filtering_data.catalog.CountRowsParameters;
import com.a_smart_cookie.adapter.filtering_data.catalog.FilterParameters;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.exception.ServiceException;

import java.util.List;

public interface PublicationService {

	List<Publication> findPublicationsByFilterParameters(FilterParameters filterParameters) throws ServiceException;

	int getTotalNumberOfRequestedQueryRows(CountRowsParameters countRowsParameters) throws ServiceException;

}
