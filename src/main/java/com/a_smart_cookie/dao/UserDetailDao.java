package com.a_smart_cookie.dao;

import com.a_smart_cookie.entity.UserDetail;
import com.a_smart_cookie.exception.DaoException;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Interface for creating concrete representation for operating on UserDetail entity.
 *
 */
public abstract class UserDetailDao extends AbstractDao {

	/**
	 * Insert UserDetail record into db and returns its set id.
	 *
	 * @param userDetail UserDetail information for creating record in db.
	 * @param userId User id of this detail info.
	 * @return Generated id for inserted UserDetail or empty if something went wrong.
	 * @throws DaoException Occurred when something went wrong on JDBC layer.
	 */
	public abstract Optional<UserDetail> insertUserDetail(UserDetail userDetail, int userId) throws DaoException;

	/**
	 * Add funds to user account by user id.
	 *
	 * @param paymentAmount Amount of money to add in dollars.
	 * @param userId Id of user to add funds.
	 * @return Whether transaction was correctly performed.
	 * @throws DaoException Occurred when something went wrong on JDBC layer.
	 */
	public abstract boolean addMoneyToBalanceByUserId(BigDecimal paymentAmount, int userId) throws DaoException;

	/**
	 * Minus funds from user account by user id.
	 *
	 * @param paymentAmount Amount of money to minus from user in dollars.
	 * @param userId Id of user to debit funds.
	 * @return Whether transaction was correctly performed.
	 * @throws DaoException Occurred when something went wrong on JDBC layer.
	 */
	public abstract boolean debitFundsFromBalanceByUserId(BigDecimal paymentAmount, int userId) throws DaoException;

	/**
	 * Gets users balance.
	 *
	 * @param userId Id of user.
	 * @return Balance of user.
	 * @throws DaoException Occurred when something went wrong on JDBC layer.
	 */
	public abstract Optional<BigDecimal> getBalanceByUserId(int userId) throws DaoException;

}
