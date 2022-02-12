package com.a_smart_cookie.service.impl;

import com.a_smart_cookie.dao.DaoFactory;
import com.a_smart_cookie.dao.EntityTransaction;
import com.a_smart_cookie.dao.PublicationDao;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.exception.DaoException;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.PublicationService;
import com.a_smart_cookie.util.sorting.SortingDirection;
import com.a_smart_cookie.util.sorting.SortingParameter;

import java.util.List;

public class PublicationServiceImpl implements PublicationService {

	@Override
	public List<Publication> findAllByLanguage(Language language) throws ServiceException {
		EntityTransaction transaction = new EntityTransaction();
		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();
			transaction.init(publicationDao);
			return publicationDao.findAllByLanguage(language);
		} catch (DaoException e) {
			throw new ServiceException("Can't find all publications in " + language.name().toLowerCase(), e);
		} finally {
			transaction.end();
		}
	}

	@Override
	public List<Publication> findLimitedWithOffsetByLanguage(int limit, int offset, Language language) throws ServiceException {
		EntityTransaction transaction = new EntityTransaction();
		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();
			transaction.init(publicationDao);
			return publicationDao.findLimitedWithOffsetByLanguage(limit, offset, language);
		} catch (DaoException e) {
			throw new ServiceException("Can't find " + limit +" publications with offset of " + offset + " in " + language, e);
		} finally {
			transaction.end();
		}
	}

	@Override
	public List<Publication> findLimitedWithOffsetByLanguageAndWithSortingParameters(
			int itemsPerPage,
			int offset,
			Language language,
			SortingParameter sortingParameter,
			SortingDirection sortingDirection) throws ServiceException {

		EntityTransaction transaction = new EntityTransaction();
		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();
			transaction.init(publicationDao);
			return publicationDao.findLimitedWithOffsetByLanguageAndWithSortingParameters(
					itemsPerPage,
					offset,
					language,
					sortingParameter,
					sortingDirection);
		} catch (DaoException e) {
			throw new ServiceException(
					"Can't find " + itemsPerPage +" publications with offset of " + offset + " in " + language +
					" with sorting parameter " + sortingParameter.getValue() + " and direction of sorting " + sortingDirection.name(), e);
		} finally {
			transaction.end();
		}

	}

	@Override
	public int getTotalNumberOfPublications() throws ServiceException {
		EntityTransaction transaction = new EntityTransaction();
		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();
			transaction.init(publicationDao);
			return publicationDao.getTotalNumberOfPublications();
		} catch (DaoException e) {
			throw new ServiceException("Can't get total number of publications", e);
		} finally {
			transaction.end();
		}
	}
}
