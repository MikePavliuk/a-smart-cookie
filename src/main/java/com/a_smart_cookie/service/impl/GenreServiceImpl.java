package com.a_smart_cookie.service.impl;

import com.a_smart_cookie.dao.EntityTransaction;
import com.a_smart_cookie.dao.GenreDao;
import com.a_smart_cookie.entity.Genre;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.exception.DaoException;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.GenreService;

import java.util.List;

public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;
    private final EntityTransaction transaction;

    public GenreServiceImpl(GenreDao genreDao, EntityTransaction transaction) {
        this.genreDao = genreDao;
        this.transaction = transaction;
    }

    public List<Genre> findAllGenresByLanguage(Language language) throws ServiceException {
        try {
            transaction.init(genreDao);
            return genreDao.findAllByLanguage(language);
        } catch (DaoException e) {
            throw new ServiceException("Can't find all genres in " + language.name().toLowerCase(), e);
        } finally {
            transaction.end();
        }
    }

}
