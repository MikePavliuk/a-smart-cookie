package com.a_smart_cookie.service.impl;

import com.a_smart_cookie.dao.DaoFactory;
import com.a_smart_cookie.dao.EntityTransaction;
import com.a_smart_cookie.dao.UserDetailDao;
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


/**
 * Serves payment purposes of user on Dao and transactional layers.
 */
public class PaymentServiceImpl implements PaymentService {

	private static final Logger LOG = Logger.getLogger(PaymentServiceImpl.class);

	@Override
	public User addBalanceToUser(BigDecimal paymentAmount, PaymentMethod paymentMethod, User user) throws ServiceException, NotUpdatedResultsException {
		LOG.debug("Method starts");

		PaymentStrategy paymentStrategy = getPaymentStrategy(paymentMethod);
		PaymentContext paymentContext = new PaymentContext(paymentStrategy);

		try {
			paymentContext.performPayment(paymentAmount);
		} catch (PaymentException e) {
			throw new ServiceException("Can't perform payment", e);
		}

		EntityTransaction transaction = new EntityTransaction();

		try {
			UserDetailDao userDetailDao = DaoFactory.getInstance().getUserDetailDao();
			transaction.init(userDetailDao);

			if (!userDetailDao.addMoneyToBalanceByUserId(paymentAmount, user.getId())) {
				LOG.debug("Finished method without added money");
				throw new NotUpdatedResultsException("Can't add transaction money to user");
			}

			LOG.trace("Method finished correct");
			return User.UserBuilder.fromUser(user)
					.withUserDetail(new UserDetail(
							user.getUserDetail().getId(),
							user.getUserDetail().getFirstName(),
							user.getUserDetail().getFirstName(),
							user.getUserDetail().getBalance().add(paymentAmount)
					))
					.build();

		} catch (DaoException e) {
			throw new ServiceException("Can't add funds to user account", e);
		} finally {
			transaction.end();
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
