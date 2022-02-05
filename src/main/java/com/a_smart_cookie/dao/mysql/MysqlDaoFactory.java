package com.a_smart_cookie.dao.mysql;

import com.a_smart_cookie.dao.DaoFactory;
import com.a_smart_cookie.dao.GenreDao;

public class MysqlDaoFactory extends DaoFactory {

    @Override
    public GenreDao getGenreDao() {
        return new MysqlGenreDao();
    }

}
