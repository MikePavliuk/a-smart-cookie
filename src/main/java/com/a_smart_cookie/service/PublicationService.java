package com.a_smart_cookie.service;

import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.exception.ServiceException;

import java.util.List;

public interface PublicationService {

	List<Publication> findAllByLanguage(Language language) throws ServiceException;

	List<Publication> findLimitedWithOffsetByLanguage(int limit, int offset, Language language) throws ServiceException;

	int getTotalNumberOfPublications() throws ServiceException;

}
