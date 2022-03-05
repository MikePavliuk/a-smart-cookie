package com.a_smart_cookie.service;

import com.a_smart_cookie.dto.admin.UserForManagement;
import com.a_smart_cookie.dto.sign_up.UserSignUpDto;
import com.a_smart_cookie.entity.Status;
import com.a_smart_cookie.entity.User;
import com.a_smart_cookie.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * Interface for creating concrete representation of UserService.
 *
 */
public interface UserService {

	/**
	 * Checks whether user already exist by email.
	 *
	 * @param email Email value to search by.
	 * @return Boolean value whether user already exist.
	 * @throws ServiceException Thrown as wrapper for possible DaoException.
	 */
	boolean isUserAlreadyExistsByEmail(String email) throws ServiceException;

	/**
	 * Returns Optional of user if he was found by email.
	 *
	 * @param email Email which user should be found by.
	 * @return Optional of user if he exists, otherwise - empty optional.
	 * @throws ServiceException Thrown when can't get users without subscriptions or get user's subscriptions.
	 */
	Optional<User> getUserByEmail(String email) throws ServiceException;

	/**
	 * Returns Optional of inserted user with generated id.
	 *
	 * @param userSignUpDto Dto from view that represents new user info.
	 * @return Newly inserted User
	 * @throws ServiceException Thrown when no User itself, UserDetail were not inserted or Hashing was not correctly performed.
	 */
	User createNewUser(UserSignUpDto userSignUpDto) throws ServiceException;

	/**
	 * Gets all limited number of subscribers for management.
	 *
	 * @return Dto UserForStatusManagement
	 * @see UserForManagement
	 * @param requestedPage Page to be got.
	 * @param itemsPerPage Items per page.
	 * @throws ServiceException Thrown when transaction was not correctly performed or DaoException had occurred.
	 */
	List<UserForManagement> getPaginatedUsersWithStatistics(int requestedPage, int itemsPerPage) throws ServiceException;

	/**
	 * Gets all subscribers with statistics for management.
	 *
	 * @return List of UserForManagement.
	 * @throws ServiceException Thrown when transaction was not correctly performed or DaoException had occurred.
	 */
	List<UserForManagement> getAllUsersWithStatistics() throws ServiceException;

	/**
	 * Method for getting number of subscribers.
	 *
	 * @return Number of founded subscribers.
	 * @throws ServiceException Thrown as wrapper for possible DaoException.
	 */
	int getTotalNumberOfSubscribers() throws ServiceException;

	/**
	 * Changes user status.
	 *
	 * @param userId Users id.
	 * @param status New status to be set to user.
	 * @throws ServiceException Occurred when user status was not updated or caught DaoException.
	 */
	void changeUserStatus(int userId, Status status) throws ServiceException;

}
