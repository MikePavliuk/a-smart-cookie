package com.a_smart_cookie.dao;

import com.a_smart_cookie.entity.Entity;

import java.sql.Connection;

public abstract class AbstractDao<T extends Entity> {

    protected Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
