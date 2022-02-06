package com.a_smart_cookie.service.impl;

import com.a_smart_cookie.dao.GenreDao;
import com.a_smart_cookie.dao.mysql.MysqlGenreDao;
import com.a_smart_cookie.dao.pool.StandardConnectionPool;
import com.a_smart_cookie.entity.Genre;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.exception.DaoException;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.GenreService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GenreServiceImpl implements GenreService {

    public List<Genre> findAllGenresByLanguage(Language language) throws ServiceException {
        Connection conn = null;
        GenreDao genreDao = new MysqlGenreDao();

        try {
            conn = StandardConnectionPool.getConnection();
            genreDao.setConnection(conn);
            return genreDao.findAllByLanguage(language);
        } catch (SQLException | DaoException e) {
            throw new ServiceException(e);
        } finally {
            genreDao.close(conn);
        }
    }

}
