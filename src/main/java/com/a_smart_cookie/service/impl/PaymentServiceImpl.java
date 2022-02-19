package com.a_smart_cookie.service.impl;

import com.a_smart_cookie.dao.*;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.entity.Subscription;
import com.a_smart_cookie.entity.User;
import com.a_smart_cookie.entity.UserDetail;
import com.a_smart_cookie.exception.DaoException;
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
import java.util.List;
import java.util.Optional;


/**
 * Serves payment purposes of user on Dao and transactional layers.
 */
public class PaymentServiceImpl implements PaymentService {

	private static final Logger LOG = Logger.getLogger(PaymentServiceImpl.class);

	@Override
	public Optional<BigDecimal> addBalanceToUserById(BigDecimal paymentAmount, PaymentMethod paymentMethod, int userId) throws ServiceException {
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

		try {
			UserDetailDao userDetailDao = DaoFactory.getInstance().getUserDetailDao();
			transaction.initTransaction(userDetailDao);

			if (!userDetailDao.addMoneyToBalanceByUserId(paymentAmount, userId)) {
				transaction.rollback();
				LOG.debug("Finished method with rollback, because didn't add money");
				return Optional.empty();
			}

			Optional<BigDecimal> balance = userDetailDao.getBalanceByUserId(userId);

			if (balance.isEmpty()) {
				transaction.rollback();
				LOG.debug("Finished method with rollback, because didn't get balance");
				return Optional.empty();
			}

			transaction.commit();
			LOG.debug("Finished method with commit value");
			return balance;

		} catch (DaoException e) {
			transaction.rollback();
			LOG.error("Can't add funds to user account", e);
			throw new ServiceException("Can't add funds to user account", e);
		} finally {
			transaction.endTransaction();
		}

	}

	@Override
	public Optional<User> subscribeToPublication(User user, int publicationId) throws ServiceException {
		LOG.debug("Method starts");

		EntityTransaction transaction = new EntityTransaction();

		try {
			PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();
			UserDetailDao userDetailDao = DaoFactory.getInstance().getUserDetailDao();
			SubscriptionDao subscriptionDao = DaoFactory.getInstance().getSubscriptionDao();

			transaction.initTransaction(publicationDao, userDetailDao, subscriptionDao);

			Optional<Publication> publication = publicationDao.getPublicationById(publicationId);

			if (publication.isEmpty()) {
				transaction.rollback();
				LOG.debug("Finished method with rollback, because didn't find publication by id");
				return Optional.empty();
			}

			if (user.getUserDetail().getBalance().compareTo(publication.get().getPricePerMonth()) < 0) {
				transaction.rollback();
				LOG.debug("Finished method with rollback, because didn't debit funds");
				return Optional.empty();
			}

			if (!userDetailDao.debitFundsFromBalanceByUserId(publication.get().getPricePerMonth(), user.getId())) {
				transaction.rollback();
				LOG.debug("Finished method with rollback, because didn't debit funds");
				return Optional.empty();
			}

			Optional<BigDecimal> balance = userDetailDao.getBalanceByUserId(user.getId());

			if (balance.isEmpty()) {
				transaction.rollback();
				LOG.debug("Finished method with rollback, because didn't get balance");
				return Optional.empty();
			}

			if (!subscriptionDao.insertSubscription(user.getId(), publicationId)) {
				transaction.rollback();
				LOG.debug("Finished method with rollback, because didn't insert subscription");
				return Optional.empty();
			}

			List<Subscription> subscriptions = subscriptionDao.getSubscriptionsByUserId(user.getId());
			transaction.commit();
			LOG.debug("Finished method with commit");
			return Optional.of(
					User.UserBuilder.fromUser(user)
							.withUserDetail(new UserDetail(user.getUserDetail().getId(),
											user.getUserDetail().getFirstName(),
											user.getUserDetail().getLastName(),
											balance.get()))
							.withSubscriptions(subscriptions)
							.build()
			);

		} catch (DaoException e) {
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
