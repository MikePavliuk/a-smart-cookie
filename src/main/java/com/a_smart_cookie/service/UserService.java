package com.a_smart_cookie.service;

import com.a_smart_cookie.dto.admin.UserForStatusManagement;
import com.a_smart_cookie.dto.sign_up.UserSignUpDto;
import com.a_smart_cookie.entity.Status;
import com.a_smart_cookie.entity.User;

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
	 */
	boolean isUserAlreadyExistsByEmail(String email);

	/**
	 * Returns Optional of user if he was found by email.
	 *
	 * @param email Email which user should be found by.
	 * @return Optional of user if he exists, otherwise - empty optional.
	 */
	Optional<User> getUserByEmail(String email);

	/**
	 * Returns Optional of inserted user with generated id.
	 *
	 * @param userSignUpDto Dto from view that represents new user info.
	 * @return Newly inserted User
	 */
	User createNewUser(UserSignUpDto userSignUpDto);

	/**
	 * Gets all dto subscribers for management.
	 *
	 * @return Dto UserForStatusManagement
	 * @see UserForStatusManagement
	 */
	List<UserForStatusManagement> getAllSubscribers();

	/**
	 * Changes user status.
	 *
	 * @param userId Users id.
	 * @param status New status to be set to user.
	 */
	void changeUserStatus(int userId, Status status);

}
