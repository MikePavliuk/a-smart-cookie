package com.a_smart_cookie.service.impl;

import com.a_smart_cookie.dao.DaoFactory;
import com.a_smart_cookie.dao.EntityTransaction;
import com.a_smart_cookie.dao.UserDao;
import com.a_smart_cookie.dao.UserDetailDao;
import com.a_smart_cookie.dto.user.UserMapper;
import com.a_smart_cookie.dto.user.UserSignUpDto;
import com.a_smart_cookie.entity.User;
import com.a_smart_cookie.entity.UserDetail;
import com.a_smart_cookie.exception.DaoException;
import com.a_smart_cookie.exception.HashingException;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.UserService;
import org.apache.log4j.Logger;

import java.util.Optional;

/**
 * Serves User related entities and uses Daos and EntityTransaction for that purpose.
 *
 */
public class UserServiceImpl implements UserService {

	private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);

	@Override
	public boolean isUserAlreadyExistsByEmail(String email) throws ServiceException {
		LOG.debug("Starts getting checking if user exists");

		EntityTransaction transaction = new EntityTransaction();

		try {
			UserDao userDao = DaoFactory.getInstance().getUserDao();
			transaction.init(userDao);

			LOG.debug("Finished getting checking if user exists");
			return userDao.isUserExistsByEmail(email);
		} catch (DaoException e) {
			LOG.error("Can't check whether user exists", e);
			throw new ServiceException("Can't check whether user exists", e);
		} finally {
			transaction.end();
		}
	}

	@Override
	public Optional<User> getUserWithoutSubscriptionsByEmail(String email) throws ServiceException {
		LOG.debug("Starts getting user");

		EntityTransaction transaction = new EntityTransaction();

		try {
			UserDao userDao = DaoFactory.getInstance().getUserDao();
			transaction.init(userDao);

			LOG.debug("Finished getting user");
			return userDao.getUserByEmail(email);
		} catch (DaoException e) {
			LOG.error("Can't get user by email " + email, e);
			throw new ServiceException("Can't get user by email " + email, e);
		} finally {
			transaction.end();
		}
	}

	@Override
	public Optional<User> createNewUser(UserSignUpDto userSignUpDto) throws ServiceException {
		LOG.debug("Starts creating user");

		EntityTransaction transaction = new EntityTransaction();

		try {
			UserDao userDao = DaoFactory.getInstance().getUserDao();
			UserDetailDao userDetailDao = DaoFactory.getInstance().getUserDetailDao();

			transaction.initTransaction(userDao, userDetailDao);

			User user = UserMapper.convertFromDtoToEntity(userSignUpDto);
			Optional<User> insertedUser = userDao.insertUser(user);

			if (insertedUser.isEmpty()) {
				transaction.rollback();
				LOG.debug("Finished creating user with not created user");
				return Optional.empty();
			}

			Optional<UserDetail> userDetail = userDetailDao.insertUserDetail(user.getUserDetail(), insertedUser.get().getId());

			if (userDetail.isPresent()) {
				user = User.UserBuilder.fromUser(insertedUser.get()).withUserDetail(userDetail.get()).build();
				transaction.commit();
				LOG.debug("Finished creating user");
				return Optional.of(user);
			}

			transaction.rollback();
			LOG.debug("Finished creating user with not created user detail");
			return Optional.empty();

		} catch (DaoException | HashingException e) {
			transaction.rollback();
			LOG.error("Can't insert user", e);
			throw new ServiceException("Can't insert user", e);
		} finally {
			transaction.endTransaction();
		}
	}

}
