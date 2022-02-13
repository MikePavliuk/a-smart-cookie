package com.a_smart_cookie.dao;

import com.a_smart_cookie.dao.mysql.MysqlDaoFactory;
import com.a_smart_cookie.exception.DaoException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class DaoFactory {
    private static DaoFactory instance;
    private static final Lock LOCK = new ReentrantLock();
    private static final String daoFactoryFCN;

    static {
        daoFactoryFCN = MysqlDaoFactory.class.getName();
    }

    protected DaoFactory() {
    }

     public static DaoFactory getInstance() throws DaoException {
        try {
            LOCK.lock();
            if (instance == null) {
                try {
                    Class<?> clazz = Class.forName(DaoFactory.daoFactoryFCN);
                    instance = (DaoFactory) clazz.getDeclaredConstructor().newInstance();
                } catch (ReflectiveOperationException ex) {
                    throw new DaoException("Can't obtain the instance DaoFactory with " + daoFactoryFCN + " FCN", ex);
                }
            }
        } finally {
            LOCK.unlock();
        }
        return instance;
    }

    public abstract PublicationDao getPublicationDao();

    public abstract GenreDao getGenreDao();

}
