package com.a_smart_cookie.service.impl;

import com.a_smart_cookie.dao.*;
import com.a_smart_cookie.dto.admin.UserForManagement;
import com.a_smart_cookie.dto.sign_up.UserMapper;
import com.a_smart_cookie.dto.sign_up.UserSignUpDto;
import com.a_smart_cookie.entity.Status;
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
	public boolean isUserAlreadyExistsByEmail(String email) throws ServiceException {
		LOG.debug("Starts method");

		EntityTransaction transaction = new EntityTransaction();

		try {
			UserDao userDao = DaoFactory.getInstance().getUserDao();
			transaction.init(userDao);

			LOG.debug("Finished method");
			return userDao.isUserExistsByEmail(email);
		} catch (DaoException e) {
			throw new ServiceException("Can't check whether user exists", e);
		} finally {
			transaction.end();
		}
	}

	@Override
	public Optional<User> getUserByEmail(String email) throws ServiceException {
		LOG.debug("Starts method");

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
			LOG.debug("Finished method");
			return Optional.of(User.UserBuilder.fromUser(userWithoutSubscriptions.get())
					.withSubscriptions(subscriptionDao.getActiveSubscriptionsByUserId(userWithoutSubscriptions.get().getId()))
					.build());
		} catch (DaoException e) {
			transaction.rollback();
			throw new ServiceException("Can't get user by email " + email, e);
		} finally {
			transaction.endTransaction();
		}
	}

	@Override
	public User createNewUser(UserSignUpDto userSignUpDto) throws ServiceException {
		LOG.debug("Starts method");

		EntityTransaction transaction = new EntityTransaction();

		try {
			UserDao userDao = DaoFactory.getInstance().getUserDao();
			UserDetailDao userDetailDao = DaoFactory.getInstance().getUserDetailDao();

			transaction.initTransaction(userDao, userDetailDao);

			User user = UserMapper.convertFromDtoToEntity(userSignUpDto);
			Optional<User> insertedUser = userDao.createUser(user);

			if (insertedUser.isEmpty()) {
				transaction.rollback();
				LOG.debug("Finished creating user with not created user");
				throw new ServiceException("Can't insert user");
			}

			Optional<UserDetail> userDetail = userDetailDao.insertUserDetail(user.getUserDetail(), insertedUser.get().getId());

			if (userDetail.isPresent()) {
				user = User.UserBuilder.fromUser(insertedUser.get()).withUserDetail(userDetail.get()).build();
				transaction.commit();
				LOG.debug("Finished method");
				return user;
			}

			transaction.rollback();
			LOG.debug("Finished creating user with not created user detail");
			throw new ServiceException("Can't insert user detail");

		} catch (DaoException | HashingException e) {
			transaction.rollback();
			throw new ServiceException("Can't insert user", e);
		} finally {
			transaction.endTransaction();
		}
	}

	@Override
	public List<UserForManagement> getPaginatedUsersWithStatistics(int requestedPage, int itemsPerPage) throws ServiceException {
		LOG.debug("Starts method");

		EntityTransaction transaction = new EntityTransaction();

		try {
			UserDao userDao = DaoFactory.getInstance().getUserDao();
			SubscriptionDao subscriptionDao = DaoFactory.getInstance().getSubscriptionDao();

			transaction.initTransaction(userDao, subscriptionDao);

			List<User> subscribers = userDao.getSubscribersWithLimit(itemsPerPage * (requestedPage-1), itemsPerPage);

			return extractUserForManagementList(subscriptionDao, subscribers);

		} catch (DaoException e) {
			transaction.rollback();
			throw new ServiceException("Can't get paginated users with statistics", e);
		} finally {
			transaction.endTransaction();
		}
	}

	@Override
	public List<UserForManagement> getAllUsersWithStatistics() throws ServiceException {
		LOG.debug("Starts method");

		EntityTransaction transaction = new EntityTransaction();

		try {
			UserDao userDao = DaoFactory.getInstance().getUserDao();
			SubscriptionDao subscriptionDao = DaoFactory.getInstance().getSubscriptionDao();

			transaction.initTransaction(userDao, subscriptionDao);

			List<User> subscribers = userDao.getAllSubscribers();

			return extractUserForManagementList(subscriptionDao, subscribers);

		} catch (DaoException e) {
			transaction.rollback();
			throw new ServiceException("Can't get all users with statistics", e);
		} finally {
			transaction.endTransaction();
		}
	}

	private List<UserForManagement> extractUserForManagementList(SubscriptionDao subscriptionDao, List<User> subscribers) throws DaoException {
		List<UserForManagement> usersForStatusManagement = new ArrayList<>();

		for (User user : subscribers) {
			usersForStatusManagement.add(new UserForManagement(
					user.getId(),
					user.getUserDetail().getFirstName(),
					user.getUserDetail().getLastName(),
					user.getEmail(),
					user.getStatus(),
					subscriptionDao.getNumberOfActiveSubscriptionsByUserId(user.getId()),
					subscriptionDao.getNumberOfInactiveSubscriptionsByUserId(user.getId()),
					subscriptionDao.getTotalAmountOfSpentMoneyByUserId(user.getId())));
		}

		return usersForStatusManagement;
	}

	@Override
	public int getTotalNumberOfSubscribers() throws ServiceException {
		LOG.debug("Method starts");

		EntityTransaction transaction = new EntityTransaction();
		try {
			UserDao userDao = DaoFactory.getInstance().getUserDao();
			transaction.init(userDao);

			LOG.debug("Finished method");
			return userDao.getTotalNumberOfSubscribers();
		} catch (DaoException e) {
			throw new ServiceException("Can't get number of subscribers", e);
		} finally {
			transaction.end();
		}
	}

	@Override
	public void changeUserStatus(int userId, Status status) throws ServiceException {
		LOG.debug("Starts method");

		Status newStatus = status == Status.ACTIVE ? Status.BLOCKED : Status.ACTIVE;

		EntityTransaction transaction = new EntityTransaction();

		try {
			UserDao userDao = DaoFactory.getInstance().getUserDao();

			transaction.init(userDao);

			if (!userDao.changeUserStatus(userId, newStatus.name().toLowerCase())) {
				throw new ServiceException("Can't update status");
			}

		} catch (DaoException | ServiceException e) {
			throw new ServiceException("Can't change status for user id = '" + userId + "' to status - " + newStatus, e);
		} finally {
			transaction.end();
		}

	}

}
