package com.a_smart_cookie.dao.mysql;

import com.a_smart_cookie.dao.*;

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

	@Override
	public UserDao getUserDao() {
		return new MysqlUserDao();
	}

	@Override
	public UserDetailDao getUserDetailDao() {
		return new MysqlUserDetailDao();
	}

	@Override
	public SubscriptionDao getSubscriptionDao() {
		return new MysqlSubscriptionDao();
	}


}
