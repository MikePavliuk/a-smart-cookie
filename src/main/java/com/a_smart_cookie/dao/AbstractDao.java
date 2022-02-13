package com.a_smart_cookie.dao;

import java.sql.Connection;

public abstract class AbstractDao {

    protected Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
