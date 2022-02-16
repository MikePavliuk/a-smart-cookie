package com.a_smart_cookie.dto.user;

import com.a_smart_cookie.entity.User;
import com.a_smart_cookie.exception.HashingException;
import com.a_smart_cookie.util.hashing.PBKDF2Hash;

/**
 * Maps User dto to entity.
 *
 */
public final class UserMapper {

	/**
	 * Converts UserSignUpDto object to regular User (subscriber) object with hashing password and attaching salt.
	 *
	 * @param userSignUpDto Represents value from sign up form.
	 * @return Constructed user with hashed password and ordinary active subscriber role.
	 */
	public static User convertFromDtoToEntity(UserSignUpDto userSignUpDto) throws HashingException {
		PBKDF2Hash.HashSaltPair hashSaltPair = PBKDF2Hash.hash(userSignUpDto.getPassword());
		return new User.UserBuilder(
				userSignUpDto.getEmail(),
				hashSaltPair.getHash(),
				hashSaltPair.getSalt(),
				User.Status.ACTIVE,
				User.Role.SUBSCRIBER).build();
	}

	private UserMapper() {
	}
}
