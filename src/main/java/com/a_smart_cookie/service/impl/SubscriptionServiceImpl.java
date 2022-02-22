package com.a_smart_cookie.service.impl;

import com.a_smart_cookie.dao.*;
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
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Serves subscription purposes of user on Dao layers.
 */
public class SubscriptionServiceImpl implements SubscriptionService {

	private static final Logger LOG = Logger.getLogger(SubscriptionServiceImpl.class);

	@Override
	public User subscribeToPublication(User user, int publicationId) {
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

			if (user.getUserDetail().getBalance().compareTo(publication.get().getPricePerMonth()) < 0) {
				transaction.rollback();
				LOG.debug("Finished method with rollback, not enough money to get paid");
				throw new ServiceException("Can't make transaction because not enough money to pay for subscription");
			}

			if (!userDetailDao.debitFundsFromBalanceByUserId(publication.get().getPricePerMonth(), user.getId())) {
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

			if (!subscriptionDao.insertSubscription(user.getId(), publicationId)) {
				transaction.rollback(savepoint);
				LOG.debug("Finished method with rollback, because didn't insert subscription");
				throw new NotUpdatedResultsException("Didn't insert balance result");
			}

			List<Subscription> subscriptions = subscriptionDao.getSubscriptionsByUserId(user.getId());
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
	public List<SubscriptionWithPublicationInfo> getSubscriptionsWithFullInfoByUserAndLanguage(User user, Language language) {
		LOG.debug("Method starts");

		EntityTransaction transaction = new EntityTransaction();

		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();
			transaction.init(publicationDao);

			List<SubscriptionWithPublicationInfo> result = new ArrayList<>();

			Optional<Publication> publication;
			for (Subscription subscription : user.getSubscriptions()) {
				publication = publicationDao.getPublicationWithInfoByIdAndLanguage(subscription.getPublicationId(), language);

				publication.ifPresent(value -> result.add(new SubscriptionWithPublicationInfo(
						value,
						subscription.getStartDate()
				)));
			}

			LOG.debug("Method finished");
			return result;

		} catch (DaoException e) {
			LOG.error("Can't get subscriptions", e);
			throw new ServiceException("Can't get subscriptions", e);
		} finally {
			transaction.end();
		}
	}

	@Override
	public void unsubscribeFromPublication(User user, int publicationId) {
		LOG.debug("Method starts");

		EntityTransaction transaction = new EntityTransaction();

		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();
			SubscriptionDao subscriptionDao = DaoFactory.getInstance().getSubscriptionDao();

			transaction.initTransaction(publicationDao, subscriptionDao);

			Optional<Publication> publication = publicationDao.getPublicationWithoutInfoById(publicationId);

			if (publication.isEmpty()) {
				transaction.rollback();
				LOG.debug("Finished method with rollback, because didn't find publication by id");
				throw new ServiceException("Can't find publication");
			}

			if (!subscriptionDao.removeSubscriptions(user.getId(), publicationId)) {
				transaction.rollback();
				LOG.debug("Finished method with rollback, because didn't insert subscription");
				throw new NotUpdatedResultsException("Didn't insert balance result");
			}

			Iterator<Subscription> iterator = user.getSubscriptions().iterator();

			while (iterator.hasNext()) {
				Subscription subscription = iterator.next();

				if (subscription.getPublicationId().equals(publicationId)) {
					iterator.remove();
					break;
				}
			}

			transaction.commit();
			LOG.debug("Finished method with commit");

		} catch (DaoException e) {
			transaction.rollback();
			LOG.error("Can't perform subscribing", e);
			throw new ServiceException("Can't perform subscribing", e);
		} finally {
			transaction.endTransaction();
		}
	}

}
