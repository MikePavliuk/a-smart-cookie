package com.a_smart_cookie.service.impl;

import com.a_smart_cookie.dao.*;
import com.a_smart_cookie.dto.user.SubscriptionStatistics;
import com.a_smart_cookie.dto.user.SubscriptionWithPublicationInfo;
import com.a_smart_cookie.entity.*;
import com.a_smart_cookie.exception.DaoException;
import com.a_smart_cookie.exception.NotUpdatedResultsException;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.SubscriptionService;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Serves subscription purposes of user on Dao layers.
 */
public class SubscriptionServiceImpl implements SubscriptionService {

	private static final Logger LOG = Logger.getLogger(SubscriptionServiceImpl.class);

	@Override
	public User subscribeToPublication(User user, int publicationId, int periodInMonths) {
		LOG.debug("Method starts");

		EntityTransaction transaction = new EntityTransaction();
		Savepoint savepoint = null;

		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();
			UserDetailDao userDetailDao = DaoFactory.getInstance().getUserDetailDao();
			SubscriptionDao subscriptionDao = DaoFactory.getInstance().getSubscriptionDao();

			transaction.initTransaction(publicationDao, userDetailDao, subscriptionDao);

			Optional<Publication> publication = publicationDao.getPublicationWithoutInfoById(publicationId);

			if (publication.isEmpty()) {
				transaction.rollback();
				LOG.debug("Finished method with rollback, because didn't find publication by id");
				throw new ServiceException("Can't find publication");
			}

			BigDecimal subscriptionPrice = publication.get().getPricePerMonth().multiply(BigDecimal.valueOf(periodInMonths));
			LOG.trace("subscription total price --> " + subscriptionPrice);
			LOG.trace("user balance --> " + user.getUserDetail().getBalance());

			if (user.getUserDetail().getBalance().compareTo(subscriptionPrice) < 0) {
				transaction.rollback();
				LOG.debug("Finished method with rollback, not enough money to get paid");
				throw new ServiceException("Can't make transaction because not enough money to pay for subscription");
			}

			if (!userDetailDao.debitFundsFromBalanceByUserId(subscriptionPrice, user.getId())) {
				transaction.rollback();
				LOG.debug("Finished method with rollback, because didn't debit funds");
				throw new ServiceException("Can't debit funds");
			}

			savepoint = transaction.setSavepoint();

			Optional<BigDecimal> balance = userDetailDao.getBalanceByUserId(user.getId());

			if (balance.isEmpty()) {
				transaction.rollback(savepoint);
				LOG.debug("Finished method with rollback, because didn't get balance");
				throw new NotUpdatedResultsException("Didn't get updated balance result");
			}

			if (!subscriptionDao.insertSubscription(user.getId(), publicationId, periodInMonths)) {
				transaction.rollback(savepoint);
				LOG.debug("Finished method with rollback, because didn't insert subscription");
				throw new NotUpdatedResultsException("Didn't insert balance result");
			}

			List<Subscription> subscriptions = subscriptionDao.getActiveSubscriptionsByUserId(user.getId());
			transaction.commit();
			LOG.debug("Finished method with commit");
			return User.UserBuilder.fromUser(user)
					.withUserDetail(new UserDetail(user.getUserDetail().getId(),
							user.getUserDetail().getFirstName(),
							user.getUserDetail().getLastName(),
							balance.get()))
					.withSubscriptions(subscriptions)
					.build();

		} catch (DaoException e) {

			if (savepoint != null) {
				transaction.rollback(savepoint);
			} else {
				transaction.rollback();
			}

			LOG.error("Can't perform subscribing", e);
			throw new ServiceException("Can't perform subscribing", e);
		} finally {
			transaction.endTransaction();
		}
	}

	@Override
	public SubscriptionStatistics getSubscriptionsStatistics(User user, Language language) {
		LOG.debug("Method starts");

		EntityTransaction transaction = new EntityTransaction();

		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();
			SubscriptionDao subscriptionDao = DaoFactory.getInstance().getSubscriptionDao();

			transaction.initTransaction(publicationDao, subscriptionDao);

			List<SubscriptionWithPublicationInfo> activeSubscriptions = new ArrayList<>();

			for (Subscription subscription : user.getSubscriptions()) {
				activeSubscriptions.add(new SubscriptionWithPublicationInfo(
						publicationDao.getPublicationWithInfoByIdAndLanguage(subscription.getPublicationId(), language),
						subscription.getStartDate(),
						subscription.getPeriodInMonths()));
			}

			int numberOfInactiveSubscriptions = subscriptionDao.getNumberOfInactiveSubscriptionsByUserId(user.getId());
			BigDecimal spentMoney = subscriptionDao.getTotalAmountOfSpentMoneyByUserId(user.getId());

			LOG.debug("Method finished");
			transaction.commit();
			return new SubscriptionStatistics(activeSubscriptions, numberOfInactiveSubscriptions, spentMoney);

		} catch (DaoException e) {
			transaction.rollback();
			LOG.error("Can't get subscription statistics with '" + user + "' and '" + language + "'", e);
			throw new ServiceException("Can't get subscription statistics with '" + user + "' and '" + language + "'", e);
		} finally {
			transaction.endTransaction();
		}
	}

}
