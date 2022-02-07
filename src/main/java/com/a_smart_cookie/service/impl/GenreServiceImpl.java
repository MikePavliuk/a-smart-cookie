package com.a_smart_cookie.service.impl;

import com.a_smart_cookie.dao.DaoFactory;
import com.a_smart_cookie.dao.EntityTransaction;
import com.a_smart_cookie.dao.GenreDao;
import com.a_smart_cookie.entity.Genre;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.exception.DaoException;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.GenreService;

import java.util.List;

public class GenreServiceImpl implements GenreService {

    public List<Genre> findAllGenresByLanguage(Language language) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        try {
            GenreDao genreDao = DaoFactory.getInstance().getGenreDao();
            transaction.init(genreDao);
            return genreDao.findAllByLanguage(language);
        } catch (DaoException e) {
            throw new ServiceException("Can't find all genres in " + language.name().toLowerCase(), e);
        } finally {
            transaction.end();
        }
    }

}
