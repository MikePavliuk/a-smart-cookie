package com.a_smart_cookie.dao.mysql;

import com.a_smart_cookie.dao.DaoFactory;
import com.a_smart_cookie.dao.GenreDao;
import com.a_smart_cookie.dao.PublicationDao;

/**
 * Implementation of DaoFactory with Mysql.
 *
 */
public class MysqlDaoFactory extends DaoFactory {

	@Override
	public PublicationDao getPublicationDao() {
		return new MysqlPublicationDao();
	}

	@Override
	public GenreDao getGenreDao() {
		return new MysqlGenreDao();
	}


}
