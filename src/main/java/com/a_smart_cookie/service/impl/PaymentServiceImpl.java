package com.a_smart_cookie.service.impl;

import com.a_smart_cookie.dao.*;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.entity.Subscription;
import com.a_smart_cookie.entity.User;
import com.a_smart_cookie.entity.UserDetail;
import com.a_smart_cookie.exception.DaoException;
import com.a_smart_cookie.exception.NotUpdatedResultsException;
import com.a_smart_cookie.exception.PaymentException;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.PaymentService;
import com.a_smart_cookie.util.payment.PaymentContext;
import com.a_smart_cookie.util.payment.PaymentMethod;
import com.a_smart_cookie.util.payment.PaymentStrategy;
import com.a_smart_cookie.util.payment.strategies.CreditCardPaymentStrategy;
import com.a_smart_cookie.util.payment.strategies.PayPalPaymentStrategy;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Savepoint;
import java.util.List;
import java.util.Optional;


/**
 * Serves payment purposes of user on Dao and transactional layers.
 */
public class PaymentServiceImpl implements PaymentService {

	private static final Logger LOG = Logger.getLogger(PaymentServiceImpl.class);

	@Override
	public User addBalanceToUser(BigDecimal paymentAmount, PaymentMethod paymentMethod, User user) throws ServiceException {
		LOG.debug("Method starts");

		PaymentStrategy paymentStrategy = getPaymentStrategy(paymentMethod);
		PaymentContext paymentContext = new PaymentContext(paymentStrategy);

		try {
			paymentContext.performPayment(paymentAmount);
		} catch (PaymentException e) {
			LOG.error("Can't perform payment", e);
			throw new ServiceException("Can't perform payment", e);
		}

		EntityTransaction transaction = new EntityTransaction();
		Savepoint savepoint = null;

		try {
			UserDetailDao userDetailDao = DaoFactory.getInstance().getUserDetailDao();
			transaction.initTransaction(userDetailDao);

			if (!userDetailDao.addMoneyToBalanceByUserId(paymentAmount, user.getId())) {
				transaction.rollback();
				LOG.debug("Finished method with rollback, because didn't add money");
				throw new NotUpdatedResultsException("Can't add transaction money to user");
			}

			savepoint = transaction.setSavepoint();
			LOG.trace("Commit transaction because added money to user");

			Optional<BigDecimal> balance = userDetailDao.getBalanceByUserId(user.getId());
			if (balance.isEmpty()) {
				transaction.rollback(savepoint);
				LOG.debug("Finished method with rollback, because didn't get balance");
				throw new NotUpdatedResultsException("Can't get updated balance of user");
			}

			transaction.commit();

			return User.UserBuilder.fromUser(user)
					.withUserDetail(new UserDetail(
							user.getUserDetail().getId(),
							user.getUserDetail().getFirstName(),
							user.getUserDetail().getFirstName(),
							balance.get()
					))
					.build();

		} catch (DaoException e) {
			if (savepoint != null) {
				transaction.rollback(savepoint);
			} else {
				transaction.rollback();
			}
			LOG.error("Can't add funds to user account", e);
			throw new ServiceException("Can't add funds to user account", e);
		} finally {
			transaction.endTransaction();
		}

	}

	@Override
	public User subscribeToPublication(User user, int publicationId) throws ServiceException, NotUpdatedResultsException {
		LOG.debug("Method starts");

		EntityTransaction transaction = new EntityTransaction();
		Savepoint savepoint = null;

		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();
			UserDetailDao userDetailDao = DaoFactory.getInstance().getUserDetailDao();
			SubscriptionDao subscriptionDao = DaoFactory.getInstance().getSubscriptionDao();

			transaction.initTransaction(publicationDao, userDetailDao, subscriptionDao);

			Optional<Publication> publication = publicationDao.getPublicationById(publicationId);

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

	private PaymentStrategy getPaymentStrategy(PaymentMethod paymentMethod) {
		PaymentStrategy paymentStrategy;

		switch (paymentMethod) {
			case PAYPAL:
				paymentStrategy = new PayPalPaymentStrategy();
				break;

			case CREDIT_CARD:
			default:
				paymentStrategy = new CreditCardPaymentStrategy();
				break;
		}
		return paymentStrategy;
	}

}
