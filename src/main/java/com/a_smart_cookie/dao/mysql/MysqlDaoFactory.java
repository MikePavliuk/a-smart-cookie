package com.a_smart_cookie.dao.mysql;

import com.a_smart_cookie.dao.DaoFactory;
import com.a_smart_cookie.dao.PublicationDao;

public class MysqlDaoFactory extends DaoFactory {

	@Override
	public PublicationDao getPublicationDao() {
		return new MysqlPublicationDao();
	}

}
