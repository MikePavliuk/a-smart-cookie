package com.a_smart_cookie.dao;

import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.exception.DaoException;

import java.util.List;

public abstract class GenreDao extends AbstractDao {

	public abstract List<Publication.Genre> findAllUsedInPublicationsGenres() throws DaoException;

}
