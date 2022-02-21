package com.a_smart_cookie.service.impl;

import com.a_smart_cookie.dao.*;
import com.a_smart_cookie.dto.admin.UserForStatusManagement;
import com.a_smart_cookie.dto.sign_up.UserMapper;
import com.a_smart_cookie.dto.sign_up.UserSignUpDto;
import com.a_smart_cookie.entity.User;
import com.a_smart_cookie.entity.UserDetail;
import com.a_smart_cookie.exception.DaoException;
import com.a_smart_cookie.exception.HashingException;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.UserService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Serves User related entities and uses Daos and EntityTransaction for that purpose.
 */
public class UserServiceImpl implements UserService {

	private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);

	@Override
	public boolean isUserAlreadyExistsByEmail(String email) {
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
	public Optional<User> getUserByEmail(String email) {
		LOG.debug("Starts getting user");

		EntityTransaction transaction = new EntityTransaction();

		try {
			UserDao userDao = DaoFactory.getInstance().getUserDao();
			SubscriptionDao subscriptionDao = DaoFactory.getInstance().getSubscriptionDao();

			transaction.initTransaction(userDao, subscriptionDao);

			Optional<User> userWithoutSubscriptions = userDao.getUserWithoutSubscriptionsByEmail(email);

			if (userWithoutSubscriptions.isEmpty()) {
				transaction.rollback();
				LOG.debug("Finished getting user with empty optional");
				return Optional.empty();
			}

			transaction.commit();
			LOG.debug("Finished getting user");
			return Optional.of(User.UserBuilder.fromUser(userWithoutSubscriptions.get())
					.withSubscriptions(subscriptionDao.getSubscriptionsByUserId(userWithoutSubscriptions.get().getId()))
					.build());
		} catch (DaoException e) {
			transaction.rollback();
			LOG.error("Can't get user by email " + email, e);
			throw new ServiceException("Can't get user by email " + email, e);
		} finally {
			transaction.endTransaction();
		}
	}

	@Override
	public User createNewUser(UserSignUpDto userSignUpDto) {
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
				throw new ServiceException("Can't insert user");
			}

			Optional<UserDetail> userDetail = userDetailDao.insertUserDetail(user.getUserDetail(), insertedUser.get().getId());

			if (userDetail.isPresent()) {
				user = User.UserBuilder.fromUser(insertedUser.get()).withUserDetail(userDetail.get()).build();
				transaction.commit();
				LOG.debug("Finished creating user");
				return user;
			}

			transaction.rollback();
			LOG.debug("Finished creating user with not created user detail");
			throw new ServiceException("Can't insert user detail");

		} catch (DaoException | HashingException e) {
			transaction.rollback();
			LOG.error("Can't insert user", e);
			throw new ServiceException("Can't insert user", e);
		} finally {
			transaction.endTransaction();
		}
	}

	@Override
	public List<UserForStatusManagement> getAllSubscribers() {
		LOG.debug("Starts method");

		EntityTransaction transaction = new EntityTransaction();

		try {
			UserDao userDao = DaoFactory.getInstance().getUserDao();
			SubscriptionDao subscriptionDao = DaoFactory.getInstance().getSubscriptionDao();

			transaction.initTransaction(userDao, subscriptionDao);

			List<User> subscribers = userDao.getAllSubscribers();

			List<UserForStatusManagement> usersForStatusManagement = new ArrayList<>();

			for (User user : subscribers) {
				usersForStatusManagement.add(new UserForStatusManagement(
						user.getId(),
						user.getUserDetail().getFirstName(),
						user.getUserDetail().getLastName(),
						user.getEmail(),
						user.getStatus(),
						subscriptionDao.getNumberOfSubscriptionsByUserId(user.getId())
				));
			}

			return usersForStatusManagement;

		} catch (DaoException e) {
			transaction.rollback();
			LOG.error("Can't get all users for status management", e);
			throw new ServiceException("Can't get all users for status management", e);
		} finally {
			transaction.endTransaction();
		}
	}

}
