package com.a_smart_cookie.service.impl;

import com.a_smart_cookie.dao.DaoFactory;
import com.a_smart_cookie.dao.EntityTransaction;
import com.a_smart_cookie.dao.UserDetailDao;
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
import java.util.Optional;


/**
 * Serves payment purposes of user on Dao and transactional layers.
 *
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
			throw new ServiceException("Can't check whether user exists", e);
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
