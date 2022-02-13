package com.a_smart_cookie.dao;

import com.a_smart_cookie.adapter.filtering_data.catalog.CountRowsParameters;
import com.a_smart_cookie.adapter.filtering_data.catalog.FilterParameters;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.exception.DaoException;

import java.util.List;

public abstract class PublicationDao extends AbstractDao<Publication> {

	public abstract List<Publication> findPublicationsByFilterParameters(FilterParameters filterParameters) throws DaoException;

	public abstract int getTotalNumberOfRequestedQueryRows(CountRowsParameters countRowsParameters) throws DaoException;

}
