package com.a_smart_cookie.dao;

import com.a_smart_cookie.entity.Genre;
import com.a_smart_cookie.exception.DaoException;

import java.util.List;

/**
 * Interface for creating concrete representation for operating on Genre entity.
 *
 */
public abstract class GenreDao extends AbstractDao {

	/**
	 * Method for getting genres that have their implementations in publications.
	 *
	 * @return List of genres.
	 * @throws DaoException Occurred when something went wrong on JDBC layer.
	 */
	public abstract List<Genre> findAllUsedInPublicationsGenres() throws DaoException;

	/**
	 * Gets id of genre by name.
	 *
	 * @param name Name of genre to search.
	 * @return Id of genre.
	 * @throws DaoException Occurred when something went wrong on JDBC layer.
	 */
	public abstract int getGenreIdByName(String name) throws DaoException;

}
